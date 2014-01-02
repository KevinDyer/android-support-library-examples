
package com.l2a.storeelsemain;

import java.io.File;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.l2a.storeelseapi.SeIntent;

public class FileService extends IntentService {
    private static final String TAG = FileService.class.getSimpleName();
    private static final String FILE_PROVIDER_AUTHORITY = "com.l2a.storeelsemain.fileprovider";

    public FileService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();

        if (SeIntent.ACTION_GET_STORAGE_FILENAMES.equals(action)) {
            handleSharedPreferencesFilename(intent);
        }

    }

    private void handleSharedPreferencesFilename(Intent intent) {
        Log.d(TAG, "handleSharedPreferencesFilename");

        // Get the plugin package name
        String packageName = intent.getStringExtra(SeIntent.EXTRA_PACKAGE_NAME);
        if (TextUtils.isEmpty(packageName)) {
            return;
        }

        // Create the plugin prefs directory
        File baseFilesDir = getFilesDir();
        File prefsDir = new File(baseFilesDir, "plugin_prefs");
        File pluginPrefsDir = new File(prefsDir, packageName);
        pluginPrefsDir.mkdirs();
        pluginPrefsDir.mkdir();

        // Grant permissions
        int flags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        Uri contentUri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, pluginPrefsDir);
        grantUriPermission(packageName, contentUri, flags);

        // Send the results back to the plugin
        Intent result = new Intent(SeIntent.ACTION_GET_STORAGE_FILENAMES);
        intent.addFlags(flags);
        intent.putExtra(SeIntent.EXTRA_PREFS_DIR, contentUri);
        sendBroadcast(result);
    }
}
