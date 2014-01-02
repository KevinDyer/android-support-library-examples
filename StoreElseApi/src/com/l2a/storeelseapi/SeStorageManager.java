
package com.l2a.storeelseapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.l2a.storeelseapi.internal.FileUtils;
import com.l2a.storeelseapi.internal.FileUtils.FileStatus;
import com.l2a.storeelseapi.internal.SharedPreferencesImpl;
import com.l2a.storeelseapi.internal.XmlUtils;

@SuppressWarnings({
        "rawtypes",
        "unchecked",
})
public class SeStorageManager extends BroadcastReceiver {
    private static final String TAG = SeStorageManager.class.getSimpleName();

    private static SeStorageManager sInstance;
    private static Context sContext;

    public static void initialize(Context context) {
        if (null != sInstance) {
            throw new RuntimeException(TAG + " has already been initialized.");
        }

        sContext = context;
        sInstance = new SeStorageManager();

        // Register broadcast receiver
        sContext.registerReceiver(
                sInstance,
                new IntentFilter(SeIntent.ACTION_GET_STORAGE_FILENAMES));

        // Fire off intent to Main app to get the storage filenames
        Intent request = new Intent(SeIntent.ACTION_GET_STORAGE_FILENAMES);
        request.putExtra(SeIntent.EXTRA_PACKAGE_NAME, sContext.getPackageName());
        sContext.startService(request);
    }

    public static SeStorageManager getInstance() {
        if (null == sInstance) {
            throw new RuntimeException(TAG + " has not been initialized.");
        }

        return sInstance;
    }

    public static void close() {
        if (null == sInstance) {
            throw new RuntimeException(TAG + " has not been initialized.");
        }

        sContext.unregisterReceiver(sInstance);
        sInstance = null;
        sContext = null;
    }

    private File mPrefsDir;
    private final HashMap<String, SharedPreferencesImpl> mSharedPrefs =
            new HashMap<String, SharedPreferencesImpl>();

    private SeStorageManager() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (!SeIntent.ACTION_GET_STORAGE_FILENAMES.equals(action)) {
            return;
        }

        // Get the filename
        Uri contentUri = intent.getParcelableExtra(SeIntent.EXTRA_PREFS_DIR);
        if (null == contentUri) {
            return;
        }

        ContentResolver cr = context.getContentResolver();
        try {
            ParcelFileDescriptor fd = cr.openFileDescriptor(contentUri, "rw");
            cr.openFileDescriptor(contentUri, "rw");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // mPrefsDir = new File(dirPath);
    }

    private final HashMap<String, File> mRoots = new HashMap<String, File>();

    private File getFileForUri(Uri uri) {
        String path = uri.getEncodedPath();

        final int splitIndex = path.indexOf('/', 1);
        final String tag = Uri.decode(path.substring(1, splitIndex));
        path = Uri.decode(path.substring(splitIndex + 1));

        final File root = mRoots.get(tag);
        if (root == null) {
            throw new IllegalArgumentException("Unable to find configured root for " + uri);
        }

        File file = new File(root, path);
        try {
            file = file.getCanonicalFile();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to resolve canonical path for " + file);
        }

        if (!file.getPath().startsWith(root.getPath())) {
            throw new SecurityException("Resolved path jumped beyond configured root");
        }

        return file;
    }

    private File makeFilename(File base, String name) {
        if (name.indexOf(File.separatorChar) < 0) {
            return new File(base, name);
        }
        throw new IllegalArgumentException(
                "File " + name + " contains a path separator");
    }

    private File makeBackupFile(File prefsFile) {
        return new File(prefsFile.getPath() + ".bak");
    }

    public File getSharedPrefsFile(String name) {
        File file = makeFilename(mPrefsDir, name + ".xml");
        Log.d(TAG, "filepath=" + file.getAbsolutePath());
        return file;
    }

    public SharedPreferences getSharedPreferences(int mode) {
        return getSharedPreferences(TAG, mode);
    }

    public SharedPreferences getSharedPreferences(String name, int mode) {
        if (null == mPrefsDir) {
            return null;
        }

        SharedPreferencesImpl sp;
        File prefsFile;
        boolean needInitialLoad = false;
        synchronized (mSharedPrefs) {
            sp = mSharedPrefs.get(name);
            if (sp != null && !sp.hasFileChangedUnexpectedly()) {
                return sp;
            }
            prefsFile = getSharedPrefsFile(name);
            if (sp == null) {
                sp = new SharedPreferencesImpl(prefsFile, mode, null);
                mSharedPrefs.put(name, sp);
                needInitialLoad = true;
            }
        }

        synchronized (sp) {
            if (needInitialLoad && sp.isLoaded()) {
                // lost the race to load; another thread handled it
                return sp;
            }
            File backup = makeBackupFile(prefsFile);
            if (backup.exists()) {
                prefsFile.delete();
                backup.renameTo(prefsFile);
            }

            // Debugging
            if (prefsFile.exists() && !prefsFile.canRead()) {
                Log.w(TAG, "Attempt to read preferences file " + prefsFile + " without permission");
            }

            Map map = null;
            FileStatus stat = new FileStatus();
            if (FileUtils.getFileStatus(prefsFile.getPath(), stat) && prefsFile.canRead()) {
                try {
                    FileInputStream str = new FileInputStream(prefsFile);
                    map = XmlUtils.readMapXml(str);
                    str.close();
                } catch (org.xmlpull.v1.XmlPullParserException e) {
                    Log.w(TAG, "getSharedPreferences", e);
                } catch (FileNotFoundException e) {
                    Log.w(TAG, "getSharedPreferences", e);
                } catch (IOException e) {
                    Log.w(TAG, "getSharedPreferences", e);
                }
            }
            sp.replace(map, stat);
        }
        return sp;
    }
}
