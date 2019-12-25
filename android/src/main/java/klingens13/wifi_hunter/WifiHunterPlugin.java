package klingens13.wifi_hunter;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

/** WiFiHunterPlugin */
public class WiFiHunterPlugin implements FlutterPlugin, MethodCallHandler {

  private static WiFiHunterPlugin instance;
  WifiManager wifiManager;
  WiFiReciever WiFiReciever;
  private Context context;
  private Object initializationLock = new Object();

  public static void registerWith(PluginRegistry.Registrar registrar) {
    if (instance == null) {
      instance = new WiFiHunterPlugin();
    }
    instance.onAttachedToEngine(registrar.context(), registrar.messenger());
  }

  @Override
  public void onAttachedToEngine(FlutterPluginBinding binding) {
    onAttachedToEngine(
            binding.getApplicationContext(), binding.getFlutterEngine().getDartExecutor());
  }

  public void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {
    synchronized (initializationLock) {
      this.context = applicationContext;
      Log.i("TAG","HELLO1");
      final MethodChannel channel = new MethodChannel(messenger,"wifi_hunter");
      channel.setMethodCallHandler(this);
    }
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    WiFiReciever = new WiFiReciever(wifiManager, result);
    wifiManager.startScan();
    Log.i("TAG","HELLO2");

    if ("huntWiFis".equals(call.method)) {
      wifiManager.startScan();
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding flutterPluginBinding) {

  }
}
