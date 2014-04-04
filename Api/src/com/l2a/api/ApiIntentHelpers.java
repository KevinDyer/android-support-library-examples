package com.l2a.api;

import android.content.Intent;

import com.l2a.api.init.PluginDescription;

public class ApiIntentHelpers {
    private static final String AUTHORITY = ApiIntentHelpers.class.getName();
    private static final String EXTRA = AUTHORITY + ".extra";
    public static final String EXTRA_PLUGIN_DESCRIPTION = EXTRA + ".PLUGIN_DESCRIPTION";

    public static Intent putPluginDescription(Intent intent, PluginDescription pluginDescription) {
        return intent.putExtra(EXTRA_PLUGIN_DESCRIPTION, pluginDescription);
    }

}
