
package com.l2a.storeelseapi;

import android.app.Application;

@SuppressWarnings("unused")
public class SeApplication extends Application {
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
