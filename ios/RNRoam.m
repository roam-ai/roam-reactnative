//
//  RNGeoSpark.m
//  RNGeoSpark
//
//  Copyright © 2018 GeoSpark, Inc. All rights reserved.
//

#import "RNGeoSpark.h"
#import <GeoSpark/GeoSpark.h>
#import <React/RCTConvert.h>
#import <React/RCTUtils.h>


@implementation RNGeoSpark{
  BOOL hasListeners;
}
RCT_EXTERN_METHOD(supportedEvents)

RCT_EXPORT_MODULE();

- (instancetype)init {
  self = [super init];
  if (self) {
    [GeoSpark setDelegate:self];
  }
  return self;
}

- (NSArray<NSString *> *)supportedEvents {
  return @[@"location", @"location_received", @"trip_status", @"error"];
}

- (void)didUpdateLocation:(GeoSparkLocation *)location {
  if (hasListeners) {
    [self sendEventWithName:@"location" body:[self userLocation:location]];
  }
}

- (void)didReceiveTripStatus:(TripStatusListener *)tripStatus{
  if (hasListeners) {
    [self sendEventWithName:@"trip_status" body:[self didTripStatus:tripStatus]];
  }
}

- (void)didReceiveUserLocation:(GeoSparkLocationReceived *)location{
  if (hasListeners) {
    [self sendEventWithName:@"location_received" body:[self didUserLocation:location]];
  }
}

- (void)onError:(GeoSparkError *)error{
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
  [GeoSpark createUser:userDescription handler:^(GeoSparkUser * user, GeoSparkError * error) {
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
  [GeoSpark getUser:userId handler:^(GeoSparkUser * user, GeoSparkError * error) {
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
  [GeoSpark setDescription:userDescription];
}

// toggle Events
RCT_EXPORT_METHOD(toggleEvents:(BOOL)geofence trip:(BOOL)trip location:(BOOL)location movingGeofence:(BOOL)movingGeofence :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [GeoSpark toggleEventsWithGeofence:geofence Trip:trip Location:location MovingGeofence:movingGeofence handler:^(GeoSparkUser * user, GeoSparkError * error) {
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
  [GeoSpark toggleListenerWithEvents:event Locations:event handler:^(GeoSparkUser * user, GeoSparkError * error) {
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
  [GeoSpark getEventsStatusWithHandler:^(GeoSparkUser * user, GeoSparkError * error) {
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
  [GeoSpark getListenerStatusWithHandler:^(GeoSparkUser * user, GeoSparkError * error) {
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
  [GeoSpark logoutUserWithHandler:^(NSString * status, GeoSparkError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self userLogout:status], nil];
      successCallback(success);
      
    }else{
      errorCallback([self error:error]);
    }
  }];
  
}

// subscribeTripStatus
RCT_EXPORT_METHOD(subscribeTripStatus:(NSString *)tripId){
  [GeoSpark subscribeTripStatus:tripId];
}

// unSubscribeTripStatus
RCT_EXPORT_METHOD(unSubscribeTripStatus:(NSString *)tripId){
  [GeoSpark unsubscribeTripStatus:tripId];
}

// Start trip
RCT_EXPORT_METHOD(startTrip:(NSString *)tripId description:(NSString *)tripDescription :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  dispatch_async(dispatch_get_main_queue(), ^{
    [GeoSpark startTrip:tripId :tripDescription handler:^(NSString * status, GeoSparkError * error) {
      if (error == nil){
        NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripStatus:status], nil];
        successCallback(success);
      }else{
        errorCallback([self error:error]);
      }
    }];
  });
}

// Resume trip
RCT_EXPORT_METHOD(resumeTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [GeoSpark resumeTrip:tripId handler:^(NSString * status, GeoSparkError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Pause trip
RCT_EXPORT_METHOD(pauseTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [GeoSpark pauseTrip:tripId handler:^(NSString * status, GeoSparkError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Stop trip
RCT_EXPORT_METHOD(stopTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [GeoSpark stopTrip:tripId handler:^(NSString * status, GeoSparkError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// ForceStop trip
RCT_EXPORT_METHOD(forceStopTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [GeoSpark forceEndTrip:tripId handler:^(NSString * status, GeoSparkError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Delete trip
RCT_EXPORT_METHOD(deleteTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [GeoSpark deleteTrip:tripId handler:^(NSString * status, GeoSparkError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Sync trip
RCT_EXPORT_METHOD(syncTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [GeoSpark syncTrip:tripId handler:^(NSString * status, GeoSparkError * error) {
    if (error == nil){
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripStatus:status], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Create trip
// ["origin":[[longitude1,latitude1],[longitude2,latitude2]],"destinations":[[longitude1,latitude1]]]

RCT_EXPORT_METHOD(createTrip:(BOOL)offline :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [GeoSpark createTrip:offline :nil handler:^(GeoSparkCreateTrip * trip, GeoSparkError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self createTripResponse:trip], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

RCT_EXPORT_METHOD(activeTrips:(BOOL)offline:(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [GeoSpark activeTrips:offline handler:^(NSArray<GeoSparkTrip *> * trips, GeoSparkError * error) {
    if (error == nil){
      successCallback([self activeTripResponse:trips]);
    }else{
      errorCallback([self error:error]);
    }
  }];
}

// Request Location
RCT_EXPORT_METHOD(requestLocationPermission){
  dispatch_async(dispatch_get_main_queue(), ^{
    [GeoSpark requestLocation];
  });
}

RCT_EXPORT_METHOD(checkLocationPermission:(RCTResponseSenderBlock)callback){
  NSMutableArray *array = [[NSMutableArray alloc] initWithObjects:[self checkPermission:[GeoSpark isLocationEnabled]], nil];
  callback(array);
}

RCT_EXPORT_METHOD(isLocationTracking:(RCTResponseSenderBlock)callback){
  NSMutableArray *array = [[NSMutableArray alloc] initWithObjects:[self checkPermission:[GeoSpark isLocationTracking]], nil];
  callback(array);
}

RCT_EXPORT_METHOD(locationPermissionStatus:(RCTResponseSenderBlock)callback){
  NSMutableArray *array = [[NSMutableArray alloc] initWithObjects:[self locationPermissionValue:[GeoSpark locationPermissionStatus]], nil];
  callback(array);
}

RCT_EXPORT_METHOD(getCurrentLocationIos:(NSInteger)accuracy :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  dispatch_async(dispatch_get_main_queue(), ^{
    [GeoSpark getCurrentLocation:accuracy handler:^(CLLocation * location, GeoSparkError * error) {
      if (error == nil) {
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
    [GeoSpark updateCurrentLocation:accuracy];
  });
}

//Tracking
// passive,BALANCED,active
RCT_EXPORT_METHOD(startTracking:(NSString *)trackingMode){
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([trackingMode  isEqual:@"PASSIVE"]) {
      [GeoSpark startTracking:GeoSparkTrackingModePassive options:nil];
    } else if ([trackingMode  isEqual:@"BALANCED"]){
      [GeoSpark startTracking:GeoSparkTrackingModeBalanced options:nil];
    } else{
      [GeoSpark startTracking:GeoSparkTrackingModeActive options:nil];
    }
  });
}

// Custom Tracking
RCT_EXPORT_METHOD(startTrackingCustom:(BOOL)allowBackground pauseAutomatic:(BOOL)pauseAutomatic activityType:(NSString *)activityType desiredAccuracy:(NSString *)desiredAccuracy showBackIndicator:(BOOL)showBackIndicator distanceFilter:(NSInteger)distanceFilter accuracyFilter:(NSInteger)accuracyFilter){
  dispatch_async(dispatch_get_main_queue(), ^{
    GeoSparkTrackingCustomMethodsObjcWrapper *wrapper = [[GeoSparkTrackingCustomMethodsObjcWrapper alloc] init];
    [wrapper setUpCustomOptionsWithDesiredAccuracy:[self getDesireAccuracy:desiredAccuracy] useVisit:nil showsBackgroundLocationIndicator:showBackIndicator distanceFilter:distanceFilter useSignificant:nil useRegionMonitoring:nil useDynamicGeofencRadius:nil geofenceRadius:nil allowBackgroundLocationUpdates:allowBackground activityType:[self getActivityType:activityType] pausesLocationUpdatesAutomatically:pauseAutomatic useStandardLocationServices:nil accuracyFilter:accuracyFilter];
    [GeoSpark startTracking:GeoSparkTrackingModeCustom options:wrapper.customMethods];
  });
}

// Self tracking
RCT_EXPORT_METHOD(startSelfTracking:(NSString *)trackingMode){
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([trackingMode  isEqual:@"PASSIVE"]) {
      [GeoSpark startTracking:GeoSparkTrackingModePassive options:nil];
    } else if ([trackingMode  isEqual:@"BALANCED"]){
      [GeoSpark startTracking:GeoSparkTrackingModeBalanced options:nil];
    } else{
      [GeoSpark startTracking:GeoSparkTrackingModeActive options:nil];
    }
  });
}

RCT_EXPORT_METHOD(startSelfTrackingCustom:(BOOL)allowBackground pauseAutomatic:(BOOL)pauseAutomatic activityType:(NSString *)activityType desiredAccuracy:(NSString *)desiredAccuracy showBackIndicator:(BOOL)showBackIndicator distanceFilter:(NSInteger)distanceFilter accuracyFilter:(NSInteger)accuracyFilter){
  dispatch_async(dispatch_get_main_queue(), ^{
    GeoSparkTrackingCustomMethodsObjcWrapper *wrapper = [[GeoSparkTrackingCustomMethodsObjcWrapper alloc] init];
    [wrapper setUpCustomOptionsWithDesiredAccuracy:[self getDesireAccuracy:desiredAccuracy] useVisit:nil showsBackgroundLocationIndicator:showBackIndicator distanceFilter:distanceFilter useSignificant:nil useRegionMonitoring:nil useDynamicGeofencRadius:nil geofenceRadius:nil allowBackgroundLocationUpdates:allowBackground activityType:[self getActivityType:activityType] pausesLocationUpdatesAutomatically:pauseAutomatic useStandardLocationServices:nil accuracyFilter:accuracyFilter];
    [GeoSpark startTracking:GeoSparkTrackingModeCustom options:wrapper.customMethods];
  });
}


RCT_EXPORT_METHOD(stopSelfTracking){
  dispatch_async(dispatch_get_main_queue(), ^{
    [GeoSpark stopSelfTracking];
  });
}

RCT_EXPORT_METHOD(stopTracking){
  dispatch_async(dispatch_get_main_queue(), ^{
    [GeoSpark stopTracking];
  });
}

RCT_EXPORT_METHOD(enableAccuracyEngine){
  [GeoSpark enableAccuracyEngine];
}

RCT_EXPORT_METHOD(disableAccuracyEngine){
  [GeoSpark disableAccuracyEngine];
}


RCT_EXPORT_METHOD(setTrackingInAppState:(NSString *)appState){
  if ([appState  isEqual:@"ALWAYS_ON"]) {
    [GeoSpark setTrackingInAppState:GeoSparkTrackingStateAlwaysOn];
  } else if ([appState  isEqual:@"FOREGROUND"]){
    [GeoSpark setTrackingInAppState:GeoSparkTrackingStateForeground];
  } else {
    [GeoSpark setTrackingInAppState:GeoSparkTrackingStateBackground];
  }
}

RCT_EXPORT_METHOD(offlineLocationTracking:(BOOL)offline){
  [GeoSpark offlineLocationTracking:offline];
}

// Subscribe events & location

RCT_EXPORT_METHOD(subscribe:(NSString *)type userId:(NSString *)userId){
  if ([type  isEqual:@"LOCATION"]){
    [GeoSpark subscribe:GeoSparkSubscribeLocation :userId];
  }else if ([type isEqual:@"EVENTS"]){
    [GeoSpark subscribe:GeoSparkSubscribeEvents :userId];
  }else{
    [GeoSpark subscribe:GeoSparkSubscribeEvents :userId];
  }
}

RCT_EXPORT_METHOD(unsubscribe:(NSString *)type userId:(NSString *)userId){
  if ([type  isEqual:@"LOCATION"]){
    [GeoSpark unsubscribe:GeoSparkSubscribeLocation :userId];
  }else if ([type isEqual:@"EVENTS"]){
    [GeoSpark unsubscribe:GeoSparkSubscribeEvents :userId];
  }else{
    [GeoSpark unsubscribe:GeoSparkSubscribeEvents :userId];
  }
}


// Publish only & Publish Save
RCT_EXPORT_METHOD(publishAndSave:(NSDictionary *)dict){
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([[dict allKeys] count] != 0) {
      GeoSparkPublish *publish = [[GeoSparkPublish alloc] init];
      publish.meta_data = dict;
      [GeoSpark publishSave:publish];
    }else{
      [GeoSpark publishSave:nil];
    }
  });
}

RCT_EXPORT_METHOD(publishOnly:(NSArray *)array metaData:(NSDictionary *)metaData){
  
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([array count] != 0) {
      GeoSparkPublish *publish = [self publish:array metaData:metaData];
      [GeoSpark publishOnly:publish];
    }else{
      [GeoSpark publishOnly:nil];
    }
  });
}

RCT_EXPORT_METHOD(stopPublishing){
  [GeoSpark stopPublishing];
}



- (NSMutableDictionary *) userData:(GeoSparkUser *)user{
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

- (NSMutableDictionary *)tripStatus:(NSString *)string{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:string forKey:@"message"];
  return dict;
}

- (NSError *)error:(GeoSparkError *)error{
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

- (NSMutableDictionary *) createTripResponse:(GeoSparkCreateTrip *)trip{
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
      GeoSparkTripOrigin *origin = [trip.origins objectAtIndex:i];
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
      GeoSparkTripDestination *destination = [trip.destinations objectAtIndex:i];
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

- (NSArray *)activeTripResponse:(NSArray<GeoSparkTrip *>*)trips{
  
  NSMutableArray *tripsArray = [[NSMutableArray alloc] init];
  for (int i = 0; i < trips.count; i++){
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    GeoSparkTrip *trip = [trips objectAtIndex:i];
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

- (NSMutableDictionary *) userLocation:(GeoSparkLocation *)location{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:location.userId forKey:@"userId"];
  [dict setValue:location.activity forKey:@"activity"];
  [dict setValue:location.recordedAt forKey:@"recordedAt"];
  [dict setValue:location.timezoneOffset forKey:@"timezone"];
  [dict setObject:[self locationReponse:location.location] forKey:@"location"];
  return  dict;
}

- (NSMutableDictionary *) didUserLocation:(GeoSparkLocationReceived *)location{
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

- (NSMutableDictionary *) didTripStatus:(TripStatusListener *)trip{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:[NSNumber numberWithDouble:trip.latitude] forKey:@"latitude"];
  [dict setValue:[NSNumber numberWithDouble:trip.longitude] forKey:@"longitude"];
  [dict setValue:[NSNumber numberWithDouble:trip.distance] forKey:@"distance"];
  [dict setValue:[NSNumber numberWithDouble:trip.duration] forKey:@"duration"];
  [dict setValue:[NSNumber numberWithDouble:trip.speed] forKey:@"speed"];
  [dict setValue:[NSNumber numberWithDouble:trip.pace] forKey:@"pace"];
  [dict setValue:trip.startedTime forKey:@"startedTime"];
  return dict;
  
}

- (GeoSparkPublish *)publish:(NSArray *)array metaData:(NSDictionary *)metaData{
  GeoSparkPublish *publish = [[GeoSparkPublish alloc] init];
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

@end


