import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:wifi_hunter/wifi_hunter.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  WifiInfoWrapper _wifiObject;

  @override
  void initState() {
    initPlatformState();
    super.initState();
  }

  Future<void> initPlatformState() async {
    WifiInfoWrapper wifiObject;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      wifiObject = await WifiHunter.wifiDetails;
    } on PlatformException {}
    //if (!mounted) return;

    setState(() {
      _wifiObject = wifiObject;
    });
  }

  @override
  Widget build(BuildContext context) {
    WifiHunter.wifiDetails;
    if (_wifiObject != null) {
      List<String> ssids = _wifiObject.SSIDs;
      String result = "Results (availible SSIDs) : | ";
      for (var i = 0; i < ssids.length; i++) {
        result += ssids[i] + " | ";
      }

      return MaterialApp(
        home: Scaffold(
          appBar: AppBar(
            title: const Text('WiFiHunter Example App'),
          ),
          body: Center(
              child: Text(result)
          ),
        ),
      );
    } else {
      WifiHunter.wifiDetails;
      return MaterialApp(
        home: Scaffold(
          appBar: AppBar(
            title: const Text('WiFiHunter Example App'),
          ),
          body: Center (
            child: Text ("Scanning... Please wait..."),
          ),
        ),
      );
    }
  }
}
