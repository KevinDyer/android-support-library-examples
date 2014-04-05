package com.l2a.main;

import android.app.Application;
import android.util.Log;

import com.l2a.main.plugin.PluginManager;

public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.d(TAG, "Starting MainApplication");
        super.onCreate();
        
        PluginManager.initialize();
    }
    
    @Override
    public void onTerminate() {
        PluginManager.close();
        
        super.onTerminate();
        Log.d(TAG, "Stopping MainApplication");
    }
}
