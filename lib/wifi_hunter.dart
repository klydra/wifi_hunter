import 'dart:async';

import 'package:flutter/services.dart';

class WifiHunter {
  static const MethodChannel _channel = const MethodChannel('wifi_hunter');
  int scanSpeed;

  const WifiHunter({
    this.scanSpeed = 3000,
  });

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
