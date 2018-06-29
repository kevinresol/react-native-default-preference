
#import "RNDefaultPreference.h"

@implementation RNDefaultPreference

NSString* defaultSuiteName = nil;

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

- (NSUserDefaults *) getDefaultUser{
    if(defaultSuiteName == nil){
        NSLog(@"No prefer suite for userDefaults. Using standard one.");
        return [NSUserDefaults standardUserDefaults];
    } else {
        NSLog(@"Using %@ suite for userDefaults", defaultSuiteName);
        return [[NSUserDefaults alloc] initWithSuiteName:defaultSuiteName];
    }
}

- (NSString *)getStringOrArray:(NSString *)key {
    NSString *value = [[self getDefaultUser] stringForKey:key];
    if (nil == value) {
        NSError* error = nil;
        NSArray *array = [[self getDefaultUser] arrayForKey:key];
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:array options:0 error:&error];
        return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    } else {
        return value;
    }
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(setName:(NSString *)name
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(__unused RCTPromiseRejectBlock)reject)
{
    defaultSuiteName = name;
    resolve([NSNull null]);
}

RCT_EXPORT_METHOD(getName:(RCTPromiseResolveBlock)resolve
                  reject:(__unused RCTPromiseRejectBlock)reject)
{
    resolve(defaultSuiteName);
}

RCT_EXPORT_METHOD(get:(NSString *)key
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(__unused RCTPromiseRejectBlock)reject)
{
    resolve([self getStringOrArray:key]);
}

RCT_EXPORT_METHOD(set:(NSString *)key value:(NSString *)value
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(__unused RCTPromiseRejectBlock)reject)
{
    [[self getDefaultUser] setObject:value forKey:key];
    resolve([NSNull null]);
}

RCT_EXPORT_METHOD(clear:(NSString *)key
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(__unused RCTPromiseRejectBlock)reject)
{
    [[self getDefaultUser] removeObjectForKey:key];
    resolve([NSNull null]);
}

RCT_EXPORT_METHOD(getMultiple:(NSArray *)keys
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(__unused RCTPromiseRejectBlock)reject)
{
    NSMutableArray *result = [NSMutableArray array];
    for(NSString *key in keys) {
        NSString *value = [self getStringOrArray:key];
        [result addObject: value == nil ? [NSNull null] : value];
    }
    resolve(result);
}

RCT_EXPORT_METHOD(setMultiple:(NSDictionary *)data
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(__unused RCTPromiseRejectBlock)reject)
{
    for (id key in data) {
        [[self getDefaultUser] setObject:[data objectForKey:key] forKey:key];
    }
    resolve([NSNull null]);
}

RCT_EXPORT_METHOD(clearMultiple:(NSArray *)keys
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(__unused RCTPromiseRejectBlock)reject)
{
    for(NSString *key in keys) {
        [[self getDefaultUser] removeObjectForKey:key];
    }
    resolve([NSNull null]);
}

RCT_EXPORT_METHOD(getAll:(RCTPromiseResolveBlock)resolve
                  reject:(__unused RCTPromiseRejectBlock)reject)
{
    NSArray *keys = [[[self getDefaultUser] dictionaryRepresentation] allKeys];
    NSMutableDictionary *result = [NSMutableDictionary dictionary];
    for(NSString *key in keys) {
        NSString *value = [self getStringOrArray:key];
        result[key] = value == nil ? [NSNull null] : value;
    }
    resolve(result);
}

RCT_EXPORT_METHOD(clearAll:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
    NSArray *keys = [[[self getDefaultUser] dictionaryRepresentation] allKeys];
    [self clearMultiple:keys resolve:resolve reject:reject];
}

@end
