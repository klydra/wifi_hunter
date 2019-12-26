#import "WiFiHunterPlugin.h"
#import <wifi_hunter/wifi_hunter-Swift.h>

@implementation WiFiHunterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftWiFiHunterPlugin registerWithRegistrar:registrar];
}
@end
