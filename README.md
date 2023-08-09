# wifi_hunter

A flutter package to hunt down info from all WiFi APs around you.

<p><a href="https://pub.dartlang.org/packages/wifi_hunter/" rel="nofollow"><img alt="version" src="https://img.shields.io/pub/v/wifi_hunter.svg?style=flat-square" style="max-width:100%;"></a>
<a href="https://github.com/klingens13/wifi_hunter/blob/master/LICENSE"><img alt="BSD License" src="https://img.shields.io/github/license/klingens13/wifi_hunter.svg?style=flat-square" style="max-width:100%;"></a>
<a href="https://makeapullrequest.com" rel="nofollow"><img alt="PRs Welcome" src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square" style="max-width:100%;"></a></p>

## Demonstration

![demo.gif](demo.gif)

## Getting Started

```dart

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:async';

import 'package:wifi_hunter/wifi_hunter.dart';
import 'package:wifi_hunter/wifi_hunter_result.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  WiFiHunterResult wiFiHunterResult = WiFiHunterResult();
  
  Future<void> huntWiFis() async {
    try {
      wiFiHunterResult = (await WiFiHunter.huntWiFiNetworks)!;
    } on PlatformException catch (exception) {
      print(exception.toString());
    }
    
    for (var i = 0; i < wiFiHunterResult.results.length) {
      print(wiFiHunterResult.results.ssid);
      print(wiFiHunterResult.results.bssid);
      print(wiFiHunterResult.results.capabilities);
      print(wiFiHunterResult.results.frequency);
      print(wiFiHunterResult.results.level);
      print(wiFiHunterResult.results.channelWidth);
      print(wiFiHunterResult.results.timestamp);
    }

    if (!mounted) return;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
      appBar: AppBar(
        title: const Text('WiFiHunter example app'),
      ),
      body: ElevatedButton(
        onPressed: () => huntWiFis(),
        child: const Text('Hunt Networks')
      )
    );
  }
}


```

## Functionality and Features
Here is what infos you can get by using this package :

  * `String[]` SSIDs
  * `String[]` BSSIDs
  * `String[]` Signal strength
  * `int[]` Frequencies
  * `int[]` Capabilities
  * `int[]` Channel widths
  * `long[]` Timestamps (of information retrieval)
  
    ... of all WiFi APs in reach.

## Running the sample
When running the sample app, **make sure you've granted it the location permissions** available in the system preferences, as there is currently **no permission dialog implemented** in the example app. <br>
The permission settings are available under `Settings` > `Apps` > `wifi_hunter_example` > `Permissions` > `Location` > `Allow only while using the app`

## Android WiFi Scan Throttling
Starting with **Android 8.0** and up, the operating system severely **limits the number of WiFi scans**.<br>*Please keep this limit in mind when implementing your scan issuing logic.*<br>Find out more about these limits on [developer.android.com](https://developer.android.com/guide/topics/connectivity/wifi-scan#wifi-scan-throttling).

## Debugging
You can get a good overview of pretty much all related log entries by searching for `wifi` in the **Logcat output**.