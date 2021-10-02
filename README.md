# wifi_hunter

A flutter package to hunt down info from all WiFi APs around you.

<p><a href="https://pub.dartlang.org/packages/wifi_hunter/" rel="nofollow"><img alt="version" src="https://img.shields.io/pub/v/wifi_hunter.svg?style=flat-square" style="max-width:100%;"></a>
<a href="https://github.com/klingens13/wifi_hunter/blob/master/LICENSE"><img alt="BSD License" src="https://img.shields.io/github/license/klingens13/wifi_hunter.svg?style=flat-square" style="max-width:100%;"></a>
<a href="http://makeapullrequest.com" rel="nofollow"><img alt="PRs Welcome" src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square" style="max-width:100%;"></a></p>

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
      print(wiFiHunterResult.results.SSID);
      print(wiFiHunterResult.results.BSSID);
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

  * [string] SSIDs
  * [string] BSSIDs
  * [string] Signal strength
  * [int] Frequencies
  * [int] Capabilities
  * [int] Channel widths
  * [int] Timestamps (of information retrieval)
  
    ... of all WiFi APs in reach.
  
### Wasn't this package discontinued?

Yes, if you remember this package as discontinued, you're not wrong! <br>
But in honor of <b>Hacktoberfest 2021</b>, I decided to completely recreate this package from ground up, so if anyone has a use for it, enjoy 😄