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
    WifiManager wifiManager;
    MethodCall call;
    MethodChannel.Result result;

    public WiFiReciever(Context context, @NonNull MethodCall call, @NonNull final MethodChannel.Result result) {
        this.context = context;
        this.call = call;
        this.result = result;

        Log.i("TAG", "HELLO3");
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

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

                    for (ScanResult scanResult : wifiList) {
                        BSSIDs.add(scanResult.BSSID);
                        SSIDs.add(scanResult.SSID);
                        capabilities.add(scanResult.capabilities);
                        frequencies.add(scanResult.frequency);
                        levels.add(scanResult.level);
                    }

                    Map<String, Object> data = new HashMap();
                    data.put("SSIDS", SSIDs);
                    data.put("BSSIDS", BSSIDs);
                    data.put("CAPABILITES", capabilities);
                    data.put("FREQUENCIES", frequencies);
                    data.put("SIGNALSTRENGTHS", levels);

                    Log.i("T", "DATA : " + data.toString());
                    result.success(data);
                } else {
                    Log.i("WiFiHunter - Reciever", "No results available!");
                }
            }
        };
        context.registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        if ("huntWiFis".equals(call.method)) {
            wifiManager.startScan();
            Log.i("TAG", "HELLO4");
        } else {
            result.notImplemented();
        }
    }

}