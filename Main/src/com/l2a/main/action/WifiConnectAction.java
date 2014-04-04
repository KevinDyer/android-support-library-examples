
package com.l2a.main.action;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.l2a.main.widget.ActionAdapter.Action;

public class WifiConnectAction implements Action {
    private final Context mContext;

    public WifiConnectAction(Context context) {
        mContext = context;
    }

    @Override
    public CharSequence getTitle() {
        return "Wifi Connect";
    }

    @Override
    public CharSequence getSummary() {
        CharSequence summary = null;

        int state = getWifiManager().getWifiState();
        if (WifiManager.WIFI_STATE_ENABLED == state) {
            summary = "Connected";
        } else if (WifiManager.WIFI_STATE_ENABLING == state) {
            summary = "Enabling";
        } else if (WifiManager.WIFI_STATE_DISABLED == state) {
            summary = "Disabling";
        } else if (WifiManager.WIFI_STATE_DISABLING == state) {
            summary = "Disabling";
        } else {
            summary = "Unknown";
        }

        return summary;
    }

    @Override
    public void execute() {
        enableWifi();

        // TODO Perform action based on state
        WifiManager wifiManager = getWifiManager();

        // TODO Enable wifi
        boolean wifiEnabled = wifiManager.setWifiEnabled(true);
        if (!wifiEnabled) {
            return;
        }

        // TODO Select network
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (0 < configuredNetworks.size()) {
            int netId = configuredNetworks.get(0).networkId;
            boolean enableNetwork = wifiManager.enableNetwork(netId, true);
            // TODO Handle fail
        } else {
            Intent intent = new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void enableWifi() {
        WifiManager wifiManager = getWifiManager();
        if (wifiManager.isWifiEnabled()) {
            enableNetwork();
        } else {
            wifiManager.setWifiEnabled(true);
            // TODO Listen for wifi to be enabled
        }
    }

    private void enableNetwork() {
        WifiManager wifiManager = getWifiManager();

        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (null == configuredNetworks) {
            selectNetwork();
        } else if (0 >= configuredNetworks.size()) {
            selectNetwork();
        } else {
            WifiConfiguration wifiConfiguration = configuredNetworks.get(0);
            wifiManager.enableNetwork(wifiConfiguration.networkId, true);

            checkWalledGarden();
        }
    }

    private void selectNetwork() {
        Intent intent = new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        // TODO Listen for when a network is selected
    }

    private void checkWalledGarden() {
        // TODO Auto-generated method stub

    }

    private WifiManager getWifiManager() {
        return (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }

    private void startActivity(Intent intent) {
        mContext.startActivity(intent);
    }
}
