
package com.l2a.api;

import android.app.PendingIntent;

import com.l2a.api.init.PluginDescription;

public class ApiIntent {
    private static final String AUTHORITY = "com.l2a.api.intent";
    private static final String ACTION = AUTHORITY + ".action";
    private static final String EXTRA = AUTHORITY + ".extra";

    private ApiIntent() {
    }

    /**
     * Action to request storage file names from the main application. This is
     * used internally by the {@link SeStorageManager} to initialize itself.
     * 
     * @see #EXTRA_PACKAGE_NAME
     * @see #EXTRA_PREFS_DIR
     */
    public static final String ACTION_GET_STORAGE_FILENAMES = ACTION + ".GET_STORAGE_FILENAMES";

    /**
     * This extra is included in the response to
     * {@link #ACTION_GET_STORAGE_FILENAMES}. It defines the path to the
     * preferences file to be used for this plugin.
     */
    public static final String EXTRA_PREFS_DIR_PATH = EXTRA + ".PREFS_DIR_PATH";
    public static final String EXTRA_PACKAGE_NAME = EXTRA + ".PACKAGE_NAME";

    /**
     * This action is used to query plugin so they can describe themselves. To
     * be considered a plugin this action must be implemented in the
     * AndroidManifest.xml.
     */
    public static final String ACTION_GET_PLUGIN_DESCRIPTION = ACTION + ".GET_PLUGIN_DESCRIPTION";

    /**
     * This is a {@link PluginDescription}. Usually stored in an {@link Intent}.
     * use the helper methods on {@link ApiIntentHelper} to access the
     * PluginDescription.
     * 
     * @see ApiIntentHelper#getPluginDescription(android.content.Intent)
     * @see ApiIntentHelper#putPluginDescription(android.content.Intent)
     */
    public static final String EXTRA_PLUGIN_DESCRIPTION = EXTRA + ".PLUGIN_DESCRIPTION";

    /**
     * This holds a {@link PendingIntent} that should be used a place to reply
     * to.
     * 
     * @see ApiIntent#ACTION_GET_PLUGIN_DESCRIPTION
     */
    public static final String EXTRA_REPLY_TO = EXTRA + ".REPLY_TO";
    public static final String EXTRA_PERSON = EXTRA + ".PERSON";
}
