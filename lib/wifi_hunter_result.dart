class WiFiHunterResult {
  List<WiFiHunterResultEntry> results = <WiFiHunterResultEntry>[];
}

class WiFiHunterResultEntry {
  String SSID;
  String BSSID;
  String capabilities;
  int frequency;
  int level;
  int channelWidth;
  int timestamp;

  WiFiHunterResultEntry(this.SSID, this.BSSID, this.capabilities, this.frequency, this.level, this.channelWidth, this.timestamp);
}