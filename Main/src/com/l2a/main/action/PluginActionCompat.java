
package com.l2a.main.action;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.util.Log;

import com.l2a.api.init.PluginAction;
import com.l2a.main.widget.ActionAdapter.Action;

public class PluginActionCompat implements Action {
    private static final String TAG = PluginActionCompat.class.getSimpleName();

    private final PluginAction mPluginAction;

    public PluginActionCompat(PluginAction pluginAction) {
        mPluginAction = pluginAction;
    }

    @Override
    public CharSequence getTitle() {
        return mPluginAction.getDisplayName();
    }

    @Override
    public CharSequence getSummary() {
        return null;
    }

    @Override
    public void execute() {
        PendingIntent intent = mPluginAction.getIntent();
        if (null == intent) {
            return;
        }

        try {
            intent.send();
        } catch (CanceledException e) {
            Log.e(TAG, "Failed to send PluginAction", e);
        }
    }

}
