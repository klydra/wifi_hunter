package klingens13.wifi_hunter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;


class WiFiReciever extends BroadcastReceiver {
    WifiManager wifiManager;
    MethodChannel.Result result;
    List<String> BSSIDs;
    List<String> SSIDs;
    List<String> capabilities;
    List<Integer> frequencys;
    List<Integer> levels;

    public WiFiReciever(WifiManager wifiManager, @NonNull MethodChannel.Result result) {
        this.wifiManager = wifiManager;
        this.result = result;
        Log.i("TAG", "HELLO3");
        wifiManager.startScan();
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("TAG", "HELLO4");
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

            Map<String, Object> data = new HashMap();
            data.put("SSIDS", SSIDs);
            data.put("BSSIDS", BSSIDs);
            data.put("CAPABILITES", capabilities);
            data.put("FREQUENCY", frequencys);
            data.put("SIGNALSTRENGTHS", levels);

            Log.i("T", "DATA : " + data.toString());
            result.success(data);
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