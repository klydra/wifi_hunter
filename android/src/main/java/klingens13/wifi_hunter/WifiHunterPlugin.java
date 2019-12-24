package klingens13.wifi_hunter;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** WifiHunterPlugin */
public class WifiHunterPlugin implements FlutterPlugin, MethodCallHandler {

  private Handler handler = new Handler();
  private WifiManager wifiManager;
  WiFiReciever WiFiReciever;
  ArrayList<List> finalScanResults;
  int scanDelay = 3000; //-- Scan Delay in ms (default: 3000ms; Limitations in Android OS won't allow shorter anyways...)

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "wifi_hunter");
    channel.setMethodCallHandler(new WifiHunterPlugin());
    wifiManager = (WifiManager)flutterPluginBinding.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "wifi_hunter");
    channel.setMethodCallHandler(new WifiHunterPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    wifiManager.startScan();
    handler.postDelayed(performScan, scanDelay);
    result.success(finalScanResults);
  }

  Runnable performScan = new Runnable() {
    @Override
    public void run() {
      WiFiReciever = new WiFiReciever(wifiManager);
      wifiManager.startScan();
    }
  };

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
  }
}
