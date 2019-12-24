package klingens13.wifi_hunter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


class WiFiReciever extends BroadcastReceiver {
    WifiManager wifiManager;
    List<String> BSSIDs;
    List<String> SSIDs;
    List<String> capabilities;
    List<Integer> frequencys;
    List<Integer> levels;

    public WiFiReciever(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            List<ScanResult> wifiList = wifiManager.getScanResults();
            BSSIDs.clear();
            SSIDs.clear();
            capabilities.clear();
            frequencys.clear();
            levels.clear();
            for (ScanResult scanResult : wifiList) {
                BSSIDs.add(scanResult.BSSID);
                SSIDs.add(scanResult.SSID);
                capabilities.add(scanResult.capabilities);
                frequencys.add(scanResult.frequency);
                levels.add(scanResult.level);
            }

        } else {
            Log.println(Log.INFO, "WiFiHunter - Reciever", "No results available!");
        }
    }

    public List<String> getBSSIDs() {
        return BSSIDs;
    }

    public List<String> getSSIDs() {
        return SSIDs;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public List<Integer> getFrequencys() {
        return frequencys;
    }

    public List<Integer> getLevels() {
        return levels;
    }
}
