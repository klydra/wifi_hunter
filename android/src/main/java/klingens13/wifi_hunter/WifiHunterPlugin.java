package klingens13.wifi_hunter;

import android.content.Context;
import android.net.wifi.WifiManager;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** WifiHunterPlugin */
class WiFiHunterPlugin implements FlutterPlugin, MethodCallHandler {

  private WifiManager wifiManager;
  WiFiReciever WiFiReciever;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "wifi_hunter");
    channel.setMethodCallHandler(new WiFiHunterPlugin());
    wifiManager = (WifiManager)flutterPluginBinding.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "wifi_hunter");
    channel.setMethodCallHandler(new WiFiHunterPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    WiFiReciever = new WiFiReciever(wifiManager);
    wifiManager.startScan();

    switch (call.method) {
      case "getPlatformVersion": {
        result.success(android.os.Build.VERSION.RELEASE);
      }
      break;
      case "huntWiFis": {
        Map<String, Object> data = new HashMap();
        data.put("SSIDS", WiFiReciever.getSSIDs());
        data.put("BSSIDS", WiFiReciever.getBSSIDs());
        data.put("CAPABILITES", WiFiReciever.getCapabilities());
        data.put("SIGNALSTRENGTHS", WiFiReciever.getLevels());
        data.put("FREQUENCY", WiFiReciever.getFrequencys());

        result.success(data);
      }
      break;
      default:
        result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding flutterPluginBinding) {

  }
}
