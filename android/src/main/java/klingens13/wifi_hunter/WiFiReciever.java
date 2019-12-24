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
    List<String> BSSID;
    List<String> SSID;
    List<String> capabilities;
    List<Integer> frequency;
    List<Integer> level;
    ArrayList<List> finalScanResults;

    public WiFiReciever(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            List<ScanResult> wifiList = wifiManager.getScanResults();
            for (ScanResult scanResult : wifiList) {
                BSSID.add(scanResult.BSSID);
                SSID.add(scanResult.SSID);
                capabilities.add(scanResult.capabilities);
                frequency.add(scanResult.frequency);
                level.add(scanResult.level);
            }
            finalScanResults.add(BSSID);
            finalScanResults.add(SSID);
            finalScanResults.add(capabilities);
            finalScanResults.add(frequency);
            finalScanResults.add(level);

        } else {
            Log.println(1, "WiFiHunter - Reciever", "No results available!");
        }
    }
}
