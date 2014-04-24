
package com.l2a.main.plugin;

import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.l2a.api.ApiIntent;
import com.l2a.api.ApiIntentHelper;
import com.l2a.api.init.PluginDescription;

public class PluginManagerService extends IntentService {
    private static final String TAG = "PluginManagerService";
    private static final String ACTION = TAG + ".action";
    private static final String ACTION_GET_PLUGIN_DESCRIPTION = ACTION + ".GET_PLUGIN_DESCRIPTION";
    private static final String ACTION_RECEIVED_PLUGIN_DESCRIPTION = ACTION
            + ".RECEIVED_PLUGIN_DESCRIPTION";
    public static final String ACTION_NEW_PLUGIN_DESCRIPTION = ACTION + ".NEW_PLUGIN_DESCRIPTION";

    public static void getPluginDescription(Context context) {
        Intent intent = new Intent(context, PluginManagerService.class);
        intent.setAction(ACTION_GET_PLUGIN_DESCRIPTION);
        context.startService(intent);
    }

    private List<PluginDescription> mPluginDescriptions = new ArrayList<PluginDescription>();

    public PluginManagerService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        if (ACTION_GET_PLUGIN_DESCRIPTION.equals(action)) {
            handleGetPluginDescription(intent);
        } else if (ACTION_RECEIVED_PLUGIN_DESCRIPTION.equals(action)) {
            handleReceivedPluginDescription(intent);
        }
    }

    private void handleGetPluginDescription(Intent intent) {
        PackageManager manager = getPackageManager();

        Intent queryIntent = new Intent(ApiIntent.ACTION_GET_PLUGIN_DESCRIPTION);
        List<ResolveInfo> infos = manager.queryIntentServices(queryIntent, 0);

        // Create reply to pending intent
        Intent reply = new Intent(this, PluginManagerService.class);
        reply.setAction(ACTION_RECEIVED_PLUGIN_DESCRIPTION);
        PendingIntent replyTo = PendingIntent.getService(this, 0, reply, 0);

        // Go try to grab all the plugin description info
        for (ResolveInfo i : infos) {
            Intent get = new Intent(ApiIntent.ACTION_GET_PLUGIN_DESCRIPTION);
            get.setComponent(new ComponentName(i.serviceInfo.packageName, i.serviceInfo.name));
            ApiIntentHelper.putReplyTo(get, replyTo);
            startService(get);
        }
    }

    private void handleReceivedPluginDescription(Intent intent) {
        PluginDescription pluginDescription = ApiIntentHelper.getPluginDescription(intent);
        if (null == pluginDescription) {
            return;
        }

        Log.d(TAG, "Received plugin description");

        // Add plugin descriptions to list of plugin descriptions
        mPluginDescriptions.add(pluginDescription);

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.sendBroadcast(ApiIntentHelper.putPluginDescription(new Intent(
                ACTION_NEW_PLUGIN_DESCRIPTION), pluginDescription));
    }
}
