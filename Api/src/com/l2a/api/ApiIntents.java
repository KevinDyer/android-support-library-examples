
package com.l2a.api;

public class ApiIntents {
    private static final String AUTHORITY = "com.l2a.api.intent";
    private static final String ACTION = AUTHORITY + ".action";
    private static final String EXTRA = AUTHORITY + ".extra";

    private ApiIntents() {
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

    public static final String ACTION_GET_PLUGIN_DESCRIPTION = ACTION + ".GET_PLUGIN_DESCRIPTION";
    public static final String EXTRA_REPLY_TO = EXTRA + ".REPLY_TO";
}
