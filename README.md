# wifi_hunter

A flutter package to hunt down info from all WiFi APs around you.

<p><a href="https://pub.dartlang.org/packages/wifi_hunter/" rel="nofollow"><img alt="version" src="https://img.shields.io/pub/v/wifi_hunter.svg?style=flat-square" style="max-width:100%;"></a>
<a href="https://github.com/klingens13/wifi_hunter/blob/master/LICENSE"><img alt="BSD License" src="https://img.shields.io/github/license/klingens13/wifi_hunter.svg?style=flat-square" style="max-width:100%;"></a>
<a href="http://makeapullrequest.com" rel="nofollow"><img alt="PRs Welcome" src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square" style="max-width:100%;"></a></p>

## Getting Started
The plugin only supports the android platform, and it's very unlikely to launch on iOS, because Apple refuses to provide permissions for that, so don't wait for that to come around.
Credit where credit is due : This package is pretty much based on the __wifi_info_plugin__ from __@VTechJm__, which you can check out here __(https://github.com/VTechJm/wifi_info_plugin)__.

```dart
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
      print("- " + _wifiObject.ssids[i]);
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
          mainAxisSize: MainAxisSize.min,
          child: Column (
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
```

## Functionality and Features
Here is what infos you can get by using this package :

  * SSIDs ...                         ```_wifiObject.ssids```
  * BSSIDs ...                        ```_wifiObject.bssids```
  * Signal strength ...               ```_wifiObject.signalStrengths```
  * Frequencies ...                   ```_wifiObject.frequenies```
  * Capabilities ...                  ```_wifiObject.capabilities```
  * Channel width ...                 ```_wifiObject.channelWidths```
  
    ... of all WiFi APs in reach.
    

The available __SSIDs__, __BSSIDs__ and __capabilities__ ___(= protocols, ex. EES, WPA2...)___ are returned as Java ```List<String>```,
while the __frequencies__ and __signal strengths__ ___(dBm)___ are returned as ```List<Integer>```.


If you want to run a scan again just execute ```scanHandler();```, and your ```_wifiObject.``` results will be refreshed.
Scans, for usual, can run every 3 seconds.


NOTE : If you don't need to retrieve the WiFi channel width, you can still use v1.0.2 and implement it in applications with a min. SDK version of 16 without any disadvantages.


If anyone wants to, _Pull requests are welcome_ 😉 






