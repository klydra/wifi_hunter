# wifi_hunter

Wifi info wrapper android plugin

## Getting Started
This Plugin is currently only supports android. IOS implementation to be release soon.
Import the plugin.
Listed are all the supported getter methods to query and retrieve Network Information on your android device.

Below is an Example code of using the plugin in  flutter application to retrieve android only Network info.
The WifiInfoWrapper class contains methods for some of the most useful Network infomation to be requested.

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
  WifiInfoWrapper _wifiObject;


  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    WifiInfoWrapper wifiObject;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      wifiObject = await  WiFiHunterPlugin.wifiDetails;

    }
    on PlatformException{

    }
    if (!mounted) return;

    setState(() {

      _wifiObject = wifiObject;
    });
  }

  @override
  Widget build(BuildContext context) {
   String ipAddress = _wifiObject!=null?_wifiObject.ipAddress.toString():"ip";
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on:'+ ipAddress),
        ),
      ),
    );
  }
}
```
Below are valid getters on the WifiWrapper Class at instantiation

  * ipAddress
  * routerIp
  * dns1
  * dns2
  * bssId
  * ssid
  * macAddress
  * linkSpeed
  * signalStrength
  * frequency
  * networkId
  * connectionType
  * isHiddenSSid









