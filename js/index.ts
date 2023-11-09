import { NativeEventEmitter, NativeModules, Platform } from "react-native";

if (!NativeModules.RNRoam) {
  throw new Error("NativeModules.RNRoam is undefined");
}

const eventEmitter = new NativeEventEmitter(NativeModules.RNRoam);

const TrackingMode = {
  ACTIVE: "ACTIVE",
  BALANCED: "BALANCED",
  PASSIVE: "PASSIVE",
  CUSTOM: "CUSTOM",
};

const NetworkState = {
  BOTH: "BOTH",
  ONLINE: "ONLINE",
  OFFLINE: "OFFLINE",
};

const Source = {
  ALL: "ALL",
  LAST_KNOWN: "LAST_KNOWN",
  GPS: "GPS",
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

const createUser = (
  description: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.createUser(
    description,
    null,
    successCallback,
    errorCallback,
  );
};

const getUser = (userid: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.getUser(userid, successCallback, errorCallback);
};

const setDescription = (description: any) => {
  if (Platform.OS === "android") {
    NativeModules.RNRoam.setDescription(description);
  } else {
    NativeModules.RNRoam.setDescription(description, null);
  }
};

const toggleEvents = (
  geofence: any,
  trip: any,
  location: any,
  movingGeofence: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.toggleEvents(
    geofence,
    trip,
    location,
    movingGeofence,
    successCallback,
    errorCallback,
  );
};

const toggleListener = (
  location: any,
  event: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.toggleListener(
    location,
    event,
    successCallback,
    errorCallback,
  );
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

// -------- Trips V2 ----------

class RoamTrip {
  metadata: any;
  description: any;
  name: any;
  stops: any;
  isLocal: any;
  tripId: any;
  userId: any;
  constructor(
    metadata: any,
    description: any,
    name: any,
    stops: any,
    isLocal: any,
    tripId: any,
    userId: any,
  ) {
    this.metadata = metadata;
    this.description = description;
    this.name = name;
    this.stops = stops;
    this.isLocal = isLocal;
    this.tripId = tripId;
    this.userId = userId;
  }
}

class RoamTripStop {
  id: any;
  metadata: any;
  description: any;
  name: any;
  address: any;
  geometryRadius: any;
  geometry: any;
  constructor(
    id: any,
    metadata: any,
    description: any,
    name: any,
    address: any,
    geometryRadius: any,
    geometry: any,
  ) {
    this.id = id;
    this.metadata = metadata;
    this.description = description;
    this.name = name;
    this.address = address;
    this.geometryRadius = geometryRadius;
    this.geometry = geometry;
  }
}

class RoamCustomTrackingOptions {
  desiredAccuracy: any;
  updateInterval: any;
  distanceFilter: any;
  stopDuration: any;
  activityType: any;
  desiredAccuracyIOS: any;
  allowBackgroundLocationUpdates: any;
  pausesLocationUpdatesAutomatically: any;
  showsBackgroundLocationIndicator: any;
  accuracyFilter: any;
  constructor(
    desiredAccuracy: any,
    updateInterval: any,
    distanceFilter: any,
    stopDuration: any,
    activityType: any,
    desiredAccuracyIOS: any,
    allowBackgroundLocationUpdates: any,
    pausesLocationUpdatesAutomatically: any,
    showsBackgroundLocationIndicator: any,
    accuracyFilter: any,
  ) {
    this.desiredAccuracy = desiredAccuracy;
    this.updateInterval = updateInterval;
    this.distanceFilter = distanceFilter;
    this.stopDuration = stopDuration;
    this.activityType = activityType;
    this.desiredAccuracyIOS = desiredAccuracyIOS;
    this.allowBackgroundLocationUpdates = allowBackgroundLocationUpdates;
    this.pausesLocationUpdatesAutomatically =
      pausesLocationUpdatesAutomatically;
    this.showsBackgroundLocationIndicator = showsBackgroundLocationIndicator;
    this.accuracyFilter = accuracyFilter;
  }
}

function roamCustomTrackingOptionsToMap(customOptions: any) {
  if (customOptions === null) {
    return null;
  }
  var customMap = {
    desiredAccuracy: customOptions.desiredAccuracy,
    updateInterval: customOptions.updateInterval,
    distanceFilter: customOptions.distanceFilter,
    stopDuration: customOptions.stopDuration,
    activityType: customOptions.activityType,
    desiredAccuracyIOS: customOptions.desiredAccuracyIOS,
    allowBackgroundLocationUpdates:
      customOptions.allowBackgroundLocationUpdates,
    pausesLocationUpdatesAutomatically:
      customOptions.pausesLocationUpdatesAutomatically,
    showsBackgroundLocationIndicator:
      customOptions.showsBackgroundLocationIndicator,
    accuracyFilter: customOptions.accuracyFilter,
  };
  return customMap;
}

function roamTripStopsToMap(stop: any) {
  if (stop === null) {
    return null;
  }
  var stopsList = [];
  for (let i = 0; i < stop.length; i++) {
    var stopMap = {
      RoamTripStop: stop[i].id,
      stopName: stop[i].name,
      stopDescription: stop[i].description,
      address: stop[i].address,
      geometryRadius: stop[i].geometryRadius,
      geometryCoordinates: stop[i].geometry,
      metadata: stop[i].metadata,
    };
    stopsList.push(stopMap);
  }

  return stopsList;
}

function roamTripToMap(roamTrip: any) {
  if (roamTrip === null) {
    return null;
  }

  var roamTripMap = {
    tripId: roamTrip.tripId,
    tripDescription: roamTrip.description,
    tripName: roamTrip.name,
    metadata: roamTrip.metadata,
    isLocal: roamTrip.isLocal,
    stops: roamTripStopsToMap(roamTrip.stops),
    userId: roamTrip.userId,
  };
  return roamTripMap;
}

const createTrip = (
  roamTrip: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.createTrip(
    roamTripToMap(roamTrip),
    successCallback,
    errorCallback,
  );
};

const startQuickTrip = (
  roamTrip: any,
  trackingMode: any,
  customTrackingOption: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.startQuickTrip(
    roamTripToMap(roamTrip),
    trackingMode,
    roamCustomTrackingOptionsToMap(customTrackingOption),
    successCallback,
    errorCallback,
  );
};

const startTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.startTrip(tripId, successCallback, errorCallback);
};

const updateTrip = (
  roamTrip: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.updateTrip(
    roamTripToMap(roamTrip),
    successCallback,
    errorCallback,
  );
};

const endTrip = (
  tripId: any,
  forceStopTracking: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.endTrip(
    tripId,
    forceStopTracking,
    successCallback,
    errorCallback,
  );
};

const pauseTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.pauseTrip(tripId, successCallback, errorCallback);
};

const resumeTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.resumeTrip(tripId, successCallback, errorCallback);
};

const syncTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.syncTrip(tripId, successCallback, errorCallback);
};

const getTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.getTrip(tripId, successCallback, errorCallback);
};

const getActiveTrips = (
  isLocal: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.getActiveTrips(isLocal, successCallback, errorCallback);
};

const getTripSummary = (
  tripId: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.getTripSummary(tripId, successCallback, errorCallback);
};

const subscribeTrip = (tripId: any) => {
  NativeModules.RNRoam.subscribeTripStatus(tripId);
};

const unSubscribeTrip = (tripId: any) => {
  NativeModules.RNRoam.unSubscribeTripStatus(tripId);
};

const deleteTrip = (tripId: any, successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.deleteTrip(tripId, successCallback, errorCallback);
};

// -------- END ------------

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

const startTrackingCustom = (
  allowBackground: any,
  pauseAutomatic: any,
  activityType: any,
  desiredAccuracy: any,
  showBackIndicator: any,
  distanceFilter: any,
  accuracyFilter: any,
  updateInterval: any,
) => {
  NativeModules.RNRoam.startTrackingCustom(
    allowBackground,
    pauseAutomatic,
    activityType,
    desiredAccuracy,
    showBackIndicator,
    distanceFilter,
    accuracyFilter,
    updateInterval,
  );
};

const startSelfTrackingCustom = (
  allowBackground: any,
  pauseAutomatic: any,
  activityType: any,
  desiredAccuracy: any,
  showBackIndicator: any,
  distanceFilter: any,
  accuracyFilter: any,
  updateInterval: any,
) => {
  NativeModules.RNRoam.startSelfTrackingCustom(
    allowBackground,
    pauseAutomatic,
    activityType,
    desiredAccuracy,
    showBackIndicator,
    distanceFilter,
    accuracyFilter,
    updateInterval,
  );
};

const startTrackingTimeInterval = (timeInterval: any, desiredAccuracy: any) => {
  NativeModules.RNRoam.startTrackingTimeInterval(timeInterval, desiredAccuracy);
};

const startTrackingDistanceInterval = (
  distance: any,
  stationary: any,
  desiredAccuracy: any,
) => {
  NativeModules.RNRoam.startTrackingDistanceInterval(
    distance,
    stationary,
    desiredAccuracy,
  );
};

const stopTracking = () => {
  NativeModules.RNRoam.stopTracking();
};

const isLocationTracking = (callback: any) => {
  NativeModules.RNRoam.isLocationTracking(callback);
};

const setForegroundNotification = (
  enabled: any,
  title: any,
  description: any,
  image: any,
  activity: any,
  roamService: any,
) => {
  NativeModules.RNRoam.setForegroundNotification(
    enabled,
    title,
    description,
    image,
    activity,
    roamService,
  );
};

const allowMockLocation = (enabled: any) => {
  NativeModules.RNRoam.allowMockLocation(enabled);
};

const getCurrentLocationListener = (accuracy: any) => {
  NativeModules.RNRoam.getCurrentLocationListener(accuracy);
};

const getCurrentLocation = (
  desiredAccuracy: any,
  accuracy: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.getCurrentLocation(
    desiredAccuracy,
    accuracy,
    successCallback,
    errorCallback,
  );
};

const updateCurrentLocation = (desiredAccuracy: any, accuracy: any) => {
  NativeModules.RNRoam.updateCurrentLocation(desiredAccuracy, accuracy);
};

const getCurrentLocationIos = (
  accuracy: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.getCurrentLocationIos(
    accuracy,
    successCallback,
    errorCallback,
  );
};

const updateCurrentLocationIos = (accuracy: any) => {
  NativeModules.RNRoam.updateCurrentLocationIos(accuracy);
};

const updateLocationWhenStationary = (interval: any) => {
  NativeModules.RNRoam.updateLocationWhenStationary(interval);
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

const startSelfTrackingTimeInterval = (
  timeInterval: any,
  desiredAccuracy: any,
) => {
  NativeModules.RNRoam.startSelfTrackingTimeInterval(
    timeInterval,
    desiredAccuracy,
  );
};

const startSelfTrackingDistanceInterval = (
  distance: any,
  stationary: any,
  desiredAccuracy: any,
) => {
  NativeModules.RNRoam.startSelfTrackingDistanceInterval(
    distance,
    stationary,
    desiredAccuracy,
  );
};

const stopSelfTracking = () => {
  NativeModules.RNRoam.stopSelfTracking();
};

const enableAccuracyEngine = (accuracy?: any) => {
  if (Platform.OS === "ios") {
    NativeModules.RNRoam.enableAccuracyEngine();
  } else {
    if (accuracy === null || accuracy === undefined) {
      NativeModules.RNRoam.enableAccuracyEngine(50);
    } else {
      NativeModules.RNRoam.enableAccuracyEngine(accuracy);
    }
  }
};

const disableAccuracyEngine = () => {
  NativeModules.RNRoam.disableAccuracyEngine();
};

const startListener = (event: string, callback: (...args: any[]) => any) =>
  eventEmitter.addListener(event, callback);

const stopListener = (event: string) => {
  eventEmitter.removeAllListeners(event);
};

const setBatchReceiverConfig = (
  networkState: any,
  batchCount: any,
  batchWindow: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.setBatchReceiverConfig(
    networkState,
    batchCount,
    batchWindow,
    successCallback,
    errorCallback,
  );
};

const getBatchReceiverConfig = (successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.getBatchReceiverConfig(successCallback, errorCallback);
};

const resetBatchReceiverConfig = (successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.resetBatchReceiverConfig(successCallback, errorCallback);
};

const setTrackingConfig = (
  accuracy: any,
  timeout: any,
  source: any,
  discardLocation: any,
  successCallback: any,
  errorCallback: any,
) => {
  NativeModules.RNRoam.setTrackingConfig(
    accuracy,
    timeout,
    source,
    discardLocation,
    successCallback,
    errorCallback,
  );
};

const getTrackingConfig = (successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.getTrackingConfig(successCallback, errorCallback);
};

const resetTrackingConfig = (successCallback: any, errorCallback: any) => {
  NativeModules.RNRoam.resetTrackingConfig(successCallback, errorCallback);
};

const checkActivityPermission = (callback: any) => {
  NativeModules.RNRoam.checkActivityPermission(callback);
};

const requestActivityPermission = () => {
  NativeModules.RNRoam.requestActivityPermission();
};

const Roam = {
  TrackingMode,
  DesiredAccuracy,
  AppState,
  NetworkState,
  DesiredAccuracyIOS,
  ActivityType,
  SubscribeListener,
  Publish,
  Source,
  createUser,
  getUser,
  setDescription,
  toggleEvents,
  toggleListener,
  getEventsStatus,
  getListenerStatus,
  subscribe,
  unSubscribe,
  disableBatteryOptimization,
  isBatteryOptimizationEnabled,
  checkLocationPermission,
  checkLocationServices,
  checkBackgroundLocationPermission,
  requestLocationPermission,
  requestLocationServices,
  requestBackgroundLocationPermission,
  locationPermissionStatus,
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
  updateLocationWhenStationary,
  setBatchReceiverConfig,
  getBatchReceiverConfig,
  resetBatchReceiverConfig,
  setTrackingConfig,
  getTrackingConfig,
  resetTrackingConfig,
  createTrip,
  startQuickTrip,
  startTrip,
  updateTrip,
  endTrip,
  pauseTrip,
  resumeTrip,
  syncTrip,
  getTrip,
  getActiveTrips,
  getTripSummary,
  subscribeTrip,
  unSubscribeTrip,
  deleteTrip,
  RoamTrip,
  RoamTripStop,
  RoamCustomTrackingOptions,
  checkActivityPermission,
  requestActivityPermission,
};

export default Roam;
