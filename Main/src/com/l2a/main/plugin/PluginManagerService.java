
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
import android.util.Log;
import android.util.SparseArray;

import com.l2a.api.ApiIntent;
import com.l2a.api.ApiIntentHelper;
import com.l2a.api.init.PluginDescription;

public class PluginManagerService extends IntentService {
    private static final String TAG = "PluginManagerService";
    private static final String ACTION = TAG + ".action";
    private static final String EXTRA = TAG + ".extra";
    private static final String ACTION_GET_PLUGIN_DESCRIPTION = ACTION + ".GET_PLUGIN_DESCRIPTION";
    private static final String ACTION_CLEAN_PLUGIN_DESCRIPTIONS = ACTION
            + ".CLEAN_PLUGIN_DESCRIPTIONS";
    private static final String ACTION_RECEIVED_PLUGIN_DESCRIPTION = ACTION
            + ".RECEIVED_PLUGIN_DESCRIPTION";
    private static final String EXTRA_TOKEN = EXTRA + ".TOKEN";
    
    private static final long SEND_PLUGIN_DESCRIPTION_DELAY_MS = 10* 1000L;

    public static void getPluginDescription(Context context) {
        Intent intent = new Intent(context, PluginManagerService.class);
        intent.setAction(ACTION_GET_PLUGIN_DESCRIPTION);
        context.startService(intent);
    }

    private int mToken = 0;
    private SparseArray<PluginHolder> mPluginDescriptions = new SparseArray<PluginHolder>();

    private static class PluginHolder {
        long start;
        int total;
        PendingIntent replyTo;
        List<PluginDescription> pluginDescriptions = new ArrayList<PluginDescription>();
    }

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
        } else if (ACTION_CLEAN_PLUGIN_DESCRIPTIONS.equals(action)) {
            handleCleanPluginDescriptions();
        }
    }

    private void handleGetPluginDescription(Intent intent) {
        PackageManager manager = getPackageManager();

        Intent queryIntent = new Intent(ApiIntent.ACTION_GET_PLUGIN_DESCRIPTION);
        List<ResolveInfo> infos = manager.queryIntentServices(queryIntent, 0);

        // Create reply to pending intent
        Intent replyIntent = new Intent(this, PluginManagerService.class);
        replyIntent.setAction(ACTION_RECEIVED_PLUGIN_DESCRIPTION);
        replyIntent.putExtra(EXTRA_TOKEN, mToken);
        PendingIntent replyTo = PendingIntent.getService(this, 0, replyIntent, 0);

        // Create plugin holder for received plugin description
        PluginHolder pluginHolder = new PluginHolder();
        pluginHolder.start = System.currentTimeMillis();
        pluginHolder.total = infos.size();
        pluginHolder.replyTo = ApiIntentHelper.getReplyTo(intent);
        mPluginDescriptions.append(mToken, pluginHolder);
        mToken++;

        // Go try to grab all the plugin description info
        for (ResolveInfo i : infos) {
            ComponentName cn = new ComponentName(i.serviceInfo.packageName, i.serviceInfo.name);
            Intent getIntent = new Intent(ApiIntent.ACTION_GET_PLUGIN_DESCRIPTION);
            getIntent.setComponent(cn);
            ApiIntentHelper.putPerson(getIntent, null);
            ApiIntentHelper.putReplyTo(getIntent, replyTo);
            startService(getIntent);
        }
    }

    private void handleReceivedPluginDescription(Intent intent) {
        PluginDescription pluginDescription = ApiIntentHelper.getPluginDescription(intent);
        if (null == pluginDescription) {
            return;
        }

        // Get list of plugin descriptions
        int token = intent.getIntExtra(EXTRA_TOKEN, -1);
        PluginHolder pluginHolder = mPluginDescriptions.get(token);
        if (null == pluginHolder) {
            Log.w(TAG, "Bad token: " + Integer.toString(token));
            return;
        }

        // Add plugin descriptions to list of plugin descriptions
        pluginHolder.pluginDescriptions.add(pluginDescription);
    }

    private void handleCleanPluginDescriptions() {
        long now = System.currentTimeMillis();

        final int N = mPluginDescriptions.size();
        for (int i = N - 1; i > 0; i--) {
            int token = mPluginDescriptions.keyAt(i);
            PluginHolder pluginHolder = mPluginDescriptions.valueAt(i);

            int size = pluginHolder.pluginDescriptions.size();
            if (pluginHolder.total <= 0) {
                // TODO Remove this plugin holder
            } else if (size >= pluginHolder.total) {
                // TODO Send plugin descriptions
            } else if (now > pluginHolder.start + SEND_PLUGIN_DESCRIPTION_DELAY_MS) {
                // TODO Send plugin descriptions
            }
        }
    }
}
