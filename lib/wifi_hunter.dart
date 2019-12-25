import 'dart:async';

import 'package:flutter/services.dart';

class WifiHunter {
  static const MethodChannel _channel = const MethodChannel('wifi_hunter');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<WifiInfoWrapper> get wifiDetails async {
    final Map<dynamic, dynamic> data =
    await _channel.invokeMethod('huntWiFis');

    WifiInfoWrapper wifiInfoWrapper = new WifiInfoWrapper.withMap(data);
    return wifiInfoWrapper;
  }
}

class WifiInfoWrapper {
  List<String> _bssids;
  List<String> _ssids;
  List<String> _capabilities;
  List<int> _singalStrengths;
  List<int> _frequencys;

  WifiInfoWrapper();

  WifiInfoWrapper.withMap(Map<dynamic, dynamic> nativeInfo) {
    if (nativeInfo != null) {
      this._bssids = nativeInfo["BSSIDS"];
      this._ssids = nativeInfo["SSIDS"];
      this._capabilities = nativeInfo["CAPABILITES"];
      this._singalStrengths = nativeInfo["SIGNALSTRENGTHS"];
      this._frequencys = nativeInfo["FREQUENCYS"];
    }
  }

  List<String> get BSSIDs {
    return this._bssids;
  }

  List<String> get SSIDs {
    return this._ssids;
  }

  List<String> get capabilities {
    return this._capabilities;
  }

  List<int> get signalStrengths {
    return this._singalStrengths;
  }

  List<int> get frequencys {
    return this._frequencys;
  }
}
