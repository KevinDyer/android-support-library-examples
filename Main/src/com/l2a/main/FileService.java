package com.l2a.main;

import java.io.File;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.l2a.plugin.api.SeIntent;

public class FileService extends IntentService {
    private static final String TAG = FileService.class.getSimpleName();

    private File mPrefsDir;

    public FileService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Create the plugin prefs directory
        mPrefsDir = new File(getFilesDir(), "plugin_prefs");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();

        if (SeIntent.ACTION_GET_STORAGE_FILENAMES.equals(action)) {
            handleSharedPreferencesFilename(intent);
        }

    }

    private void handleSharedPreferencesFilename(Intent intent) {
        // Get the plugin package name
        String packageName = intent.getStringExtra(SeIntent.EXTRA_PACKAGE_NAME);
        if (TextUtils.isEmpty(packageName)) {
            return;
        }

        File pluginPrefsDir = new File(mPrefsDir, packageName);
        pluginPrefsDir.mkdirs();
        pluginPrefsDir.mkdir();
//        pluginPrefsDir.setExecutable(true, false);
//        pluginPrefsDir.setWritable(true, false);
//        pluginPrefsDir.setReadable(true, false);
        Log.d(TAG, "prefsDirPath=" + pluginPrefsDir.getAbsolutePath());

        // Send the results back to the plugin
        Intent result = new Intent(SeIntent.ACTION_GET_STORAGE_FILENAMES);
        intent.putExtra(SeIntent.EXTRA_PREFS_DIR_PATH, pluginPrefsDir.getAbsolutePath());
        sendBroadcast(result);
    }
}
