
package com.l2a.main.plugin;

public class PluginManager {
    private static PluginManager sInstance = null;

    public static void initialize() {
        if (null != sInstance) {
            return;
        }

        sInstance = new PluginManager();
    }

    public static void close() {
        sInstance = null;
    }

}
