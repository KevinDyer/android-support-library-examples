
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
import android.util.SparseArray;

import com.l2a.api.ApiIntent;
import com.l2a.api.ApiIntentHelper;
import com.l2a.api.init.PluginDescription;

public class PluginManagerService extends IntentService {
    private static final String TAG = "PluginManagerService";
    private static final String ACTION = TAG + ".action";
    private static final String EXTRA = TAG + ".extra";
    private static final String ACTION_GET_PLUGIN_DESCRIPTION = ACTION + ".GET_PLUGIN_DESCRIPTION";
    private static final String ACTION_RECEIVED_PLUGIN_DESCRIPTION = ACTION
            + ".RECEIVED_PLUGIN_DESCRIPTION";
    private static final String EXTRA_TOKEN = EXTRA + ".TOKEN";

    public static void getPluginDescription(Context context) {
        Intent intent = new Intent(context, PluginManagerService.class);
        intent.setAction(ACTION_GET_PLUGIN_DESCRIPTION);
        context.startService(intent);
    }

    private int mToken = 0;
    private SparseArray<List<PluginDescription>> mPluginDescriptions = new SparseArray<List<PluginDescription>>();

    public PluginManagerService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        if (ACTION_GET_PLUGIN_DESCRIPTION.equals(action)) {
            handleGetPluginDescription();
        } else if (ACTION_RECEIVED_PLUGIN_DESCRIPTION.equals(action)) {
            handleReceivedPluginDescription(intent);
        }
    }

    private void handleGetPluginDescription() {
        PackageManager manager = getPackageManager();

        Intent queryIntent = new Intent(ApiIntent.ACTION_GET_PLUGIN_DESCRIPTION);
        List<ResolveInfo> infos = manager.queryIntentServices(queryIntent, 0);

        // Create list for plugin descriptions
        mPluginDescriptions.append(mToken, new ArrayList<PluginDescription>());

        // Create reply to pending intent
        Intent replyIntent = new Intent(this, PluginManagerService.class);
        replyIntent.setAction(ACTION_RECEIVED_PLUGIN_DESCRIPTION);
        replyIntent.putExtra(EXTRA_TOKEN, mToken);
        PendingIntent replyTo = PendingIntent.getService(this, 0, replyIntent, 0);

        for (ResolveInfo i : infos) {
            ComponentName cn = new ComponentName(i.serviceInfo.packageName, i.serviceInfo.name);
            Intent getIntent = new Intent(ApiIntent.ACTION_GET_PLUGIN_DESCRIPTION);
            getIntent.setComponent(cn);
            ApiIntentHelper.putReplyTo(getIntent, replyTo);
            startService(getIntent);
        }

        mToken++;
    }

    private void handleReceivedPluginDescription(Intent intent) {
        PluginDescription pluginDescription = ApiIntentHelper.getPluginDescription(intent);
        if (null == pluginDescription) {
            return;
        }

        // Get list of plugin descriptions
        int token = intent.getIntExtra(EXTRA_TOKEN, -1);
        List<PluginDescription> pluginDescriptions = mPluginDescriptions.get(mToken);
        if (null == pluginDescriptions) {
            pluginDescriptions = new ArrayList<PluginDescription>();
            mPluginDescriptions.append(token, pluginDescriptions);
        }

        // Add plugin descriptions to list of plugin descriptions
        pluginDescriptions.add(pluginDescription);

        // TODO Send list to original caller
    }
}
