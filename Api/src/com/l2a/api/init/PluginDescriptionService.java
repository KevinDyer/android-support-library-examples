package com.l2a.api.init;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.util.Log;

import com.l2a.api.ApiIntentHelpers;
import com.l2a.api.ApiIntents;

public abstract class PluginDescriptionService extends IntentService {
    private static final String TAG = PluginDescriptionService.class.getSimpleName();

    public PluginDescriptionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();
        
        if (ApiIntents.ACTION_GET_PLUGIN_DESCRIPTION.equals(action)) {
            PendingIntent replyTo = intent.getParcelableExtra(ApiIntents.EXTRA_REPLY_TO);
            
            // Create plugin description and send it to the requestor
            PluginDescription pluginDescription = getPluginDescription();
            
            Intent result = ApiIntentHelpers.putPluginDescription(new Intent(), pluginDescription);
            try {
                replyTo.send(this, 0, result);
            } catch (CanceledException e) {
                Log.e(TAG, "Failed to send plugin description", e);
            }
        }
    }

    protected abstract PluginDescription getPluginDescription();

}
