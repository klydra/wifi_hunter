# wifi_hunter

A flutter package to hunt down info from all WiFi APs around you.

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
  /* Defining the Info Wrapper */
  WiFiInfoWrapper _wifiObject;                                      


  @override
  void initState() {
    super.initState();
    /* Initializing the Info-Wrapper */
    initPlatformState();                                            
  }

  Future<void> initPlatformState() async {
    WiFiInfoWrapper wifiObject;
    
    /* Platform Exception may occour : try/catch */
    try {
      wifiObject = await WiFiHunter.huntRequest;
    } on PlatformException {}

    /* syncing the Info-Wrappers */
    setState(() {
      _wifiObject = wifiObject;
    });
  }

  @override
  Widget build(BuildContext context) {
    /* Checking if Response was given already */
    if (_wifiObject != null) {
      /* Printing the responses */
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
```

## Functionality and Features
Here is what infos you can get by using this package :

  * SSIDs ...                         ```_wifiObject.ssids```
  * BSSIDs ...                        ```_wifiObject.bssids```
  * Signal strength ...               ```_wifiObject.signalStrength```
  * Frequencies ...                   ```_wifiObject.frequenies```
  * Capabilities ...                  ```_wifiObject.capabilities```
  
    ... of all WiFi APs in reach.
    

The available __SSIDs__, __BSSIDs__ and __capabilities__ ___(= protocols, ex. EES, WPA2...)___ are returned as Java ```List<String>```,
while the __frequencies__ and __signal strengths__ ___(dBm)___ are returned as ```List<Integer>```.


If you want to run a scan again just execute ```initPlatformState();```, and your ```_wifiObject.``` results will be refreshed.
Scans, for usual, can run every 3 seconds.

If anyone wants to, _Pull requests are welcome_ 😉 






