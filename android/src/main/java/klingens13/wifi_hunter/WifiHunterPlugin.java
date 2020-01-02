package klingens13.wifi_hunter;


import android.content.Context;
import android.net.wifi.WifiManager;

import io.flutter.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * WiFiHunterPlugin
 */
public class WiFiHunterPlugin implements MethodCallHandler {

    WiFiReciever WiFiReciever;
    static Context context;

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "wifi_hunter");
        context = registrar.context();
        channel.setMethodCallHandler(new WiFiHunterPlugin());
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        WiFiReciever = new WiFiReciever(context, call, result);
    }
}
