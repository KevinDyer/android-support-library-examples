
package com.l2a.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;

import com.l2a.api.internal.FileUtils;
import com.l2a.api.internal.SharedPreferencesImpl;
import com.l2a.api.internal.XmlUtils;
import com.l2a.api.internal.FileUtils.FileStatus;

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
                new IntentFilter(ApiIntents.ACTION_GET_STORAGE_FILENAMES));

        // Fire off intent to Main app to get the storage filenames
        Intent request = new Intent(ApiIntents.ACTION_GET_STORAGE_FILENAMES);
        request.putExtra(ApiIntents.EXTRA_PACKAGE_NAME, sContext.getPackageName());
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
    private HashMap<String, SharedPreferencesImpl> mSharedPrefs =
            new HashMap<String, SharedPreferencesImpl>();

    private SeStorageManager() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (!ApiIntents.ACTION_GET_STORAGE_FILENAMES.equals(action)) {
            return;
        }

        String prefsDirPath = intent.getStringExtra(ApiIntents.EXTRA_PREFS_DIR_PATH);
        Log.d(TAG, "prefsDirPath=" + prefsDirPath);

        if (null != prefsDirPath) {
            mPrefsDir = new File(prefsDirPath);
        }
    }

    private File makeFilename(File base, String name) {
        if (name.indexOf(File.separatorChar) < 0) {
            return new File(base, name);
        }
        throw new IllegalArgumentException("File " + name + " contains a path separator");
    }

    private File makeBackupFile(File prefsFile) {
        return new File(prefsFile.getPath() + ".bak");
    }

    private File getSharedPrefsFile(String name) {
        File file = makeFilename(mPrefsDir, name + ".xml");
        Log.d(TAG, "filepath=" + file.getAbsolutePath());
        return file;
    }

    public SharedPreferences getSharedPreferences(int mode) {
        return getSharedPreferences(TAG, mode);
    }

    public SharedPreferences getSharedPreferences(String name, int mode) {
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
