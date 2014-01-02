
package com.l2a.storeelseplugin;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.l2a.storeelseapi.SeStorageManager;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private SeStorageManager mStorageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStorageManager = SeStorageManager.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        boolean handled = false;

        switch (id) {
        case R.id.action_settings:
            SharedPreferences prefs = mStorageManager.getSharedPreferences(0);
            if (null == prefs) {
                Log.w(TAG, "prefs is null.");
                break;
            }

            Editor editor = prefs.edit();
            editor.putString(TAG, TAG);
            editor.apply();

            handled = true;
            break;

        default:
            handled = super.onOptionsItemSelected(item);
            break;
        }

        return handled;
    }
}
