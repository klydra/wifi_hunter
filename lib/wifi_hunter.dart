import 'dart:async';

import 'package:flutter/services.dart';

class WiFiHunter {
  static const MethodChannel _channel = const MethodChannel('wifi_hunter');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<WiFiInfoWrapper> get huntRequest async {
    final Map<dynamic, dynamic> data = await _channel.invokeMethod('huntWiFis');

    WiFiInfoWrapper wifiInfoWrapper = new WiFiInfoWrapper.withMap(data);
    return wifiInfoWrapper;
  }
}

class WiFiInfoWrapper {
  List<dynamic> _bssids;
  List<dynamic> _ssids;
  List<dynamic> _capabilities;
  List<dynamic> _singalStrengths;
  List<dynamic> _frequencies;
  List<dynamic> _channelWidths;

  WiFiInfoWrapper();

  WiFiInfoWrapper.withMap(Map<dynamic, dynamic> nativeInfo) {
    if (nativeInfo != null) {
      this._bssids = nativeInfo["BSSIDS"];
      this._ssids = nativeInfo["SSIDS"];
      this._capabilities = nativeInfo["CAPABILITES"];
      this._singalStrengths = nativeInfo["SIGNALSTRENGTHS"];
      this._frequencies = nativeInfo["FREQUENCIES"];
      this._channelWidths = nativeInfo["CHANNELWIDTHS"];
    }
  }

  List<dynamic> get bssids {
    return this._bssids;
  }

  List<dynamic> get ssids {
    return this._ssids;
  }

  List<dynamic> get capabilities {
    return this._capabilities;
  }

  List<dynamic> get signalStrengths {
    return this._singalStrengths;
  }

  List<dynamic> get frequenies {
    return this._frequencies;
  }

  List<dynamic> get channelWidths {
    return this._channelWidths;
  }
}
