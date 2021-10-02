class WiFiHunterResult {
  late List<WiFiHunterResultEntry> results = <WiFiHunterResultEntry>[];
}

class WiFiHunterResultEntry {
  late String SSID;
  late String BSSID;
  late String capabilities;
  late int frequency;
  late int level;
  late int channelWidth;
  late int timestamp;

  WiFiHunterResultEntry(this.SSID, this.BSSID, this.capabilities, this.frequency, this.level, this.channelWidth, this.timestamp);
}