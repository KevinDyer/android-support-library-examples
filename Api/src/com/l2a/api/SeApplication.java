
package com.l2a.api;

import android.app.Application;

public class SeApplication extends Application {
    @SuppressWarnings("unused")
    private static final String TAG = SeApplication.class.getSimpleName();

    public SeApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SeStorageManager.initialize(this);
    }

    @Override
    public void onTerminate() {
        SeStorageManager.close();

        super.onTerminate();
    }
}
