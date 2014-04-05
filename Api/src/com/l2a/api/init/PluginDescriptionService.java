
package com.l2a.api.init;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.util.Log;

import com.l2a.api.ApiIntentHelper;
import com.l2a.api.ApiIntent;

public abstract class PluginDescriptionService extends IntentService {
    private static final String TAG = PluginDescriptionService.class.getSimpleName();

    public PluginDescriptionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();

        if (!ApiIntent.ACTION_GET_PLUGIN_DESCRIPTION.equals(action)) {
            return;
        }

        // Get the reply to intent.
        PendingIntent replyTo = ApiIntentHelper.getReplyTo(intent);

        // Create plugin description.
        PluginDescription pluginDescription = getPluginDescription();

        // Add the PluginDescription to the return intent.
        Intent result = ApiIntentHelper.putPluginDescription(new Intent(), pluginDescription);

        /*
         * Send the plugin description back to the package that requested it.
         */
        try {
            replyTo.send(this, 0, result);
        } catch (CanceledException e) {
            Log.e(TAG, "Failed to send plugin description", e);
        }
    }

    /**
     * This is where a plugin describes the actions it can perform.
     * 
     * @return the description of the plugin
     * @see PluginAction
     */
    protected abstract PluginDescription getPluginDescription();

}
