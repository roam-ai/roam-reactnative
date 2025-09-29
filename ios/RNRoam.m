//
//  RNRoam.m
//  RoamApp
//
//  Created by GeoSpark on 11/11/22.
//

#import "RNRoam.h"
#import <Roam/Roam.h>
#import <RoamBatchConnector/RoamBatchConnector.h>
#import <CoreLocation/CoreLocation.h>
#import <RoamGeofence/RoamGeofence.h>

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
  return @[@"location", @"location_received", @"trip_status", @"error",@"events"];
}

// Roam Delegate Methods
- (void)didUpdateLocation:(NSArray<RoamLocation *> *)locations {
  if (hasListeners) {
    [self sendEventWithName:@"location" body:[self userLocation:locations]];
  }
}
- (void)onReceiveTrip:(NSArray<RoamTripStatus *> *)tripStatus {
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

- (void)didReceiveEvents:(RoamEvents *)events {
  if (hasListeners) {
        [self sendEventWithName:@"events" body:[self didUpdateEvent:events]];
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
      [Roam startTracking:RoamTrackingModePassive options:nil handler:nil];
    } else if ([trackingMode  isEqual:@"BALANCED"]){
      [Roam startTracking:RoamTrackingModeBalanced options:nil handler:nil];
    } else{
      [Roam startTracking:RoamTrackingModeActive options:nil handler:nil];
    }
  });
}

// Custom Tracking
RCT_EXPORT_METHOD(startTrackingCustom:(BOOL)allowBackground pauseAutomatic:(BOOL)pauseAutomatic activityType:(NSString *)activityType desiredAccuracy:(NSString *)desiredAccuracy showBackIndicator:(BOOL)showBackIndicator distanceFilter:(NSInteger)distanceFilter accuracyFilter:(NSInteger)accuracyFilter updateInterval:(NSInteger)updateInterval){
  dispatch_async(dispatch_get_main_queue(), ^{
    RoamTrackingCustomMethodsObjcWrapper *wrapper = [[RoamTrackingCustomMethodsObjcWrapper alloc] init];
    [wrapper setUpCustomOptionsWithDesiredAccuracy:[self getDesireAccuracy:desiredAccuracy] useVisit:true showsBackgroundLocationIndicator:showBackIndicator distanceFilter:distanceFilter useSignificant:true useRegionMonitoring:true useDynamicGeofencRadius:true geofenceRadius:true allowBackgroundLocationUpdates:allowBackground activityType:[self getActivityType:activityType] pausesLocationUpdatesAutomatically:pauseAutomatic useStandardLocationServices:false accuracyFilter:accuracyFilter updateInterval:updateInterval];
    [Roam startTracking:RoamTrackingModeCustom options:wrapper.customMethods handler: nil];
  });
}

// Self tracking
RCT_EXPORT_METHOD(startSelfTracking:(NSString *)trackingMode){
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([trackingMode  isEqual:@"PASSIVE"]) {
      [Roam startTracking:RoamTrackingModePassive options:nil handler: nil];
    } else if ([trackingMode  isEqual:@"BALANCED"]){
      [Roam startTracking:RoamTrackingModeBalanced options:nil handler: nil];
    } else{
      [Roam startTracking:RoamTrackingModeActive options:nil handler: nil];
    }
  });
}

RCT_EXPORT_METHOD(stopTracking){
  dispatch_async(dispatch_get_main_queue(), ^{
    [Roam stopTrackingWithHandler:nil];
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
    [Roam subscribe:RoamSubscribeLocation :userId handler:nil];
  }else if ([type isEqual:@"EVENTS"]){
    [Roam subscribe:RoamSubscribeEvents :userId handler:nil];
  }else{
    [Roam subscribe:RoamSubscribeBoth :userId handler:nil];
  }
}

RCT_EXPORT_METHOD(unSubscribe:(NSString *)type userId:(NSString *)userId){
  if ([type  isEqual:@"LOCATION"]){
    [Roam unsubscribe:RoamSubscribeLocation :userId handler:nil];
  }else if ([type isEqual:@"EVENTS"]){
    [Roam unsubscribe:RoamSubscribeEvents :userId handler:nil];
  }else{
    [Roam unsubscribe:RoamSubscribeBoth :userId handler:nil];
  }
}

// Publish only & Publish Save
RCT_EXPORT_METHOD(publishAndSave:(NSDictionary *)dict){
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([[dict allKeys] count] != 0) {
      RoamPublish *publish = [[RoamPublish alloc] init];
      publish.meta_data = dict;
      [publish enableAll];
      [Roam publishSave:publish handler:nil];
    }else{
      [Roam publishSave:nil handler:nil];
    }
  });
}

RCT_EXPORT_METHOD(batchProcess:(BOOL)enable syncHour:(NSInteger)syncHour) {
  dispatch_async(dispatch_get_main_queue(), ^{
      RoamBatchPublish *batchPublish = [[RoamBatchPublish alloc] init];
      [batchPublish enableAll];
      [[RoamBatch shared] setConfigWithEnable:enable syncHour:@(syncHour) publish:batchPublish];
  });
}


RCT_EXPORT_METHOD(createGeofence:(NSDictionary *)geofence) {
  dispatch_async(dispatch_get_main_queue(), ^{
    NSNumber *geofenceId = geofence[@"id"];
    NSString *name = geofence[@"name"];
    NSNumber *latitude = geofence[@"latitude"];
    NSNumber *longitude = geofence[@"longitude"];
    
    // Check for the geofence type (circle or polygon)
    if (geofence[@"radius"]) {
      // This is a circular geofence
      NSNumber *radius = geofence[@"radius"];
      
      CircularGeofence *circularGeofence = [[CircularGeofence alloc] initWithId:geofenceId.intValue
                                                                         name:name
                                                                     latitude:latitude.doubleValue
                                                                    longitude:longitude.doubleValue
                                                                       radius:radius.doubleValue];
      
      // Pass the circular geofence data to RoamLocalGeofenceManager
      [[RoamLocalGeofenceManager shared] createGeofence:circularGeofence completion:^(enum RoamGeofenceStatus status, id<Geofence> geofence) {
        [self handleGeofenceStatus:status geofence:geofence];
      }];
      
    } else if (geofence[@"coordinates"]) {
      // This is a polygon geofence
      NSArray *coordinates = geofence[@"coordinates"];
      NSMutableArray *polygonCoordinates = [NSMutableArray array];
      
      for (NSDictionary *coordinate in coordinates) {
        CLLocationDegrees latitude = [coordinate[@"latitude"] doubleValue];
        CLLocationDegrees longitude = [coordinate[@"longitude"] doubleValue];
        CLLocationCoordinate2D coord = CLLocationCoordinate2DMake(latitude, longitude);
        [polygonCoordinates addObject:[NSValue valueWithBytes:&coord objCType:@encode(CLLocationCoordinate2D)]];

      }
      
      PolygonGeofence *polygonGeofence = [[PolygonGeofence alloc] initWithId:geofenceId.intValue
                                                                       name:name
                                                                  coordinates:polygonCoordinates];
      
      // Pass the polygon geofence data to RoamLocalGeofenceManager
      [[RoamLocalGeofenceManager shared] createGeofence:polygonGeofence completion:^(enum RoamGeofenceStatus status, id<Geofence> geofence) {
        [self handleGeofenceStatus:status geofence:geofence];
      }];
      
    } else {
      NSLog(@"Invalid geofence data. Missing radius or coordinates.");
    }
  });
}

// Helper method to handle geofence statuses
- (void)handleGeofenceStatus:(enum RoamGeofenceStatus)status geofence:(id<Geofence>)geofence {
  switch (status) {
    case RoamGeofenceStatusSuccess:
      NSLog(@"Geofence status: RoamGeofenceStatusSuccess");
      break;
    case RoamGeofenceStatusDuplicateGeofenceId:
      NSLog(@"Geofence status: RoamGeofenceStatusDuplicateGeofenceId");
      break;
    case RoamGeofenceStatusInvalidCoordinates:
      NSLog(@"Geofence status: RoamGeofenceStatusInvalidCoordinates");
      break;
    case RoamGeofenceStatusInvalidRadiusRange:
      NSLog(@"Geofence status: RoamGeofenceStatusInvalidRadiusRange");
      break;
    case RoamGeofenceStatusInvalidPolygonRange:
      NSLog(@"Geofence status: RoamGeofenceStatusInvalidPolygonRange");
      break;
    case RoamGeofenceStatusInvalidGeofenceId:
      NSLog(@"Geofence status: RoamGeofenceStatusInvalidGeofenceId");
      break;
  }
  
  // Handle geofence data after creation (check whether it's a Circle or Polygon)
  if ([(NSObject *)geofence isKindOfClass:[CircularGeofence class]]) {
        CircularGeofence *circular = (CircularGeofence *)geofence;
        NSLog(@"Radius: %@", @(circular.radius));
    } else if ([(NSObject *)geofence isKindOfClass:[PolygonGeofence class]]) {
        PolygonGeofence *polygon = (PolygonGeofence *)geofence;
        NSLog(@"Coordinates: %@", polygon.coordinates);
    }

}

RCT_EXPORT_METHOD(publishOnly:(NSArray *)array metaData:(NSDictionary *)metaData){
  
  dispatch_async(dispatch_get_main_queue(), ^{
    if ([array count] != 0) {
      RoamPublish *publish = [self publish:array metaData:metaData];
      [Roam publishOnly:publish handler:nil];
    }else{
      [Roam publishOnly:nil handler:nil];
    }
  });
}

RCT_EXPORT_METHOD(stopPublishing){
  [Roam stopPublishingWithHandler:nil];
}

RCT_EXPORT_METHOD(updateLocationWhenStationary:(NSInteger)interval){
  dispatch_async(dispatch_get_main_queue(), ^{
    [Roam updateLocationWhenStationary:interval];
  });
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

RCT_EXPORT_METHOD(startQuickTrip:(NSDictionary *)dict trackingMode:(NSString *)tracking customTrackingOptions:(NSDictionary *)customDict :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  
  [Roam startTrip:[self createTripdict:dict] :[self trackingMode:tracking] :[self customMethod:customDict]  handler:^(RoamTripResponse * response, RoamTripError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self tripResponse:response], nil];
      successCallback(success);
    }else{
      errorCallback([self tripError:error]);
    }
  }];
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
  NSLog(@"updateTrip %@",dict);
  RoamTrip *trip = [self createTripdict:dict];
  [Roam updateTrip:trip handler:^(RoamTripResponse * response, RoamTripError * error) {
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
  NSLog(@"synctrip %@",tripId);
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

RCT_EXPORT_METHOD(requestActivityPermission){
  dispatch_async(dispatch_get_main_queue(), ^{
    [Roam requestActivityPermission];
  });
}


RCT_EXPORT_METHOD(checkActivityPermission:(RCTResponseSenderBlock)callback){
  NSMutableArray *array = [[NSMutableArray alloc] initWithObjects:[self checkPermission:[Roam checkActivityPermission]], nil];
  callback(array);
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



// Tracking config

RCT_EXPORT_METHOD(setTrackingConfig:(NSInteger)accuracy timeout:(NSInteger)timeout discard:(BOOL)discard :(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  
  [Roam setTrackingConfigWithAccuracy:accuracy timeout:timeout discardLocation:discard handler:^(RoamLocationConfig * config, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self trackingConfig:config], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }
  }];
  
}

RCT_EXPORT_METHOD(getTrackingConfig:(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam getTrackingConfigWithHandler:^(RoamLocationConfig * config, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self trackingConfig:config], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }

  }];
}

RCT_EXPORT_METHOD(resetTrackingConfig:(RCTResponseSenderBlock)successCallback rejecter:(RCTResponseErrorBlock)errorCallback){
  [Roam resetTrackingConfigWithHandler:^(RoamLocationConfig * config, RoamError * error) {
    if (error == nil) {
      NSMutableArray *success = [[NSMutableArray alloc] initWithObjects:[self trackingConfig:config], nil];
      successCallback(success);
    }else{
      errorCallback([self error:error]);
    }

  }];
}


- (NSMutableDictionary *) trackingConfig:(RoamLocationConfig *)config{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:[NSNumber numberWithInt:config.accuracy] forKey:@"accuracy"];
  [dict setValue:[NSNumber numberWithInt:config.timeout] forKey:@"timeout"];
  [dict setValue:[NSNumber numberWithBool:config.discardLocation] forKey:@"discardLocation"];

  return dict;
}


- (NSMutableDictionary *) userData:(RoamUser *)user{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:user.userId forKey:@"userId"];
  [dict setValue:user.userDescription forKey:@"description"];
  [dict setValue:[NSNumber numberWithBool:user.geofenceEvents] forKey:@"geofenceEvents"];
  [dict setValue:[NSNumber numberWithBool:user.locationEvents] forKey:@"locationEvents"];
  [dict setValue:[NSNumber numberWithBool:user.tripsEvents] forKey:@"tripsEvents"];
  [dict setValue:[NSNumber numberWithBool:user.nearbyEvents] forKey:@"nearbyEvents"];
  [dict setValue:[NSNumber numberWithBool:user.eventsListener] forKey:@"eventListenerStatus"];
  [dict setValue:[NSNumber numberWithBool:user.locationListener] forKey:@"locationListenerStatus"];
  
  return dict;
}

- (NSDictionary *)centroidToDictionary:(RoamCentroid *)centroid {
    NSMutableDictionary *centroidDict = [[NSMutableDictionary alloc] init];
    // Convert positions array to dictionaries
    NSMutableArray *positionsArray = [[NSMutableArray alloc] init];
    for (Position *position in centroid.positions) {
        [positionsArray addObject:@{
            @"latitude": @(position.latitude),
            @"longitude": @(position.longitude)
        }];
    }
    [centroidDict setObject:positionsArray forKey:@"positions"];
    // Add centroid coordinate
    [centroidDict setObject:@{
        @"latitude": @(centroid.centroidCoordinate.latitude),
        @"longitude": @(centroid.centroidCoordinate.longitude)
    } forKey:@"centroidCoordinate"];
    return centroidDict;
}

- (NSMutableArray *) userLocation:(NSArray<RoamLocation *> *)locations{
  
  NSMutableArray *array = [[NSMutableArray alloc] init];
  for (RoamLocation* location in locations) {
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    [dict setValue:location.userId forKey:@"userId"];
    [dict setValue:location.activity forKey:@"activity"];
    [dict setValue:location.recordedAt forKey:@"recordedAt"];
    [dict setValue:location.timezoneOffset forKey:@"timezone"];
    [dict setValue:location.trackingMode forKey:@"trackingMode"];
    [dict setValue:location.appContext forKey:@"appContext"];
    [dict setValue:location.batteryStatus forKey:@"batteryStatus"];

    NSMutableArray *eventDictArray = [NSMutableArray array];
    for (RoamGeofenceEvent *event in location.localGeofenceEvents) {
        NSDictionary *eventDict = @{
            @"eventType": event.eventType ?: @"",
            @"geofenceId": @(event.geofenceId)
        };
        [eventDictArray addObject:eventDict];
    }
    [dict setValue:eventDictArray forKey:@"localGeofenceEvents"];
    NSMutableArray *bluetoothSignalStrengths = [NSMutableArray array];
    for (BluetoothSignal *event in location.bluetoothSignalStrengths) {
        NSDictionary *eventDict = @{
          @"name": event.name ?: @"",
          @"RSSI": event.rssi ?: @"",
        };
        [bluetoothSignalStrengths addObject:eventDict];
    }
    [dict setValue:bluetoothSignalStrengths forKey:@"bluetoothSignalStrengths"];
    [dict setValue:[NSNumber numberWithInt:location.batteryRemaining] forKey:@"batteryRemaining"];
    [dict setValue:[NSNumber numberWithBool:location.batterySaver] forKey:@"batterySaver"];
    [dict setValue:[NSNumber numberWithBool:location.networkStatus] forKey:@"networkStatus"];
    [dict setValue:[NSNumber numberWithBool:location.locationPermission] forKey:@"locationPermission"];
    [dict setObject:[self locationReponse:location.location] forKey:@"location"];
    [dict setValue:location.deviceModel forKey:@"deviceModel"];
    [dict setValue:location.networkType forKey:@"networkType"];
    [dict setValue:location.networkState forKey:@"networkState"];
    [dict setValue:location.bundleId forKey:@"buildID"];
    [dict setValue:location.kernelVersion forKey:@"kernelVersion"];
    [dict setValue:location.ipAddress forKey:@"ipAddress"];
    [dict setValue:location.deviceName forKey:@"deviceName"];
    [dict setValue:location.systemName forKey:@"systemName"];
    [dict setValue:location.appName forKey:@"appName"];
    [dict setValue:location.appId forKey:@"appId"];
    [dict setValue:location.locationId forKey:@"locationId"];
    [dict setValue:location.sdkVerison forKey:@"sdkVerison"];
    [dict setValue:location.osVersion forKey:@"osVersion"];
    [dict setValue:location.idfv forKey:@"idfv"];
    [dict setValue:location.idfa forKey:@"idfa"];
    [dict setValue:location.wifiSSID forKey:@"wifiSSID"];
    [dict setValue:location.localeCountry forKey:@"localeCountry"];
    [dict setValue:location.localeLanguage forKey:@"localeLanguage"];
    [dict setValue:location.carrierName forKey:@"carrierName"];
    [dict setValue:location.appInstallationDate forKey:@"appInstallationDate"];
    [dict setValue:location.appVersion forKey:@"appVersion"];
    [dict setValue:location.source forKey:@"source"]; 
    [dict setValue:location.manufacturer forKey:@"manufacturer"];
    [dict setValue:location.publicIpAddress forKey:@"publicIpAddress"];
    [dict setValue:location.mcc forKey:@"mcc"];
    [dict setValue:location.mnc forKey:@"mnc"];
    [dict setValue:location.carrierIsoCountryCode forKey:@"carrierIsoCountryCode"];
    [dict setValue:location.rat forKey:@"rat"];
    [dict setValue:location.wifiBSSID forKey:@"wifiBSSID"];
    [dict setValue:location.wifiProxySettingHost forKey:@"wifiProxySettingHost"];
    [dict setValue:location.wifiProxySettingPort forKey:@"wifiProxySettingPort"];
    [dict setValue:location.wifiSubnetMask forKey:@"wifiSubnetMask"];
    [dict setValue:[NSNumber numberWithDouble:location.speed] forKey:@"speed"];
    if (location.centroid) {
             [dict setValue:[self centroidToDictionary:location.centroid] forKey:@"centroid"];
         }
    [array addObject:dict];
  }
  
  return array;
}

- (NSMutableArray *) didUpdateEvent:(RoamEvents *)events{
  
  NSMutableArray *array = [[NSMutableArray alloc] init];
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:events.activity forKey:@"activity"];
  [dict setValue:events.eventDescription forKey:@"eventDescription"];
  [dict setValue:events.course forKey:@"course"];
  [dict setValue:events.createdAt forKey:@"createdAt"];
  [dict setValue:events.distance forKey:@"distance"];
  [dict setValue:events.eventSource forKey:@"eventSource"];
  [dict setValue:events.eventVersion forKey:@"eventVersion"];
  [dict setValue:events.geofenceId forKey:@"geofenceId"];
  [dict setValue:events.horizontalAccuracy forKey:@"horizontalAccuracy"];
  [dict setValue:events.locationId forKey:@"locationId"];
  [dict setValue:events.nearbyUserId forKey:@"nearbyUserId"];

  [dict setValue:events.recordedAt forKey:@"recordedAt"];
  [dict setValue:events.speed forKey:@"speed"];
  [dict setValue:events.tripId forKey:@"tripId"];
  [dict setValue:events.userId forKey:@"userId"];
  [dict setValue:events.veritcalAccuracy forKey:@"veritcalAccuracy"];

  [dict setValue:[events.coordinates firstObject] forKey:@"longitude"];
  [dict setValue:[events.coordinates lastObject] forKey:@"latitude"];
  [dict setValue:events.location_metadata forKey:@"metadata"];
  [dict setValue:events.eventType forKey:@"eventType"];
  [array addObject:dict];
  return  array;
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

- (NSMutableArray *) didTripStatus:(NSArray<RoamTripStatus *> *)trips{
  NSMutableArray *array = [[NSMutableArray alloc] init];
  for (RoamTripStatus* trip in trips) {
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    [dict setValue:trip.tripId forKey:@"tripId"];
    [dict setValue:trip.tripState forKey:@"tripState"];
    [dict setValue:[NSNumber numberWithDouble:trip.speed] forKey:@"speed"];
    [dict setValue:[NSNumber numberWithDouble:trip.distance] forKey:@"distance"];
    [dict setValue:[NSNumber numberWithDouble:trip.duration] forKey:@"duration"];
    [dict setValue:[NSNumber numberWithDouble:trip.pace] forKey:@"pace"];
    [dict setValue:trip.startedTime forKey:@"startedTime"];
    [dict setValue:trip.recordedAt forKey:@"recordedAt"];
    [dict setValue:[NSNumber numberWithDouble:trip.latitude] forKey:@"latitude"];
    [dict setValue:[NSNumber numberWithDouble:trip.longitude] forKey:@"longitude"];
    [dict setValue:[NSNumber numberWithDouble:trip.altitude] forKey:@"altitude"];
    [dict setValue:[NSNumber numberWithDouble:trip.elevationGain] forKey:@"elevationGain"];
    
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
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  dict[@"errorDescription"] = error.errorDescription;
  dict[@"errors"] = error.errors;
  return [NSError errorWithDomain:error.message code:error.code userInfo:dict];
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
  [dict setValue:[NSNumber numberWithDouble:location.verticalAccuracy] forKey:@"verticalAccuracy"];
  [dict setValue:[NSNumber numberWithDouble:location.altitude] forKey:@"altitude"];
  [dict setValue:[NSNumber numberWithDouble:location.speed] forKey:@"speed"];
  [dict setValue:[NSNumber numberWithDouble:location.course] forKey:@"course"];
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
  NSString *tripDescription = [dict objectForKey:@"tripDescription"];
  NSString *tripId = [dict objectForKey:@"tripId"];
  NSString *tripName = [dict objectForKey:@"tripName"];
  NSDictionary *metaData = [dict objectForKey:@"metadata"];
  NSArray *stops = [dict objectForKey:@"stops"];
  BOOL isLocal = [dict[@"isLocal"] boolValue];
  response.isLocal = isLocal;
  
  if (isEmpty(tripId) == false){
    response.tripId = tripId;
   }
  
  if (isEmpty(tripDescription) == false){
    response.tripDescription = tripDescription;
  }
  if (isEmpty(tripName) == false){
    response.tripName = tripName;
  }
  if (metaData != (NSDictionary*) [NSNull null]){
    response.metadata = metaData;
  }
  
  if ([stops isKindOfClass:[NSArray class]] && stops.count > 0) {
     response.stops = [self creatTripStops:stops];
   }else{
     response.stops = [[NSArray alloc] init];
   }
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
    NSString *stopId = [stop objectForKey:@"RoamTripStop"];
    NSString *stopName = [stop objectForKey:@"stopName"];
    NSString *stopDescription = [stop objectForKey:@"stopDescription"];
    NSString *address = [stop objectForKey:@"address"];
    int geometryRadius = [[stop objectForKey:@"geometryRadius"] intValue];
    NSArray *coord = [stop objectForKey:@"geometryCoordinates"];
    
    if (isEmpty(stopId) == false){
      tripStop.stopId = stopId;
    }
    if (isEmpty(stopName) == false){
      tripStop.stopName = stopName;
    }
    if (isEmpty(stopDescription) == false){
      tripStop.stopDescription = stopDescription;
    }
    if (isEmpty(address) == false){
      tripStop.address = address;
    }
    if (geometryRadius != 0){
      tripStop.geometryRadius = [NSNumber numberWithInteger:geometryRadius];
    }
    if ([coord count] != 0){
      NSMutableArray *stopCoord = [[NSMutableArray alloc] init];
      [stopCoord addObjectsFromArray:coord];
      tripStop.geometryCoordinates = stopCoord;
    }
    [array addObject:tripStop];
  }
  return array;
}

BOOL isEmpty(id thing) {
  return thing == nil
  || [thing isKindOfClass:[NSNull class]]
  || ([thing respondsToSelector:@selector(length)]
      && ![thing respondsToSelector:@selector(count)]
      && [(NSData *)thing length] == 0)
  || ([thing respondsToSelector:@selector(count)]
      && [thing count] == 0);
}

-(NSMutableDictionary *) tripResponse:(RoamTripResponse *)response {
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:response.message forKey:@"message"];
  [dict setValue:response.code forKey:@"code"];
  [dict setValue:response.errorDescription forKey:@"description"];
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
    [dict setValue:[self tripRoutesCoordinates:route.coordinates] forKey:@"coordinates"];
    [array addObject:dict];
    
  }
  return  array;
}

-(NSMutableDictionary *) tripRoutesCoordinates:(RoamTripRoutecoordinates *)tripRoutes {
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    [dict setValue:tripRoutes.type forKey:@"type"];
    [dict setValue:tripRoutes.coordinates forKey:@"coordinates"];
  return  dict;
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
  [dict setValue:[self activeTrips:response.trips] forKey:@"trips"];
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

-(RoamTrackingMode *)trackingMode:(NSString *)tracking {
  if ([tracking  isEqual: @"ACTIVE"]) {
    return  RoamTrackingModeActive;
  }else if ([tracking isEqual: @"BALANCED"]){
    return  RoamTrackingModeBalanced;
  }else if ([tracking isEqual: @"PASSIVE"]){
    return RoamTrackingModePassive;
  }else{
    return  RoamTrackingModeCustom;
  }
}

- (NSMutableDictionary *)BatchConfigResonse:(RoamBatchConfig *)config{
  NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
  [dict setValue:[NSNumber numberWithInt:config.batchCount] forKey:@"batchCount"];
  [dict setValue:[NSNumber numberWithInt:config.batchWindow] forKey:@"batchWindow"];
  [dict setValue:config.networkState forKey:@"networkState"];
  return  dict;
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

-(RoamTrackingCustomMethods *)customMethod:(NSDictionary *)dict {
  RoamTrackingCustomMethodsObjcWrapper *wrapper = [[RoamTrackingCustomMethodsObjcWrapper alloc] init];
  
  CLActivityType * activityType = [self getActivityType:[dict objectForKey:@"activityType"]];
  LocationAccuracy * accuracy = [self getDesireAccuracy:[dict objectForKey:@"desiredAccuracyIOS"]];
  BOOL allowBackground = [[dict objectForKey:@"allowBackgroundLocationUpdates"] boolValue];
  BOOL pausesLocationUpdatesAutomatically = [[dict objectForKey:@"pausesLocationUpdatesAutomatically"] boolValue];
  BOOL showsBackgroundLocationIndicator = [[dict objectForKey:@"showsBackgroundLocationIndicator"] boolValue];
  int accuracyFilter = [[dict objectForKey:@"accuracyFilter"] integerValue];
  int distanceFilter = [[dict objectForKey:@"distanceFilter"] integerValue];
  int updateInterval = [[dict objectForKey:@"updateInterval"] integerValue];
  
  [wrapper setUpCustomOptionsWithDesiredAccuracy:accuracy useVisit:true showsBackgroundLocationIndicator:showsBackgroundLocationIndicator distanceFilter:distanceFilter useSignificant:true useRegionMonitoring:true useDynamicGeofencRadius:true geofenceRadius:true allowBackgroundLocationUpdates:allowBackground activityType:activityType pausesLocationUpdatesAutomatically:pausesLocationUpdatesAutomatically useStandardLocationServices:false accuracyFilter:accuracyFilter updateInterval:updateInterval];
  return  wrapper.customMethods;
}
@end
