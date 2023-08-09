package klydra.wifi_hunter;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** WiFiHunterPlugin */
public class WiFiHunterPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private WifiManager wifiManager;
  private Result result;
  private MethodCall call;
  private Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "wifi_hunter");
    channel.setMethodCallHandler(this);

    context = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    this.result = result;
    this.call = call;

    if (call.method.equals("huntWiFiNetworks")) {
      wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

      if (!wifiManager.isWifiEnabled()) wifiManager.setWifiEnabled(true);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        scanWithExecutor();
      } else {
        scanWithIntent();
      }
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  public void scanWithIntent() {
    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
      @TargetApi(Build.VERSION_CODES.M)
      @Override
      public void onReceive(Context c, Intent intent) {
        boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
        if (success) {
          processScanResults();
        } else {
          result.error("2", "WiFi Scan failed. Most likely your app is being throttled. Check out https://developer.android.com/guide/topics/connectivity/wifi-scan#wifi-scan-throttling for more information.", null);
        }
      }
    };


    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
    context.registerReceiver(wifiScanReceiver, intentFilter);
    wifiManager.startScan();
  }

  @RequiresApi(api = Build.VERSION_CODES.R)
  public void scanWithExecutor() {

      WifiManager.ScanResultsCallback scanResultsCallback = new WifiManager.ScanResultsCallback() {
        @Override
        public void onScanResultsAvailable() {
          processScanResults();
          wifiManager.unregisterScanResultsCallback(this);
        }
      };


    Executor executor = ContextCompat.getMainExecutor(context);
    wifiManager.registerScanResultsCallback(executor, scanResultsCallback);
    wifiManager.startScan();
  }

  public void processScanResults() {
    List<ScanResult> results = wifiManager.getScanResults();

    List<String> BSSIDs = new ArrayList<String>();
    List<String> SSIDs = new ArrayList<String>();
    List<String> capabilities = new ArrayList<String>();
    List<Integer> frequencies = new ArrayList<Integer>();
    List<Integer> levels = new ArrayList<Integer>();
    List<Integer> channelWidths = new ArrayList<Integer>();
    List<Long> timestamps = new ArrayList<Long>();

    for (ScanResult scanResult : results) {
      SSIDs.add(scanResult.SSID);
      BSSIDs.add(scanResult.BSSID);
      capabilities.add(scanResult.capabilities);
      frequencies.add(scanResult.frequency);
      levels.add(scanResult.level);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        channelWidths.add(scanResult.channelWidth);
      }
      timestamps.add(scanResult.timestamp);
    }

    Map<String, Object> data = new HashMap();
    data.put("SSIDS", SSIDs);
    data.put("BSSIDS", BSSIDs);
    data.put("CAPABILITES", capabilities);
    data.put("FREQUENCIES", frequencies);
    data.put("LEVELS", levels);
    data.put("CHANNELWIDTHS", channelWidths);
    data.put("TIMESTAMPS", timestamps);

    try {
      result.success(data);
    } catch (Exception e) {
      System.out.println("Failed to send results to Flutter: " + e.getMessage());
    }
  }
}
