
package com.l2a.storeelseapi;

public class SeIntent {
    private SeIntent() {
    }

    /**
     * Action to request storage file names from the main application. This is
     * used internally by the {@link SeStorageManager} to initialize itself.
     * 
     * @see #EXTRA_PACKAGE_NAME
     * @see #EXTRA_PREFS_DIR
     */
    public static final String ACTION_GET_STORAGE_FILENAMES = "com.l2a.storeelseapi.action.GET_STORAGE_FILENAMES";

    /**
     * This extra is included in the response to
     * {@link #ACTION_GET_STORAGE_FILENAMES}. It defines the path to the
     * preferences file to be used for this plugin.
     */
    public static final String EXTRA_PREFS_DIR = "com.l2a.storeelseapi.extra.PREFS_DIR";

    public static final String EXTRA_PACKAGE_NAME = "com.l2a.storeelseapi.extra.PACKAGE_NAME";
}
