
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:wifi_hunter/wifi_hunter_result.dart';

class WiFiHunter {
  static const MethodChannel _channel = MethodChannel('wifi_hunter');

  static Future<WiFiHunterResult?> get huntWiFiNetworks async {
    final Map<String, dynamic> networks = Map<String, dynamic>.from(await _channel.invokeMethod('huntWiFiNetworks'));

    WiFiHunterResult result = WiFiHunterResult();

    List<String> ssids = List<String>.from(networks["SSIDS"]);
    List<String> bssids = List<String>.from(networks["BSSIDS"]);
    List<String> capabilities = List<String>.from(networks["CAPABILITES"]);
    List<int> frequencies = List<int>.from(networks["FREQUENCIES"]);
    List<int> levels = List<int>.from(networks["LEVELS"]);
    List<int> channelWidths = List<int>.from(networks["CHANNELWIDTHS"]);
    List<int> timestamps = List<int>.from(networks["TIMESTAMPS"]);

    for (var i = 0; i < ssids.length; i++)
      result.results.add(WiFiHunterResultEntry(ssids[i], bssids[i], capabilities[i], frequencies[i], levels[i], channelWidths[i], timestamps[i]));

    return result;
  }
}
