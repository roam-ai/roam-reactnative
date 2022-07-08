
#import "RNRoam.h"
#import <Roam/Roam.h>
#import <CoreLocation/CoreLocation.h>

@implementation RNRoam{
  BOOL hasListeners;
}

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

RCT_EXTERN_METHOD(supportedEvents)

RCT_EXPORT_MODULE();

- (instancetype)init {
  self = [super init];
  if (self) {
    [Roam setDelegate:self];
  }
  return self;
}

- (NSArray<NSString *> *)supportedEvents {
  return @[@"location", @"location_received", @"trip_status", @"error"];
}

// Roam Delegate Methods
- (void)didUpdateLocation:(NSArray<RoamLocation *> *)locations {
  if (hasListeners) {
        [self sendEventWithName:@"location" body:[self userLocation:locations]];
  }
}

- (void)didReceiveTripStatus:(NSArray<RoamTripStatusListener *> *)tripStatus{
  if (hasListeners) {
    [self sendEventWithName:@"trip_status" body:[self didTripStatus:tripStatus]];
  }
}

- (void)didReceiveUserLocation:(RoamLocationReceived *)location{
  if (hasListeners) {
    [self sendEventWithName:@"location_received" body:[self didUserLocation:location]];
  }
}

- (void)onError:(RoamError *)error{
  if (hasListeners) {
    [self sendEventWithName:@"error" body:[self error:error]];
  }
}


-(void)startObserving {
  hasListeners = YES;
}

- (void)stopObserving {
  hasListeners = NO;
}

// Create User
RCT_EXPORT_METHOD(createUser:(NSString *)userDescription :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam createUser:userDescription :nil handler:^(RoamUser * user, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self userData:user], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Get User
RCT_EXPORT_METHOD(getUser:(NSString *)userId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam getUser:userId handler:^(RoamUser * user, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self userData:user], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Set Description User
RCT_EXPORT_METHOD(setDescription:(NSString *)userDescription){
  [Roam updateUser:userDescription :nil];
}

// toggle Events
RCT_EXPORT_METHOD(toggleEvents:(BOOL)geofence trip:(BOOL)trip location:(BOOL)location movingGeofence:(BOOL)movingGeofence :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam toggleEventsWithGeofence:geofence Trip:trip Location:location MovingGeofence:movingGeofence handler:^(RoamUser * user, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self userData:user], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Toggle Listener
RCT_EXPORT_METHOD(toggleListener:(BOOL)location event:(BOOL)event :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam toggleListenerWithEvents:event Locations:event handler:^(RoamUser * user, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self userData:user], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

//getEvents Status
RCT_EXPORT_METHOD(getEventsStatus:(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam getEventsStatusWithHandler:^(RoamUser * user, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self userData:user], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Get listener status
RCT_EXPORT_METHOD(getListenerStatus:(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam getListenerStatusWithHandler:^(RoamUser * user, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self userData:user], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Logout
RCT_EXPORT_METHOD(logout:(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam logoutUserWithHandler:^(NSString * status, RoamError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self userLogout:status], nil];
      successCallback(success);
      
    }else{
      errorCallback([self error:error]);
    }
  }];
  
}

RCT_EXPORT_METHOD(setTrackingConfig:(NSInteger )accuracy
                  timeout:(NSInteger)timeout
                  discard:(BOOL)discardLocation
                  success: (RCTResponseSenderBlock)successCallback
                  error:(RCTResponseErrorBlock)errorCallback){
  [Roam setTrackingConfigWithAccuracy:accuracy timeout:timeout discardLocation:discardLocation handler:^(RoamLocationConfig * config, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self TrackingConfigResonse:config], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

RCT_EXPORT_METHOD(getTrackingConfig:(RCTResponseSenderBlock)successCallback
                               error:(RCTResponseErrorBlock)errorCallback){
  [Roam getTrackingConfigWithHandler:^(RoamLocationConfig * config, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self TrackingConfigResonse:config], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

RCT_EXPORT_METHOD(resetTrackingConfig:(RCTResponseSenderBlock)successCallback
error:(RCTResponseErrorBlock)errorCallback){
  [Roam resetTrackingConfigWithHandler:^(RoamLocationConfig * config, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self TrackingConfigResonse:config], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// subscribeTripStatus
RCT_EXPORT_METHOD(subscribeTripStatus:(NSString *)tripId){
  [Roam subscribeTripStatus:tripId];
}

// unSubscribeTripStatus
RCT_EXPORT_METHOD(unSubscribeTripStatus:(nullable NSString *)tripId){
  if ([tripId length] == 0) {
    [Roam unsubscribeTripStatus:nil];
  }else{
    [Roam unsubscribeTripStatus:tripId];
  }
}

// Start trip
RCT_EXPORT_METHOD(startTrip:(NSString *)tripId description:(NSString *)tripDescription :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  dispatch_async(dispatch_get_main_queue(), ^{
    
    [Roam startTrip:tripId :tripDescription handler:^(RoamStartTrip * trip, RoamError * error) {
      if (error == nil){
        NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripStatus:trip], nil];
        successCallback(success);
      }else{
        errorCallback([self error:error]);
      }
    }];
  });
}

// Resume trip
RCT_EXPORT_METHOD(resumeTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam resumeTrip:tripId handler:^(NSString * status, RoamError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self roamTripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Pause trip
RCT_EXPORT_METHOD(pauseTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam pauseTrip:tripId handler:^(NSString * status, RoamError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self userLogout:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Stop trip
RCT_EXPORT_METHOD(stopTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam stopTrip:tripId handler:^(NSString * status, RoamError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self roamTripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// ForceStop trip
RCT_EXPORT_METHOD(forceStopTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam forceEndTrip:tripId handler:^(NSString * status, RoamError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self roamTripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Delete trip
RCT_EXPORT_METHOD(deleteTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam deleteTrip:tripId handler:^(NSString * status, RoamError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self roamTripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Sync trip
RCT_EXPORT_METHOD(syncTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam syncTrip:tripId handler:^(NSString * status, RoamError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self roamTripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Create trip
// ["origin":[[longitude1,latitude1],[longitude2,latitude2]],"destinations":[[longitude1,latitude1]]]

RCT_EXPORT_METHOD(createTrip:(BOOL)offline :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  
  [Roam createTrip:offline :nil :nil handler:^(RoamCreateTrip * trip, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self createTripResponse:trip], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

RCT_EXPORT_METHOD(activeTrips:(BOOL)offline:(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam activeTrips:offline handler:^(NSArray<RoamTrip *> * trips, RoamError * error) {
    if (error == nil){
      successCallback([self activeTripResponse:trips]);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

RCT_EXPORT_METHOD(getTripSummary:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam getTripSummary:tripId handler:^(RoamTripSummary * summary, RoamError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripSummary:summary], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}
// Request Location
RCT_EXPORT_METHOD(requestLocationPermission){
  dispatch_async(dispatch_get_main_queue(), ^{
    [Roam requestLocation];
  });
}

RCT_EXPORT_METHOD(checkLocationPermission:(RCTResponseSenderBlock)callback){
  NSMutableArray *array = [[NSMutableArray alloc] initWithObjects:[self checkPermission:[Roam isLocationEnabled]], nil];
  callback(array);
}

RCT_EXPORT_METHOD(isLocationTracking:(RCTResponseSenderBlock)callback){
  NSMutableArray *array = [[NSMutableArray alloc] initWithObjects:[self checkPermission:[Roam isLocationTracking]], nil];
  callback(array);
}

RCT_EXPORT_METHOD(locationPermissionStatus:(RCTResponseSenderBlock)callback){
  NSMutableArray *array = [[NSMutableArray alloc] initWithObjects:[self locationPermissionValue:[Roam locationPermissionStatus]], nil];
  callback(array);
}

RCT_EXPORT_METHOD(getCurrentLocationIos:(NSInteger)accuracy :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  
  NSLog(@" getting current location Started %@",[[NSDate date] descriptionWithLocale:[NSLocale currentLocale]]);
  dispatch_async(dispatch_get_main_queue(), ^{
    [Roam getCurrentLocation:accuracy handler:^(CLLocation * location, RoamError * error) {
      if (error == nil) {
        NSLog(@" getting current location After %@",[[NSDate date] descriptionWithLocale:[NSLocale currentLocale]]);
        NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self locationReponse:location], nil];
        successCallback(success);
      }else{
        errorCallback([self error:error]);
        
      }
    }];
  });
}

RCT_EXPORT_METHOD(updateCurrentLocationIos:(NSInteger)accuracy){
  dispatch_async(dispatch_get_main_queue(), ^{
    [Roam updateCurrentLocation:accuracy :nil];
  });
}

RCT_EXPORT_METHOD(updateLocationWhenStationary:(NSInteger)interval){
  dispatch_async(dispatch_get_main_queue(), ^{
    [Roam updateLocationWhenStationary:interval];
  });
}

//Tracking
// passive,BALANCED,active
RCT_EXPORT_METHOD(startTracking:(NSString *)trackingMode){
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([trackingMode  isEqual:@"PASSIVE"]) {
      [Roam startTracking:RoamTrackingModePassive options:nil];
    } else if ([trackingMode  isEqual:@"BALANCED"]){
      [Roam startTracking:RoamTrackingModeBalanced options:nil];
    } else{
      [Roam startTracking:RoamTrackingModeActive options:nil];
    }
  });
}

// Custom Tracking
RCT_EXPORT_METHOD(startTrackingCustom:(BOOL)allowBackground pauseAutomatic:(BOOL)pauseAutomatic activityType:(NSString *)activityType desiredAccuracy:(NSString *)desiredAccuracy showBackIndicator:(BOOL)showBackIndicator distanceFilter:(NSInteger)distanceFilter accuracyFilter:(NSInteger)accuracyFilter updateInterval:(NSInteger)updateInterval){
  dispatch_async(dispatch_get_main_queue(), ^{
    RoamTrackingCustomMethodsObjcWrapper *wrapper = [[RoamTrackingCustomMethodsObjcWrapper alloc] init];
    [wrapper setUpCustomOptionsWithDesiredAccuracy:[self getDesireAccuracy:desiredAccuracy] useVisit:true showsBackgroundLocationIndicator:showBackIndicator distanceFilter:distanceFilter useSignificant:true useRegionMonitoring:true useDynamicGeofencRadius:true geofenceRadius:true allowBackgroundLocationUpdates:allowBackground activityType:[self getActivityType:activityType] pausesLocationUpdatesAutomatically:pauseAutomatic useStandardLocationServices:false accuracyFilter:accuracyFilter updateInterval:updateInterval];
    [Roam startTracking:RoamTrackingModeCustom options:wrapper.customMethods];
  });
}

// Self tracking
RCT_EXPORT_METHOD(startSelfTracking:(NSString *)trackingMode){
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([trackingMode  isEqual:@"PASSIVE"]) {
      [Roam startTracking:RoamTrackingModePassive options:nil];
    } else if ([trackingMode  isEqual:@"BALANCED"]){
      [Roam startTracking:RoamTrackingModeBalanced options:nil];
    } else{
      [Roam startTracking:RoamTrackingModeActive options:nil];
    }
  });
}


RCT_EXPORT_METHOD(stopTracking){
  dispatch_async(dispatch_get_main_queue(), ^{
    [Roam stopTracking];
  });
}

RCT_EXPORT_METHOD(enableAccuracyEngine:(NSInteger)accuracy){
  [Roam enableAccuracyEngine:accuracy];
}

RCT_EXPORT_METHOD(disableAccuracyEngine){
  [Roam disableAccuracyEngine];
}


RCT_EXPORT_METHOD(setTrackingInAppState:(NSString *)appState){
  if ([appState  isEqual:@"ALWAYS_ON"]) {
    [Roam setTrackingInAppState:RoamTrackingStateAlwaysOn];
  } else if ([appState  isEqual:@"FOREGROUND"]){
    [Roam setTrackingInAppState:RoamTrackingStateForeground];
  } else {
    [Roam setTrackingInAppState:RoamTrackingStateBackground];
  }
}

RCT_EXPORT_METHOD(offlineLocationTracking:(BOOL)offline){
  [Roam offlineLocationTracking:offline];
}

// Subscribe events & location

RCT_EXPORT_METHOD(subscribe:(NSString *)type userId:(NSString *)userId){
  if ([type  isEqual:@"LOCATION"]){
    [Roam subscribe:RoamSubscribeLocation :userId];
  }else if ([type isEqual:@"EVENTS"]){
    [Roam subscribe:RoamSubscribeEvents :userId];
  }else{
    [Roam subscribe:RoamSubscribeEvents :userId];
  }
}

RCT_EXPORT_METHOD(unSubscribe:(NSString *)type userId:(NSString *)userId){
  if ([type  isEqual:@"LOCATION"]){
    [Roam unsubscribe:RoamSubscribeLocation :userId];
  }else if ([type isEqual:@"EVENTS"]){
    [Roam unsubscribe:RoamSubscribeEvents :userId];
  }else{
    [Roam unsubscribe:RoamSubscribeEvents :userId];
  }
}


// Publish only & Publish Save
RCT_EXPORT_METHOD(publishAndSave:(NSDictionary *)dict){
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([[dict allKeys] count] != 0) {
      RoamPublish *publish = [[RoamPublish alloc] init];
      publish.meta_data = dict;
      [Roam publishSave:publish];
    }else{
      [Roam publishSave:nil];
    }
  });
}

RCT_EXPORT_METHOD(publishOnly:(NSArray *)array metaData:(NSDictionary *)metaData){
  
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([array count] != 0) {
      RoamPublish *publish = [self publish:array metaData:metaData];
      [Roam publishOnly:publish];
    }else{
      [Roam publishOnly:nil];
    }
  });
}

RCT_EXPORT_METHOD(stopPublishing){
  [Roam stopPublishing];
}


// Batch Config

RCT_EXPORT_METHOD(setBatchReceiverConfig:(NSString *)state
                  batchCount:(NSInteger)batchCount
                  batchwindow:(NSInteger)batchWindow
                  success: (RCTResponseSenderBlock)successCallback
                  error:(RCTResponseErrorBlock)errorCallback){
  [Roam setBatchReceiverConfigWithNetworkState: [self networkState:state] batchCount:batchCount batchWindow:batchWindow handler:^(RoamBatchConfig * config, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self BatchConfigResonse:config], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
      
    }
  }];
}

RCT_EXPORT_METHOD(getBatchReceiverConfig
                  : (RCTResponseSenderBlock)successCallback
                  error:(RCTResponseErrorBlock)errorCallback){
  [Roam getBatchReceiverConfigWithHandler:^(RoamBatchConfig * config, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self BatchConfigResonse:config], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

RCT_EXPORT_METHOD(resetBatchReceiverConfig : (RCTResponseSenderBlock)successCallback
                  error:(RCTResponseErrorBlock)errorCallback){
  [Roam resetBatchReceiverConfigWithHandler:^(RoamBatchConfig * config, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self BatchConfigResonse:config], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
   }];
  }


+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

- (NSMutableDictionary *) userData:(RoamUser *)user{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:user.userId forKey:@"userId"];
  [dict setValue:user.userId forKey:@"description"];
  [dict setValue:user.userId forKey:@"geofenceEvents"];
  [dict setValue:user.userId forKey:@"locationEvents"];
  [dict setValue:user.userId forKey:@"tripsEvents"];
  [dict setValue:user.userId forKey:@"movingGeofenceEvents"];
  [dict setValue:user.userId forKey:@"eventListenerStatus"];
  [dict setValue:user.userId forKey:@"locationListenerStatus"];
  return dict;
}

- (NSMutableDictionary *) userLogout:(NSString *)status{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:status forKey:@"message"];
  return dict;
}

- (NSMutableDictionary *) roamTripStatus:(NSString *)status{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:status forKey:@"message"];
  return dict;
}

- (NSMutableDictionary *)tripStatus:(RoamStartTrip *)trip{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:trip.status forKey:@"message"];
  return dict;
}

- (NSError *)error:(RoamError *)error{
  return [NSError errorWithDomain:error.message code:[self removeString:error.code] userInfo:nil];
}

// Removing GS from error code

- (NSInteger)removeString:(NSString *)stringValue{
  NSMutableString *strippedString = [NSMutableString stringWithCapacity:stringValue.length];
  NSScanner *scanner = [NSScanner scannerWithString:stringValue];
  NSCharacterSet *numbers = [NSCharacterSet characterSetWithCharactersInString:@"0123456789"];
  while ([scanner isAtEnd] == NO) {
    NSString *buffer;
    if ([scanner scanCharactersFromSet:numbers intoString:&buffer]) {
      [strippedString appendString:buffer];
    } else {
      [scanner setScanLocation:([scanner scanLocation] + 1)];
    }
  }
  return @([strippedString integerValue]);
}

- (NSMutableDictionary *)TrackingConfigResonse:(RoamLocationConfig *)config{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:[NSNumber numberWithInt:config.accuracy] forKey:@"accuracy"];
  [dict setValue:[NSNumber numberWithInt:config.timeout] forKey:@"timeout"];
  [dict setValue:[NSNumber numberWithBool:config.discardLocation] forKey:@"discardLocation"];
  return  dict;
}

- (NSString *)checkPermission:(BOOL)isEnabled{
  if (isEnabled){
    return @"GRANTED";
  } else {
    return @"DENIED";
  }
}

- (NSString *)locationPermissionValue:(NSInteger) value{
  if (value == 0){
    return @"notDetermined";
  }else if (value == 1){
    return @"restricted";
  }else if (value == 2){
    return @"denied";
  }else if (value == 3){
    return @"always";
  }else{
    return @"whenInUse";
  }
}

- (NSMutableDictionary *) createTripResponse:(RoamCreateTrip *)trip{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:trip.tripId forKey:@"id"];
  [dict setValue:trip.userId forKey:@"userId"];
  [dict setValue:trip.createdAt forKey:@"createdAt"];
  [dict setValue:trip.updatedAt forKey:@"updatedAt"];
  [dict setValue:[NSNumber numberWithBool:trip.isStarted] forKey:@"isStarted"];
  [dict setValue:[NSNumber numberWithBool:trip.isEnded] forKey:@"isEnded"];
  [dict setValue:[NSNumber numberWithBool:trip.isDeleted] forKey:@"isDeleted"];
  [dict setValue:trip.tripTrackingUrl forKey:@"tripTrackingUrl"];
  
  if (trip.origins.count != 0){
    NSMutableArray *originArray = [[NSMutableArray alloc] init];
    
    for (int i = 0; i < trip.origins.count; i++)
    {
      NSMutableDictionary *originDict = [[NSMutableDictionary alloc] init];
      RoamTripOrigin *origin = [trip.origins objectAtIndex:i];
      [originDict setValue:origin.id forKey:@"id"];
      [originDict setValue:origin.tripId forKey:@"tripId"];
      [originDict setValue:origin.createdAt forKey:@"createdAt"];
      [originDict setValue:origin.updatedAt forKey:@"updatedAt"];
      if (origin.coordinates.count != 0){
        [originDict setValue:origin.coordinates.firstObject forKey:@"latitude"];
        [originDict setValue:origin.coordinates.lastObject forKey:@"longitude"];
      }
      [originDict setValue:origin.locType forKey:@"type"];
      [originDict setValue:[NSNumber numberWithBool:origin.reached] forKey:@"reached"];
      [originArray addObject:originDict];
    }
    if (originArray.count != 0) {
      [dict setObject:originArray forKey:@"origin"];
    }
    
  }
  if (trip.destinations.count != 0){
    NSMutableArray *destinationArray = [[NSMutableArray alloc] init];
    
    for (int i = 0; i < trip.destinations.count; i++)
    {
      NSMutableDictionary *originDict = [[NSMutableDictionary alloc] init];
      RoamTripDestination *destination = [trip.destinations objectAtIndex:i];
      [originDict setValue:destination.id forKey:@"id"];
      [originDict setValue:destination.tripId forKey:@"tripId"];
      [originDict setValue:destination.createdAt forKey:@"createdAt"];
      [originDict setValue:destination.updatedAt forKey:@"updatedAt"];
      if (destination.coordinates.count != 0){
        [originDict setValue:destination.coordinates.firstObject forKey:@"latitude"];
        [originDict setValue:destination.coordinates.lastObject forKey:@"longitude"];
      }
      [originDict setValue:destination.locType forKey:@"type"];
      [originDict setValue:[NSNumber numberWithBool:destination.reached] forKey:@"reached"];
      [destinationArray addObject:originDict];
    }
    
    if (destinationArray.count != 0) {
      [dict setObject:destinationArray forKey:@"destination"];
    }
  }
  
  return dict;
}

- (NSMutableDictionary *)locationReponse:(CLLocation *)location{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:[NSNumber numberWithDouble:location.coordinate.latitude] forKey:@"latitude"];
  [dict setValue:[NSNumber numberWithDouble:location.coordinate.longitude] forKey:@"longitude"];
  [dict setValue:[NSNumber numberWithDouble:location.horizontalAccuracy] forKey:@"accuracy"];
  [dict setValue:[NSNumber numberWithDouble:location.altitude] forKey:@"altitude"];
  [dict setValue:[NSNumber numberWithDouble:location.speed] forKey:@"speed"];
  return  dict;
}

- (NSArray *)activeTripResponse:(NSArray<RoamTrip *>*)trips{
  
  NSMutableArray *tripsArray = [[NSMutableArray alloc] init];
  for (int i = 0; i < trips.count; i++){
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    RoamTrip *trip = [trips objectAtIndex:i];
    [dict setValue:trip.tripId forKey:@"tripId"];
    [dict setValue:trip.createdAt forKey:@"createdAt"];
    [dict setValue:trip.updatedAt forKey:@"updatedAt"];
    [dict setValue:trip.syncStatus forKey:@"syncStatus"];
    dict[@"isStarted"] = @(trip.started);
    dict[@"isPaused"] = @(trip.paused);
    dict[@"isEnded"] = @(trip.ended);
    dict[@"isDeleted"] = @(trip.deleted);
    [tripsArray addObject:dict];
  }
  
  NSDictionary *tripsData = [[NSDictionary alloc] initWithObjectsAndKeys:tripsArray,@"activeTrips", nil];
  NSArray *outArray = [[NSArray alloc] initWithObjects:tripsData, nil];
  return  outArray;
}

-(LocationAccuracy)getDesireAccuracy:(NSString *)accuracy{
  if ([accuracy  isEqual: @"BESTFORNAVIGATION"]) {
    return LocationAccuracyKCLLocationAccuracyBestForNavigation;
  }else if ([accuracy  isEqual: @"BEST"]){
    return LocationAccuracyKCLLocationAccuracyBest;
  }else if ([accuracy  isEqual: @"NEAREST_TEN_METERS"]){
    return LocationAccuracyKCLLocationAccuracyHundredMeters;
  }else if ([accuracy  isEqual: @"HUNDRED_METERS"]){
    return  LocationAccuracyKCLLocationAccuracyHundredMeters;
  }else if ([accuracy  isEqual: @"KILO_METERS"]){
    return  LocationAccuracyKCLLocationAccuracyKilometer;
  }else if ([accuracy  isEqual: @"THREE_KILOMETERS"]){
    return  LocationAccuracyKCLLocationAccuracyThreeKilometers;
  }else{
    return NULL;
  }
}

-(CLActivityType)getActivityType:(NSString *)type{
  if ([type  isEqual: @"OTHER"]) {
    return CLActivityTypeOther;
  }else if ([type  isEqual: @"AUTO_NAVIGATION"]){
    return CLActivityTypeAutomotiveNavigation;
  }else if ([type  isEqual: @"OTHER_NAVIGATION"]){
    return CLActivityTypeOtherNavigation;
  }else if ([type isEqual:@"FITNESS"]){
    return CLActivityTypeFitness;
  }else {
    return NULL;
  }
}

- (NSMutableArray *) userLocation:(NSArray<RoamLocation *> *)locations{
  
  NSMutableArray *array = [[NSMutableArray alloc] init];
  for (RoamLocation* location in locations) {
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    [dict setValue:location.userId forKey:@"userId"];
    [dict setValue:location.activity forKey:@"activity"];
    [dict setValue:location.recordedAt forKey:@"recordedAt"];
    [dict setValue:location.timezoneOffset forKey:@"timezone"];
    [dict setObject:[self locationReponse:location.location] forKey:@"location"];
    [array addObject:dict];
  }
  
  return array;
}

- (NSMutableDictionary *) didUserLocation:(RoamLocationReceived *)location{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:location.userId forKey:@"userId"];
  [dict setValue:location.locationId forKey:@"locationId"];
  [dict setValue:location.locationId forKey:@"locationId"];
  [dict setValue:location.activity forKey:@"activity"];
  [dict setValue:location.eventSource forKey:@"eventSource"];
  [dict setValue:location.eventVersion forKey:@"eventVersion"];
  [dict setValue:location.recordedAt forKey:@"recordedAt"];
  [dict setValue:location.eventType forKey:@"eventType"];
  [dict setValue:location.latitude forKey:@"latitude"];
  [dict setValue:location.longitude forKey:@"longitude"];
  [dict setValue:location.horizontalAccuracy forKey:@"horizontalAccuracy"];
  [dict setValue:location.verticalAccuracy forKey:@"verticalAccuracy"];
  [dict setValue:location.speed forKey:@"speed"];
  [dict setValue:location.course forKey:@"course"];
  [dict setValue:location.altitude forKey:@"altitude"];
  
  return dict;
}



- (NSMutableArray *) didTripStatus:(NSArray<RoamTripStatusListener *> *)trips{
    NSMutableArray *array = [[NSMutableArray alloc] init];
       for (RoamTripStatusListener* trip in trips) {
           NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
           [dict setValue:[NSNumber numberWithDouble:trip.latitude] forKey:@"latitude"];
           [dict setValue:[NSNumber numberWithDouble:trip.longitude] forKey:@"longitude"];
           [dict setValue:[NSNumber numberWithDouble:trip.distance] forKey:@"distance"];
           [dict setValue:[NSNumber numberWithDouble:trip.duration] forKey:@"duration"];
           [dict setValue:[NSNumber numberWithDouble:trip.speed] forKey:@"speed"];
           [dict setValue:[NSNumber numberWithDouble:trip.pace] forKey:@"pace"];
           [dict setValue:trip.startedTime forKey:@"startedTime"];
           [dict setValue:trip.recordedAt forKey:@"recordedAt"];
           [array addObject:dict];
       }
    return array;
}


- (RoamPublish *)publish:(NSArray *)array metaData:(NSDictionary *)metaData{
  RoamPublish *publish = [[RoamPublish alloc] init];
  for (NSString *string in array)
  {
    if ([string isEqual:@"USER_ID"]) {
      publish.user_id = true;
    }
    
    if ([string isEqual:@"APP_ID"]) {
      publish.user_id = true;
    }
    if ([string isEqual:@"GEOFENCE_EVENTS"]) {
      publish.geofence_events = true;
    }
    if ([string isEqual:@"LOCATION_EVENTS"]) {
      publish.location_events = true;
    }
    if ([string isEqual:@"NEARBY_EVENTS"]) {
      publish.nearby_events = true;
    }
    if ([string isEqual:@"TRIPS_EVENTS"]) {
      publish.trips_events = true;
    }
    if ([string isEqual:@"LOCATION_LISTENER"]) {
      publish.location_listener = true;
    }
    if ([string isEqual:@"EVENT_LISTENER"]) {
      publish.event_listener = true;
    }
    if ([string isEqual:@"ALTITUDE"]) {
      publish.altitude = true;
    }
    if ([string isEqual:@"COURSE"]) {
      publish.course = true;
    }
    if ([string isEqual:@"SPEED"]) {
      publish.speed = true;
    }
    if ([string isEqual:@"VERTICAL_ACCURACY"]) {
      publish.vertical_accuracy = true;
    }
    if ([string isEqual:@"HORIZONTAL_ACCURACY"]) {
      publish.horizontal_accuracy = true;
    }
    if ([string isEqual:@"BATTERY_REMAINING"]) {
      publish.battery_remaining = true;
    }
    if ([string isEqual:@"BATTERY_SAVER"]) {
      publish.battery_saver = true;
    }
    if ([string isEqual:@"BATTERY_STATUS"]) {
      publish.battery_status = true;
    }
    if ([string isEqual:@"ACTIVITY"]) {
      publish.activity = true;
    }
    if ([string isEqual:@"AIRPLANE_MODE"]) {
      publish.airplane_mode = true;
    }
    if ([string isEqual:@"DEVICE_MANUFACTURE"]) {
      publish.device_manufacturer = true;
    }
    if ([string isEqual:@"DEVICE_MODEL"]) {
      publish.device_model = true;
    }
    if ([string isEqual:@"TRACKING_MODE"]) {
      publish.tracking_mode = true;
    }
    if ([string isEqual:@"LOCATIONPERMISSION"]) {
      publish.location_permission = true;
    }
    if ([string isEqual:@"NETWORK_STATUS"]) {
      publish.network_status = true;
    }
    if ([string isEqual:@"OS_VERSION"]) {
      publish.os_version = true;
    }
    if ([string isEqual:@"RECORDERD_AT"]) {
      publish.recorded_at = true;
    }
    if ([string isEqual:@"TZ_OFFSET"]) {
      publish.tz_offset = true;
    }
  }
  return publish;
}

- (NSMutableDictionary *) tripSummary:(RoamTripSummary *)summary {
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:summary.distanceCovered forKey:@"distanceCovered"];
  [dict setValue:summary.tripId forKey:@"tripId"];
  [dict setValue:summary.duration forKey:@"duration"];
  [dict setValue:summary.totalElevationGain forKey:@"elevationGain"];
  [dict setValue:summary.userId forKey:@"userid"];
  [dict setValue:[self tripSummaryRoute:summary.route] forKey:@"route"];
  //  [dict setValue:[NSNumber numberWithDouble:summary.distanceCovered] forKey:@"distanceCovered"];
  
  
  
  return dict;
}

- (NSMutableArray *)tripSummaryRoute:(NSMutableArray *)routes {
  NSMutableArray *array = [[NSMutableArray alloc] init];
  
  for (RoamTripSummaryRoute* route in routes) {
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    
    [dict setValue:route.recordedAt forKey:@"recordedAt"];
    [dict setValue:route.activity forKey:@"activity"];
    [dict setValue:route.duration forKey:@"duration"];
    [dict setValue:route.altitude forKey:@"altitude"];
    [dict setValue:route.elevationGain forKey:@"elevationGain"];
    [dict setValue:route.distance forKey:@"distance"];
    [dict setValue:[route.coordinates firstObject] forKey:@"longitude"];
    [dict setValue:[route.coordinates lastObject] forKey:@"latitude"];
    
    [array addObject:dict];
  }
  
  return array;
}

- (RoamNetworkState)networkState:(NSString *)stringValue{
  if ([stringValue isEqualToString:@"BOTH"]) {
    return  RoamNetworkStateBoth;
  }else if ([stringValue isEqualToString:@"ONLINE"]) {
    return  RoamNetworkStateOnline;
  }else{
    return  RoamNetworkStateOffline;
  }
}

- (NSMutableDictionary *)BatchConfigResonse:(RoamBatchConfig *)config{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:[NSNumber numberWithInt:config.batchCount] forKey:@"batchCount"];
  [dict setValue:[NSNumber numberWithInt:config.batchWindow] forKey:@"batchWindow"];
  [dict setValue:config.networkState forKey:@"networkState"];
  return  dict;
}

@end


