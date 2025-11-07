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

const DesiredAccuracy = {
  HIGH: "HIGH",
  MEDIUM: "MEDIUM",
  LOW: "LOW",
};

const NetworkState = {
  BOTH: "BOTH",
  ONLINE: "ONLINE",
  OFFLINE: "OFFLINE",
};

const AppState = {
  ALWAYS_ON: "ALWAYS_ON",
  FOREGROUND: "FOREGROUND",
  BACKGROUND: "BACKGROUND",
};

const Source = {
  ALL: "ALL",
  LAST_KNOWN: "LAST_KNOWN",
  GPS: "GPS",
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

const createUser = (description, successCallback, errorCallback) => {
  NativeModules.RNRoam.createUser(
    description,
    null,
    successCallback,
    errorCallback,
  );
};

const getUser = (userid, successCallback, errorCallback) => {
  NativeModules.RNRoam.getUser(userid, successCallback, errorCallback);
};

const setDescription = (description) => {
  if (Platform.OS === "android") {
    NativeModules.RNRoam.setDescription(description);
  } else {
    NativeModules.RNRoam.setDescription(description, null);
  }
};

const toggleEvents = (
  geofence,
  trip,
  location,
  movingGeofence,
  successCallback,
  errorCallback,
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

const toggleListener = (location, event, successCallback, errorCallback) => {
  NativeModules.RNRoam.toggleListener(
    location,
    event,
    successCallback,
    errorCallback,
  );
};

const getEventsStatus = (successCallback, errorCallback) => {
  NativeModules.RNRoam.getEventsStatus(successCallback, errorCallback);
};

const getListenerStatus = (successCallback, errorCallback) => {
  NativeModules.RNRoam.getListenerStatus(successCallback, errorCallback);
};

const subscribe = (type, userid) => {
  NativeModules.RNRoam.subscribe(type, userid);
};

const unSubscribe = (type, userid) => {
  NativeModules.RNRoam.unSubscribe(type, userid);
};

const disableBatteryOptimization = () => {
  NativeModules.RNRoam.disableBatteryOptimization();
};

const isBatteryOptimizationEnabled = (callback) => {
  NativeModules.RNRoam.isBatteryOptimizationEnabled(callback);
};

const checkLocationPermission = (callback) => {
  NativeModules.RNRoam.checkLocationPermission(callback);
};

const checkLocationServices = (callback) => {
  NativeModules.RNRoam.checkLocationServices(callback);
};

const checkBackgroundLocationPermission = (callback) => {
  NativeModules.RNRoam.checkBackgroundLocationPermission(callback);
};

const locationPermissionStatus = (callback) => {
  NativeModules.RNRoam.locationPermissionStatus(callback);
};

const requestLocationPermission = () => {
  NativeModules.RNRoam.requestLocationPermission();
};

const requestPhoneStatePermission = () => {
  NativeModules.RNRoam.requestPhoneStatePermission();
};

const requestLocationServices = () => {
  NativeModules.RNRoam.requestLocationServices();
};

const requestBackgroundLocationPermission = () => {
  NativeModules.RNRoam.requestBackgroundLocationPermission();
};

// -------- Trips V2 ----------

class RoamTrip {
  constructor(metadata, description, name, stops, isLocal, tripId, userId) {
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
  constructor(
    id,
    metadata,
    description,
    name,
    address,
    geometryRadius,
    geometry,
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
  constructor(
    desiredAccuracy,
    updateInterval,
    distanceFilter,
    stopDuration,
    activityType,
    desiredAccuracyIOS,
    allowBackgroundLocationUpdates,
    pausesLocationUpdatesAutomatically,
    showsBackgroundLocationIndicator,
    accuracyFilter,
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

function roamCustomTrackingOptionsToMap(customOptions) {
  if (customOptions === null) {
    return null;
  }
  const customMap = {
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

function roamTripStopsToMap(stop) {
  if (stop === null) {
    return null;
  }
  const stopsList = [];
  for (let i = 0; i < stop.length; i++) {
    const stopMap = {
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

function roamTripToMap(roamTrip) {
  if (roamTrip === null) {
    return null;
  }

  const roamTripMap = {
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

const createTrip = (roamTrip, successCallback, errorCallback) => {
  NativeModules.RNRoam.createTrip(
    roamTripToMap(roamTrip),
    successCallback,
    errorCallback,
  );
};

const startQuickTrip = (
  roamTrip,
  trackingMode,
  customTrackingOption,
  successCallback,
  errorCallback,
) => {
  NativeModules.RNRoam.startQuickTrip(
    roamTripToMap(roamTrip),
    trackingMode,
    roamCustomTrackingOptionsToMap(customTrackingOption),
    successCallback,
    errorCallback,
  );
};

const startTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.startTrip(tripId, successCallback, errorCallback);
};

const updateTrip = (roamTrip, successCallback, errorCallback) => {
  NativeModules.RNRoam.updateTrip(
    roamTripToMap(roamTrip),
    successCallback,
    errorCallback,
  );
};

const endTrip = (tripId, forceStopTracking, successCallback, errorCallback) => {
  NativeModules.RNRoam.endTrip(
    tripId,
    forceStopTracking,
    successCallback,
    errorCallback,
  );
};

const pauseTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.pauseTrip(tripId, successCallback, errorCallback);
};

const resumeTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.resumeTrip(tripId, successCallback, errorCallback);
};

const syncTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.syncTrip(tripId, successCallback, errorCallback);
};

const getTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.getTrip(tripId, successCallback, errorCallback);
};

const getActiveTrips = (isLocal, successCallback, errorCallback) => {
  NativeModules.RNRoam.getActiveTrips(isLocal, successCallback, errorCallback);
};

const getTripSummary = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.getTripSummary(tripId, successCallback, errorCallback);
};

const subscribeTrip = (tripId) => {
  NativeModules.RNRoam.subscribeTripStatus(tripId);
};

const unSubscribeTrip = (tripId) => {
  NativeModules.RNRoam.unSubscribeTripStatus(tripId);
};

const deleteTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.deleteTrip(tripId, successCallback, errorCallback);
};

// -------- END ------------

const publishOnly = (array, jsonMetadata) => {
  NativeModules.RNRoam.publishOnly(array, jsonMetadata);
};

const publishAndSave = (jsonMetadata) => {
  NativeModules.RNRoam.publishAndSave(jsonMetadata);
};

const batchProcess = (enable, syncHour) => {
  NativeModules.RNRoam.batchProcess(enable, syncHour);
};

const stopPublishing = () => {
  NativeModules.RNRoam.stopPublishing();
};

const createGeofence = (geofence) => {
  NativeModules.RNRoam.createGeofence(geofence);
};

const startTracking = (trackingMode) => {
  NativeModules.RNRoam.startTracking(trackingMode);
};

const startTrackingCustom = (
  allowBackground,
  pauseAutomatic,
  activityType,
  desiredAccuracy,
  showBackIndicator,
  distanceFilter,
  accuracyFilter,
  updateInterval,
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
  allowBackground,
  pauseAutomatic,
  activityType,
  desiredAccuracy,
  showBackIndicator,
  distanceFilter,
  accuracyFilter,
  updateInterval,
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

const startTrackingTimeInterval = (timeInterval, desiredAccuracy) => {
  NativeModules.RNRoam.startTrackingTimeInterval(timeInterval, desiredAccuracy);
};

const startTrackingDistanceInterval = (
  distance,
  stationary,
  desiredAccuracy,
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

const isLocationTracking = (callback) => {
  NativeModules.RNRoam.isLocationTracking(callback);
};

const setForegroundNotification = (
  enabled,
  title,
  description,
  image,
  activity,
  roamService,
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

const allowMockLocation = (enabled) => {
  NativeModules.RNRoam.allowMockLocation(enabled);
};

const getCurrentLocationListener = (accuracy) => {
  NativeModules.RNRoam.getCurrentLocationListener(accuracy);
};

const getCurrentLocation = (
  desiredAccuracy,
  accuracy,
  successCallback,
  errorCallback,
) => {
  NativeModules.RNRoam.getCurrentLocation(
    desiredAccuracy,
    accuracy,
    successCallback,
    errorCallback,
  );
};

const updateCurrentLocation = (desiredAccuracy, accuracy) => {
  NativeModules.RNRoam.updateCurrentLocation(desiredAccuracy, accuracy);
};

const getCurrentLocationIos = (accuracy, successCallback, errorCallback) => {
  NativeModules.RNRoam.getCurrentLocationIos(
    accuracy,
    successCallback,
    errorCallback,
  );
};

const updateCurrentLocationIos = (accuracy) => {
  NativeModules.RNRoam.updateCurrentLocationIos(accuracy);
};

const updateLocationWhenStationary = (interval) => {
  NativeModules.RNRoam.updateLocationWhenStationary(interval);
};

const logout = (successCallback, errorCallback) => {
  NativeModules.RNRoam.logout(successCallback, errorCallback);
};

const setTrackingInAppState = (appState) => {
  NativeModules.RNRoam.setTrackingInAppState(appState);
};

const offlineLocationTracking = (enabled) => {
  NativeModules.RNRoam.offlineLocationTracking(enabled);
};

const startSelfTracking = (trackingMode) => {
  NativeModules.RNRoam.startSelfTracking(trackingMode);
};

const startSelfTrackingTimeInterval = (timeInterval, desiredAccuracy) => {
  NativeModules.RNRoam.startSelfTrackingTimeInterval(
    timeInterval,
    desiredAccuracy,
  );
};

const startSelfTrackingDistanceInterval = (
  distance,
  stationary,
  desiredAccuracy,
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

const enableAccuracyEngine = (accuracy) => {
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

const startListener = (event, callback) =>
  eventEmitter.addListener(event, callback);

const stopListener = (event) => {
  eventEmitter.removeAllListeners(event);
};

const setBatchReceiverConfig = (
  networkState,
  batchCount,
  batchWindow,
  successCallback,
  errorCallback,
) => {
  NativeModules.RNRoam.setBatchReceiverConfig(
    networkState,
    batchCount,
    batchWindow,
    successCallback,
    errorCallback,
  );
};

const getBatchReceiverConfig = (successCallback, errorCallback) => {
  NativeModules.RNRoam.getBatchReceiverConfig(successCallback, errorCallback);
};

const resetBatchReceiverConfig = (successCallback, errorCallback) => {
  NativeModules.RNRoam.resetBatchReceiverConfig(successCallback, errorCallback);
};

const setTrackingConfig = (
  accuracy,
  timeout,
  source,
  discardLocation,
  successCallback,
  errorCallback,
) => {
  if (Platform.OS === "android") {
    NativeModules.RNRoam.setTrackingConfig(
      accuracy,
      timeout,
      source,
      discardLocation,
      successCallback,
      errorCallback,
    );
  } else {
    NativeModules.RNRoam.setTrackingConfig(
      accuracy,
      timeout,
      discardLocation,
      successCallback,
      errorCallback,
    );
  }
};

const getTrackingConfig = (successCallback, errorCallback) => {
  NativeModules.RNRoam.getTrackingConfig(successCallback, errorCallback);
};

const resetTrackingConfig = (successCallback, errorCallback) => {
  NativeModules.RNRoam.resetTrackingConfig(successCallback, errorCallback);
};

const checkActivityPermission = (callback) => {
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
  requestPhoneStatePermission,
  requestLocationServices,
  requestBackgroundLocationPermission,
  locationPermissionStatus,
  publishOnly,
  publishAndSave,
  stopPublishing,
  batchProcess,
  createGeofence,
  // getAllGeofences,
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
