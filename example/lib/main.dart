import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'dart:async';
import 'package:wifi_hunter/wifi_hunter.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  WiFiInfoWrapper _wifiObject;

  @override
  void initState() {
    scanWiFi();
    super.initState();
  }

  Future<WiFiInfoWrapper> scanWiFi() async {
    WiFiInfoWrapper wifiObject;

    try {
      wifiObject = await WiFiHunter.huntRequest;
    } on PlatformException {}

    return wifiObject;
  }

  Future<void> scanHandler() async {
    _wifiObject = await scanWiFi();
    print("WiFi Results (SSIDs) : ");
    for (var i = 0; i < _wifiObject.ssids.length; i++) {
      print("- " + _wifiObject.channelWidths[i]);
    }
  }

  @override
  Widget build(BuildContext context) {
    scanHandler();

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('WiFiHunter Example App'),
        ),
        body: Center (
          child: Column (
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Text ("Scanning... Please check Log for results..."),
              FlatButton (
                child: Text ("ReScan (after prev. scan is finished; await...)"),
                onPressed: () => scanHandler(),
              )
            ],
          )
        )
      ),
    );
  }
}
