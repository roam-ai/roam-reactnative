import { NativeEventEmitter, NativeModules } from "react-native";


if (!NativeModules.RNRoam) {
  throw new Error("NativeModules.RNRoam is undefined");
}

const eventEmitter = new NativeEventEmitter(NativeModules.RNRoam);


const TrackingMode = {
  ACTIVE: "ACTIVE",
  BALANCED: "BALANCED",
  PASSIVE: "PASSIVE",
};

const DesiredAccuracy = {
  HIGH: "HIGH",
  MEDIUM: "MEDIUM",
  LOW: "LOW",
};

const AppState = {
  ALWAYS_ON: "ALWAYS_ON",
  FOREGROUND: "FOREGROUND",
  BACKGROUND: "BACKGROUND",
};

const DesiredAccuracyIOS = {
  BESTFORNAVIGATION: "BESTFORNAVIGATION",
  BEST: "BEST",
  NEAREST_TEN_METERS: "NEAREST_TEN_METERS",
  HUNDRED_METERS: "HUNDRED_METERS",
  KILO_METERS: "KILO_METERS",
  THREE_KILOMETERS: "THREE_KILOMETERS",
};

const ActivityType = {
  OTHER: "OTHER",
  AUTO_NAVIGATION: "AUTO_NAVIGATION",
  OTHER_NAVIGATION: "OTHER_NAVIGATION",
  FITNESS: "FITNESS",
};

const SubscribeListener = {
  EVENTS: "EVENTS",
  LOCATION: "LOCATION",
  BOTH: "BOTH",
};

const Publish = {
  APP_ID: "APP_ID",
  USER_ID: "USER_ID",
  GEOFENCE_EVENTS: "GEOFENCE_EVENTS",
  LOCATION_EVENTS: "LOCATION_EVENTS",
  NEARBY_EVENTS: "NEARBY_EVENTS",
  TRIPS_EVENTS: "TRIPS_EVENTS",
  LOCATION_LISTENER: "LOCATION_LISTENER",
  EVENT_LISTENER: "EVENT_LISTENER",
  ALTITUDE: "ALTITUDE",
  COURSE: "COURSE",
  SPEED: "SPEED",
  VERTICAL_ACCURACY: "VERTICAL_ACCURACY",
  HORIZONTAL_ACCURACY: "HORIZONTAL_ACCURACY",
  APP_CONTEXT: "APP_CONTEXT",
  ALLOW_MOCKED: "ALLOW_MOCKED",
  BATTERY_REMAINING: "BATTERY_REMAINING",
  BATTERY_SAVER: "BATTERY_SAVER",
  BATTERY_STATUS: "BATTERY_STATUS",
  ACTIVITY: "ACTIVITY",
  AIRPLANE_MODE: "AIRPLANE_MODE",
  DEVICE_MANUFACTURE: "DEVICE_MANUFACTURE",
  DEVICE_MODEL: "DEVICE_MODEL",
  TRACKING_MODE: "TRACKING_MODE",
  LOCATIONPERMISSION: "LOCATIONPERMISSION",
  NETWORK_STATUS: "NETWORK_STATUS",
  GPS_STATUS: "GPS_STATUS",
  OS_VERSION: "OS_VERSION",
  RECORDERD_AT: "RECORDERD_AT",
  TZ_OFFSET: "TZ_OFFSET",
};

const createUser = (description: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.createUser(description, successCallback, errorCallback);
};

const getUser = (userid: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.getUser(userid, successCallback, errorCallback);
};

const setDescription = (description: any) => {
  NativeModules.RNRoam.setDescription(description);
};

const toggleEvents = (geofence: any, trip: any, location: any, movingGeofence: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.toggleEvents(geofence, trip, location, movingGeofence, successCallback, errorCallback);
};

const toggleListener = (location: any, event: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.toggleListener(location, event, successCallback, errorCallback);
};

const getEventsStatus = (successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.getEventsStatus(successCallback, errorCallback);
};

const getListenerStatus = (successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.getListenerStatus(successCallback, errorCallback);
};

const subscribe = (type: any, userid: any) => {
  NativeModules.RNRoam.subscribe(type, userid);
};

const unSubscribe = (type: any, userid: any) => {
  NativeModules.RNRoam.unSubscribe(type, userid);
};

const subscribeTripStatus = (tripId: any) => {
  NativeModules.RNRoam.subscribeTripStatus(tripId);
};

const unSubscribeTripStatus = (tripId: any) => {
  NativeModules.RNRoam.unSubscribeTripStatus(tripId);
};

const disableBatteryOptimization = () => {
  NativeModules.RNRoam.disableBatteryOptimization();
};

const isBatteryOptimizationEnabled = (callback: any) => {
  NativeModules.RNRoam.isBatteryOptimizationEnabled(callback);
};

const checkLocationPermission = (callback: any) => {
  NativeModules.RNRoam.checkLocationPermission(callback);
};

const checkLocationServices = (callback: any) => {
  NativeModules.RNRoam.checkLocationServices(callback);
};

const checkBackgroundLocationPermission = (callback: any) => {
  NativeModules.RNRoam.checkBackgroundLocationPermission(callback);
};

const locationPermissionStatus = (callback: any) => {
  NativeModules.RNRoam.locationPermissionStatus(callback);
};

const requestLocationPermission = () => {
  NativeModules.RNRoam.requestLocationPermission();
};

const requestLocationServices = () => {
  NativeModules.RNRoam.requestLocationServices();
};

const requestBackgroundLocationPermission = () => {
  NativeModules.RNRoam.requestBackgroundLocationPermission();
};

const createTrip = (offline: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.createTrip(offline, successCallback, errorCallback);
};

const startTrip = (tripId: any, description: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.startTrip(tripId, description, successCallback, errorCallback);
};

const resumeTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.resumeTrip(tripId, successCallback, errorCallback);
};

const pauseTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.pauseTrip(tripId, successCallback, errorCallback);
};

const getTripSummary = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.getTripSummary(tripId, successCallback, errorCallback);
};

const stopTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.stopTrip(tripId, successCallback, errorCallback);
};

const forceStopTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.forceStopTrip(tripId, successCallback, errorCallback);
};

const deleteTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.deleteTrip(tripId, successCallback, errorCallback);
};

const syncTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.syncTrip(tripId, successCallback, errorCallback);
};

const activeTrips = (offline: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.activeTrips(offline, successCallback, errorCallback);
};

const publishOnly = (array: any, jsonMetadata: any) => {
  NativeModules.RNRoam.publishOnly(array, jsonMetadata);
};

const publishAndSave = (jsonMetadata: any) => {
  NativeModules.RNRoam.publishAndSave(jsonMetadata);
};

const stopPublishing = () => {
  NativeModules.RNRoam.stopPublishing();
};

const startTracking = (trackingMode: any) => {
  NativeModules.RNRoam.startTracking(trackingMode);
};

const startTrackingCustom = (allowBackground: any, pauseAutomatic: any, activityType: any, desiredAccuracy: any, showBackIndicator: any, distanceFilter: any, accuracyFilter: any, updateInterval: any) => {
  NativeModules.RNRoam.startTrackingCustom(allowBackground, pauseAutomatic, activityType, desiredAccuracy, showBackIndicator,  distanceFilter, accuracyFilter, updateInterval);
};

const startSelfTrackingCustom = (allowBackground: any, pauseAutomatic: any, activityType: any, desiredAccuracy: any, showBackIndicator: any, distanceFilter: any, accuracyFilter: any, updateInterval: any) => {
  NativeModules.RNRoam.startSelfTrackingCustom(allowBackground, pauseAutomatic, activityType, desiredAccuracy, showBackIndicator, distanceFilter, accuracyFilter, updateInterval);
};

const startTrackingTimeInterval = (timeInterval: any, desiredAccuracy: any) => {
  NativeModules.RNRoam.startTrackingTimeInterval(timeInterval, desiredAccuracy);
};

const startTrackingDistanceInterval = (distance: any, stationary: any, desiredAccuracy: any) => {
  NativeModules.RNRoam.startTrackingDistanceInterval(distance, stationary, desiredAccuracy);
};

const stopTracking = () => {
  NativeModules.RNRoam.stopTracking();
};

const isLocationTracking = (callback: any) => {
  NativeModules.RNRoam.isLocationTracking(callback);
};

const setForegroundNotification = (enabled: any, title: any, description: any, image: any, activity: any) => {
  NativeModules.RNRoam.setForegroundNotification(enabled, title, description, image, activity);
};

const allowMockLocation = (enabled: any) => {
  NativeModules.RNRoam.allowMockLocation(enabled);
};

const getCurrentLocationListener = (accuracy: any) => {
  NativeModules.RNRoam.getCurrentLocationListener(accuracy);
};

const getCurrentLocation = (desiredAccuracy: any, accuracy: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.getCurrentLocation(desiredAccuracy, accuracy, successCallback, errorCallback);
};

const updateCurrentLocation = (desiredAccuracy: any, accuracy: any) => {
  NativeModules.RNRoam.updateCurrentLocation(desiredAccuracy, accuracy);
};

const getCurrentLocationIos = (accuracy: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.getCurrentLocationIos(accuracy, successCallback, errorCallback);
};

const updateCurrentLocationIos = (accuracy: any) => {
  NativeModules.RNRoam.updateCurrentLocationIos(accuracy);
};

const logout = (successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.logout(successCallback, errorCallback);
};

const setTrackingInAppState = (appState: any) => {
  NativeModules.RNRoam.setTrackingInAppState(appState);
};

const offlineLocationTracking = (enabled: any) => {
  NativeModules.RNRoam.offlineLocationTracking(enabled);
};

const startSelfTracking = (trackingMode: any) => {
  NativeModules.RNRoam.startSelfTracking(trackingMode);
};

const startSelfTrackingTimeInterval = (timeInterval: any, desiredAccuracy: any) => {
  NativeModules.RNRoam.startSelfTrackingTimeInterval(timeInterval, desiredAccuracy);
};

const startSelfTrackingDistanceInterval = (distance: any, stationary: any, desiredAccuracy: any) => {
  NativeModules.RNRoam.startSelfTrackingDistanceInterval(distance, stationary, desiredAccuracy);
};

const stopSelfTracking = () => {
  NativeModules.RNRoam.stopSelfTracking();
};

const enableAccuracyEngine = () => {
  NativeModules.RNRoam.enableAccuracyEngine();
};

const disableAccuracyEngine = () => {
  NativeModules.RNRoam.disableAccuracyEngine();
};

const startListener = (event: string, callback: (...args: any[]) => any) => (
  eventEmitter.addListener(event, callback)
);

const stopListener = (event: string, callback: (...args: any[]) => any) => {
  if (callback) {
    eventEmitter.removeListener(event, callback);
  } else {
    eventEmitter.removeAllListeners(event);
  }
};

const Roam = {
TrackingMode,
DesiredAccuracy,
AppState,
DesiredAccuracyIOS,
ActivityType,
SubscribeListener,
Publish,
createUser,
getUser,
setDescription,
toggleEvents,
toggleListener,
getEventsStatus,
getListenerStatus,
subscribe,
unSubscribe,
subscribeTripStatus,
unSubscribeTripStatus,
disableBatteryOptimization,
isBatteryOptimizationEnabled,
checkLocationPermission,
checkLocationServices,
checkBackgroundLocationPermission,
requestLocationPermission,
requestLocationServices,
requestBackgroundLocationPermission,
locationPermissionStatus,
createTrip,
startTrip,
resumeTrip,
pauseTrip,
stopTrip,
forceStopTrip,
deleteTrip,
syncTrip,
activeTrips,
publishOnly,
publishAndSave,
stopPublishing,
startTracking,
startTrackingCustom,
startSelfTrackingCustom,
startTrackingTimeInterval,
startTrackingDistanceInterval,
stopTracking,
isLocationTracking,
setForegroundNotification,
allowMockLocation,
getCurrentLocationListener,
getCurrentLocation,
updateCurrentLocation,
getCurrentLocationIos,
updateCurrentLocationIos,
logout,
setTrackingInAppState,
offlineLocationTracking,
startSelfTracking,
startSelfTrackingTimeInterval,
startSelfTrackingDistanceInterval,
stopSelfTracking,
enableAccuracyEngine,
disableAccuracyEngine,
startListener,
stopListener,
getTripSummary,
};

export default Roam;
