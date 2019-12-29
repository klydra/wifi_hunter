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
    initPlatformState();
    super.initState();
  }
  Future<void> initPlatformState() async {
    WiFiInfoWrapper wifiObject;

    try {
      wifiObject = await WiFiHunter.huntRequest;
    } on PlatformException {}

    setState(() {
      _wifiObject = wifiObject;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (_wifiObject != null) {
      print("WiFi Results (SSIDs) : ");
      for (var i = 0; i < _wifiObject.ssids.length; i++) {
        print("- " + _wifiObject.ssids[i]);
      }
    }

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('WiFiHunter Example App'),
        ),
        body: Center (
          child: Text ("Scanning... Please check Log for results..."),
        ),
      ),
    );
  }
}
