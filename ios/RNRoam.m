//
//  RNRoam.m
//  RoamApp
//
//  Created by GeoSpark on 11/11/22.
//

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
  }
}

- (void)didReceiveTripStatus:(NSArray<RoamTripListener *> *)tripStatus{
  if (hasListeners) {
  }
}

- (void)didReceiveUserLocation:(RoamLocationReceived *)location{
  if (hasListeners) {
  }
}

- (void)onError:(RoamError *)error{
  if (hasListeners) {
  }
}


-(void)startObserving {
  hasListeners = YES;
}

- (void)stopObserving {
  hasListeners = NO;
}


// Create User
RCT_EXPORT_METHOD(createUser:(NSString *)userDescription meta:(NSDictionary *)dict :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam createUser:userDescription:dict handler:^(RoamUser * user, RoamError * error) {
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
RCT_EXPORT_METHOD(setDescription:(NSString *)userDescription metaData:(NSDictionary *)dict){
  [Roam updateUser:userDescription :dict];
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

// subscribeTripStatus
RCT_EXPORT_METHOD(subscribeTripStatus:(NSString *)tripId){
  [Roam subscribeTrip:tripId];
}

// unSubscribeTripStatus
RCT_EXPORT_METHOD(unSubscribeTripStatus:(NSString *)tripId){
  [Roam unsubscribeTrip:tripId];
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
  dispatch_async(dispatch_get_main_queue(), ^{
    [Roam getCurrentLocation:accuracy handler:^(CLLocation * location, RoamError * error) {
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
    [Roam updateCurrentLocation:accuracy:NULL];
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

RCT_EXPORT_METHOD(enableAccuracyEngine){
  [Roam enableAccuracyEngine];
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
    [Roam subscribe:RoamSubscribeBoth :userId];
  }
}

RCT_EXPORT_METHOD(unsubscribe:(NSString *)type userId:(NSString *)userId){
  if ([type  isEqual:@"LOCATION"]){
    [Roam unsubscribe:RoamSubscribeLocation :userId];
  }else if ([type isEqual:@"EVENTS"]){
    [Roam unsubscribe:RoamSubscribeEvents :userId];
  }else{
    [Roam unsubscribe:RoamSubscribeBoth :userId];
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


// Trips
RCT_EXPORT_METHOD(createTrip:(NSDictionary *)dict :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  
  [Roam createTrip:[self createTripdict:dict] handler:^(RoamTripResponse * response, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripResponse:response], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}

RCT_EXPORT_METHOD(startQuickTrip:(NSDictionary *)dict :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
}

RCT_EXPORT_METHOD(startTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam startTrip:tripId handler:^(RoamTripResponse * response, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripResponse:response], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}

RCT_EXPORT_METHOD(updateTrip:(NSDictionary *)dict :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  
  [Roam updateTrip:[self createTripdict:dict] handler:^(RoamTripResponse * response, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripResponse:response], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}

RCT_EXPORT_METHOD(endTrip:(NSString *)tripId forceStopTracking:(BOOL) forceStop:(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam endTrip:tripId: forceStop handler:^(RoamTripResponse * response, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripResponse:response], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}

RCT_EXPORT_METHOD(pauseTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam pauseTrip:tripId handler:^(RoamTripResponse * response, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripResponse:response], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}

RCT_EXPORT_METHOD(resumeTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam resumeTrip:tripId handler:^(RoamTripResponse * response, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripResponse:response], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}

RCT_EXPORT_METHOD(syncTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam syncTrip:tripId handler:^(RoamTripSync * response, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self syncTripResponse:response], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}

RCT_EXPORT_METHOD(deleteTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam deleteTrip:tripId handler:^(RoamTripDelete * response, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self deleteTripResponse:response], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}


RCT_EXPORT_METHOD(getTrip:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam getTrip:tripId handler:^(RoamTripResponse * reponse, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripResponse:reponse], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}

RCT_EXPORT_METHOD(getActiveTrips:(BOOL)isLocal :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam getActiveTrips:isLocal handler:^(RoamActiveTripResponse * activeResponse, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self activeTripsResponse:activeResponse], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}

RCT_EXPORT_METHOD(getTripSummary:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam getTripSummary:tripId handler:^(RoamTripResponse * response, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripResponse:response], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
}

RCT_EXPORT_METHOD(isTripSynced:(NSString *)tripId :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam isTripSynced:tripId handler:^(BOOL isSynced, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self isTripSyncedResponse:isSynced], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
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

- (NSMutableArray *) didTripStatus:(NSArray<RoamTripListener *> *)trips{
  NSMutableArray *array = [[NSMutableArray alloc] init];
  for (RoamTripListener* trip in trips) {
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
//    [dict setValue:[NSNumber numberWithDouble:trip.latitude] forKey:@"latitude"];
//    [dict setValue:[NSNumber numberWithDouble:trip.longitude] forKey:@"longitude"];
//    [dict setValue:[NSNumber numberWithDouble:trip.distance] forKey:@"distance"];
//    [dict setValue:[NSNumber numberWithDouble:trip.duration] forKey:@"duration"];
//    [dict setValue:[NSNumber numberWithDouble:trip.speed] forKey:@"speed"];
//    [dict setValue:[NSNumber numberWithDouble:trip.pace] forKey:@"pace"];
    [dict setValue:trip.startedTime forKey:@"startedTime"];
//    [dict setValue:trip.recordedAt forKey:@"recordedAt"];
    [array addObject:dict];
  }
  return array;
}

- (NSMutableDictionary *) userLogout:(NSString *)status{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:status forKey:@"message"];
  return dict;
}

- (NSError *)error:(RoamError *)error{
  return [NSError errorWithDomain:error.message code:[self removeString:error.code] userInfo:nil];
}

- (NSError *)tripError:(RoamTripError *)error {
  return [NSError errorWithDomain:error.message code:error.code userInfo:nil];
}

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

- (NSMutableDictionary *)locationReponse:(CLLocation *)location{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:[NSNumber numberWithDouble:location.coordinate.latitude] forKey:@"latitude"];
  [dict setValue:[NSNumber numberWithDouble:location.coordinate.longitude] forKey:@"longitude"];
  [dict setValue:[NSNumber numberWithDouble:location.horizontalAccuracy] forKey:@"accuracy"];
  [dict setValue:[NSNumber numberWithDouble:location.altitude] forKey:@"altitude"];
  [dict setValue:[NSNumber numberWithDouble:location.speed] forKey:@"speed"];
  return  dict;
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

- (NSString *)checkPermission:(BOOL)isEnabled{
  if (isEnabled){
    return @"GRANTED";
  } else {
    return @"DENIED";
  }
}

- (LocationAccuracy)getDesireAccuracy:(NSString *)accuracy{
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

- (CLActivityType)getActivityType:(NSString *)type{
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

- (RoamTrip *) createTripdict:(NSDictionary *)dict{
  RoamTrip *response = [RoamTrip alloc];
  response.tripId = [dict objectForKey:@"tripId"];
  response.tripDescription = [dict objectForKey:@"tripDescription"];
  response.tripState = [dict objectForKey:@"tripState"];
  response.tripName = [dict objectForKey:@"tripName"];
  response.totalDistance = [dict objectForKey:@"totalDistance"];
  response.totalDuration = [dict objectForKey:@"totalDuration"];
  response.totalElevationGain = [dict objectForKey:@"totalElevationGain"];
  response.updatedAt = [dict objectForKey:@"updatedAt"];
  response.createdAt = [dict objectForKey:@"createdAt"];
  response.startedAt = [dict objectForKey:@"startedAt"];
  response.endedAt = [dict objectForKey:@"tripId"];
  response.metadata = [dict objectForKey:@"metadata"];
  response.isLocal = [dict objectForKey:@"isLocal"];
  response.syncStatus = [dict objectForKey:@"syncStatus"];
  response.stops = [self creatTripStops:[dict objectForKey:@"stops"]];
  return  response;
}

- (RoamTripUser *) user:(NSDictionary *)dict {
  RoamTripUser *user = [RoamTripUser alloc];
  user.userName = [dict  objectForKey:@"userName"];
  user.userId = [dict  objectForKey:@"userId"];
  user.metadata = [dict  objectForKey:@"metadata"];
  user.userDescription = [dict objectForKey:@"userDescription"];
  return  user;
}

-(NSMutableArray <RoamTripStop *> *) creatTripStops:(NSArray *)stops {
  NSMutableArray *array = [[NSMutableArray alloc] init];
  for (NSDictionary* stop in stops) {
    RoamTripStop *tripStop = [RoamTripStop alloc];
    tripStop.stopId = [stop objectForKey:@"RoamTripStop"];
    tripStop.stopName = [stop objectForKey:@"stopName"];
    tripStop.stopDescription = [stop objectForKey:@"stopDescription"];
    tripStop.address = [stop objectForKey:@"address"];
    tripStop.geometryRadius = [stop objectForKey:@"geometryRadius"];
    tripStop.geometryCoordinates = [stop objectForKey:@"geometryCoordinates"];
  }
  return array;
}

-(NSMutableDictionary *) tripResponse:(RoamTripResponse *)response {
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:response.message forKey:@"message"];
  [dict setValue:response.code forKey:@"code"];
  [dict setValue:response.errorDescription forKey:@"errorDescription"];
  [dict setValue:[self trip:response.trip] forKey:@"trip"];

  return  dict;
}

-(NSMutableDictionary *) trip:(RoamTrip *)response {
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:response.tripId forKey:@"tripId"];
  [dict setValue:response.tripDescription forKey:@"tripDescription"];
  [dict setValue:response.tripState forKey:@"tripState"];
  [dict setValue:response.tripName forKey:@"tripName"];
  [dict setValue:response.totalDistance forKey:@"totalDistance"];
  [dict setValue:response.totalDuration forKey:@"totalDuration"];
  [dict setValue:response.totalElevationGain forKey:@"totalElevationGain"];
  [dict setValue:response.updatedAt forKey:@"updatedAt"];
  [dict setValue:response.createdAt forKey:@"createdAt"];
  [dict setValue:response.startedAt forKey:@"startedAt"];
  [dict setValue:response.endedAt forKey:@"endedAt"];
  [dict setValue:response.metadata forKey:@"metadata"];
  [dict setValue:[NSNumber numberWithBool:response.isLocal] forKey:@"isLocal"];
  [dict setValue:response.syncStatus forKey:@"syncStatus"];

  [dict setValue:[self tripEvents:response.events] forKey:@"events"];
  [dict setValue:[self tripStops:response.stops] forKey:@"stops"];
  [dict setValue:[self tripRoutess:response.routes] forKey:@"routes"];
  
  return dict;
}

-(NSMutableArray *) tripEvents:(NSArray<RoamTripEvents *> *)events {
  
  NSMutableArray *array = [[NSMutableArray alloc] init];
  for (RoamTripEvents* event in events) {
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    [dict setValue:event.eventsId forKey:@"eventsId"];
    [dict setValue:event.tripId forKey:@"tripId"];
    [dict setValue:event.userId forKey:@"userId"];
    [dict setValue:event.eventType forKey:@"eventType"];
    [dict setValue:event.createAt forKey:@"createAt"];
    [dict setValue:event.eventSource forKey:@"eventSource"];
    [dict setValue:event.eventVersion forKey:@"eventVersion"];
    [array addObject:dict];
  }
  return array;
}

-(NSMutableArray *) tripStops:(NSArray<RoamTripStop *> *)stops {
  NSMutableArray *array = [[NSMutableArray alloc] init];
  for (RoamTripStop* stop in stops) {
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    [dict setValue:stop.stopId forKey:@"stopId"];
    [dict setValue:stop.stopName forKey:@"stopName"];
    [dict setValue:stop.stopDescription forKey:@"stopDescription"];
    [dict setValue:stop.address forKey:@"address"];
    [dict setValue:stop.updatedAt forKey:@"updatedAt"];
    [dict setValue:stop.createdAt forKey:@"createdAt"];
    [dict setValue:stop.arrivedAt forKey:@"arrivedAt"];
    [dict setValue:stop.departedAt forKey:@"departedAt"];
    [dict setValue:stop.geometryType forKey:@"geometryType"];
    [dict setValue:stop.metadata forKey:@"metadata"];
    [dict setValue:stop.geometryRadius forKey:@"geometryRadius"];
    [dict setValue:stop.geometryCoordinates forKey:@"geometryCoordinates"];

    [array addObject:dict];
  }
  return array;
}

-(NSMutableArray *) tripRoutess:(NSArray<RoamTripRoutes *> *)tripRoutes {
  
  NSMutableArray *array = [[NSMutableArray alloc] init];
  for (RoamTripRoutes* route in tripRoutes) {
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    [dict setValue:route.activity forKey:@"activity"];
    [dict setValue:route.recordedAt forKey:@"recordedAt"];
    [dict setValue:route.altitude forKey:@"altitude"];
    [dict setValue:route.duration forKey:@"duration"];
    [dict setValue:route.elevationGain forKey:@"elevationGain"];
    [dict setValue:route.distance forKey:@"distance"];
    [dict setValue:route.coordinates forKey:@"coordinates"];
    [array addObject:dict];

  }
  return  array;
}

-(NSMutableDictionary *) syncTripResponse:(RoamTripSync *)response {
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:response.message forKey:@"message"];
  [dict setValue:response.messageDescription forKey:@"messageDescription"];
  [dict setValue:response.code forKey:@"code"];
  [dict setValue:response.trip_id forKey:@"trip_id"];
  [dict setValue:[NSNumber numberWithBool:response.isSynced] forKey:@"isSynced"];
  return  dict;
}

-(NSMutableDictionary *) deleteTripResponse:(RoamTripDelete *)response {
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:response.message forKey:@"message"];
  [dict setValue:response.messageDescription forKey:@"messageDescription"];
  [dict setValue:response.code forKey:@"code"];
  [dict setValue:response.tripId forKey:@"tripId"];
  [dict setValue:[NSNumber numberWithBool:response.isDeleted] forKey:@"isDeleted"];
  return  dict;
}

-(NSMutableDictionary *) activeTripsResponse:(RoamActiveTripResponse *)response {
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:response.message forKey:@"message"];
  [dict setValue:response.code forKey:@"code"];
  [dict setValue:response.errorDescription forKey:@"errorDescription"];
  [dict setValue:[NSNumber numberWithBool:response.has_more] forKey:@"has_more"];
  [dict setValue:response.trips forKey:@"trips"];
  return  dict;
}

-(NSMutableArray *) activeTrips:(NSArray<RoamTrip *> *)trips {
  
  NSMutableArray *array = [[NSMutableArray alloc] init];
  for (RoamTrip* trip in trips) {
    NSMutableDictionary *dict = [self trip:trip];
    [array addObject:dict];
  }
  return  array;
}

-(NSMutableDictionary *) isTripSyncedResponse:(BOOL)isSynced{
  
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:[NSNumber numberWithBool:isSynced] forKey:@"isSynced"];
  return dict;
}
@end
