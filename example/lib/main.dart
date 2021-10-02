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
  Color huntButtonColor = Colors.lightBlue;

  Future<void> huntWiFis() async {
    setState(() => huntButtonColor = Colors.red);

    try {
      wiFiHunterResult = (await WiFiHunter.huntWiFiNetworks)!;
    } on PlatformException catch (exception) {
      print(exception.toString());
    }

    if (!mounted) return;

    setState(() => huntButtonColor = Colors.lightBlue);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('WiFiHunter example app'),
        ),
        body: SingleChildScrollView(
          scrollDirection: Axis.vertical,
          physics: const BouncingScrollPhysics(),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisSize: MainAxisSize.min,
            children: [
              Container(
                margin: const EdgeInsets.symmetric(vertical: 20.0),
                child: ElevatedButton(
                  style: ButtonStyle(backgroundColor: MaterialStateProperty.all<Color>(huntButtonColor)),
                  onPressed: () => huntWiFis(),
                  child: const Text('Hunt Networks')
                ),
              ),
              wiFiHunterResult.results.isNotEmpty ? Container(
                margin: const EdgeInsets.only(bottom: 20.0, left: 30.0, right: 30.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisSize: MainAxisSize.min,
                  children: List.generate(wiFiHunterResult.results.length, (index) =>
                    Container(
                      margin: const EdgeInsets.symmetric(vertical: 10.0),
                      child: ListTile(
                        leading: Text(wiFiHunterResult.results[index].level.toString() + ' dbm'),
                        title: Text(wiFiHunterResult.results[index].SSID),
                        subtitle: Column(
                          mainAxisAlignment: MainAxisAlignment.start,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            Text('BSSID : ' + wiFiHunterResult.results[index].BSSID),
                            Text('Capabilities : ' + wiFiHunterResult.results[index].capabilities),
                            Text('Frequency : ' + wiFiHunterResult.results[index].frequency.toString()),
                            Text('Channel Width : ' + wiFiHunterResult.results[index].channelWidth.toString()),
                            Text('Timestamp : ' + wiFiHunterResult.results[index].timestamp.toString())
                          ]
                        )
                      ),
                    )
                  )
                ),
              ) : Container()
            ],
          ),
        ),
      ),
    );
  }
}
