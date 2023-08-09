class WiFiHunterResult {
  List<WiFiHunterResultEntry> results = <WiFiHunterResultEntry>[];
}

class WiFiHunterResultEntry {
  String ssid;
  String bssid;
  String capabilities;
  int frequency;
  int level;
  int channelWidth;
  int timestamp;

  WiFiHunterResultEntry(this.ssid, this.bssid, this.capabilities, this.frequency, this.level, this.channelWidth, this.timestamp);
}