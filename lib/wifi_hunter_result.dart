class WiFiHunterResult {
  /// List of WiFi networks in scan result
  List<WiFiHunterResultEntry> results = <WiFiHunterResultEntry>[];
}

class WiFiHunterResultEntry {
  /// SSID (Wi-Fi Service Set Identifier) of the WiFi
  String ssid;
  /// BSSID (Basic Service Set Identifier) of the WiFi
  String bssid;
  /// Capabilities (supported standards) of the WiFi
  String capabilities;
  /// Frequency (2.4GHz / 5GHz / 6GHz / ...) of the WiFi
  /// Reference : https://developer.android.com/reference/android/net/wifi/ScanResult#constants_1
  int frequency;
  /// Level (signal strength in dbm) of the WiFi
  int level;
  /// Channel Width (20MHz / 40MHz / 80MHz / ...) of the WiFi
  /// Reference : https://developer.android.com/reference/android/net/wifi/ScanResult#constants_1
  int channelWidth;
  /// Timestamp of the WiFi scan (in microseconds since boot)
  /// Reference : https://developer.android.com/reference/android/net/wifi/ScanResult#timestamp
  int timestamp;

  WiFiHunterResultEntry(this.ssid, this.bssid, this.capabilities, this.frequency, this.level, this.channelWidth, this.timestamp);
}