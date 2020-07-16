package klingens13.wifi_hunter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class WiFiReciever {

    Context context;
    MethodCall call;
    MethodChannel.Result result;
    boolean resultSubmitted = false;

    public WiFiReciever(Context context, @NonNull MethodCall call, @NonNull final MethodChannel.Result result) {
        this.context = context;
        this.call = call;
        this.result = result;

        final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                    List<ScanResult> wifiList = wifiManager.getScanResults();

                    List<String> BSSIDs = new ArrayList<String>();
                    List<String> SSIDs = new ArrayList<String>();
                    List<String> capabilities = new ArrayList<String>();
                    List<Integer> frequencies = new ArrayList<Integer>();
                    List<Integer> levels = new ArrayList<Integer>();
                    List<Integer> channelWidth = new ArrayList<Integer>();

                    for (ScanResult scanResult : wifiList) {
                        BSSIDs.add(scanResult.BSSID);
                        SSIDs.add(scanResult.SSID);
                        capabilities.add(scanResult.capabilities);
                        frequencies.add(scanResult.frequency);
                        levels.add(scanResult.level);
                        channelWidth.add(scanResult.channelWidth);
                    }

                    Map<String, Object> data = new HashMap();
                    data.put("SSIDS", SSIDs);
                    data.put("BSSIDS", BSSIDs);
                    data.put("CAPABILITES", capabilities);
                    data.put("FREQUENCIES", frequencies);
                    data.put("SIGNALSTRENGTHS", levels);
                    data.put("CHANNELWIDTHS", channelWidth);

                    if (!resultSubmitted) {
                        result.success(data);
                        resultSubmitted = true;
                    }
                } else {
                    Log.i("WiFiHunter - Reciever", "No results available!");
                    result.error("404","No results available!", "No WiFi APs could be found");
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, intentFilter);

        if ("huntWiFis".equals(call.method)) {
            resultSubmitted = false;
            wifiManager.startScan();
        } else {
            result.notImplemented();
        }



    }
}