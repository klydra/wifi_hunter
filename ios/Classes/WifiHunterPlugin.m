#import "WiFiHunterPlugin.h"
#if __has_include(<wifi_hunter/wifi_hunter-Swift.h>)
#import <wifi_hunter/wifi_hunter-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "wifi_hunter-Swift.h"
#endif

@implementation WiFiHunterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftWiFiHunterPlugin registerWithRegistrar:registrar];
}
@end
