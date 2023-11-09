import { NativeEventEmitter, NativeModules } from 'react-native'
if (!NativeModules.RNRoam) {
  throw new Error('NativeModules.RNRoam is undefined')
}
const eventEmitter = new NativeEventEmitter(NativeModules.RNRoam)
const TrackingMode = {
  ACTIVE: 'ACTIVE',
  BALANCED: 'BALANCED',
  PASSIVE: 'PASSIVE'
}
const DesiredAccuracy = {
  HIGH: 'HIGH',
  MEDIUM: 'MEDIUM',
  LOW: 'LOW'
}
const AppState = {
  ALWAYS_ON: 'ALWAYS_ON',
  FOREGROUND: 'FOREGROUND',
  BACKGROUND: 'BACKGROUND'
}
const DesiredAccuracyIOS = {
  BESTFORNAVIGATION: 'BESTFORNAVIGATION',
  BEST: 'BEST',
  NEAREST_TEN_METERS: 'NEAREST_TEN_METERS',
  HUNDRED_METERS: 'HUNDRED_METERS',
  KILO_METERS: 'KILO_METERS',
  THREE_KILOMETERS: 'THREE_KILOMETERS'
}
const ActivityType = {
  OTHER: 'OTHER',
  AUTO_NAVIGATION: 'AUTO_NAVIGATION',
  OTHER_NAVIGATION: 'OTHER_NAVIGATION',
  FITNESS: 'FITNESS'
}
const SubscribeListener = {
  EVENTS: 'EVENTS',
  LOCATION: 'LOCATION',
  BOTH: 'BOTH'
}
const Publish = {
  APP_ID: 'APP_ID',
  USER_ID: 'USER_ID',
  GEOFENCE_EVENTS: 'GEOFENCE_EVENTS',
  LOCATION_EVENTS: 'LOCATION_EVENTS',
  NEARBY_EVENTS: 'NEARBY_EVENTS',
  TRIPS_EVENTS: 'TRIPS_EVENTS',
  LOCATION_LISTENER: 'LOCATION_LISTENER',
  EVENT_LISTENER: 'EVENT_LISTENER',
  ALTITUDE: 'ALTITUDE',
  COURSE: 'COURSE',
  SPEED: 'SPEED',
  VERTICAL_ACCURACY: 'VERTICAL_ACCURACY',
  HORIZONTAL_ACCURACY: 'HORIZONTAL_ACCURACY',
  APP_CONTEXT: 'APP_CONTEXT',
  ALLOW_MOCKED: 'ALLOW_MOCKED',
  BATTERY_REMAINING: 'BATTERY_REMAINING',
  BATTERY_SAVER: 'BATTERY_SAVER',
  BATTERY_STATUS: 'BATTERY_STATUS',
  ACTIVITY: 'ACTIVITY',
  AIRPLANE_MODE: 'AIRPLANE_MODE',
  DEVICE_MANUFACTURE: 'DEVICE_MANUFACTURE',
  DEVICE_MODEL: 'DEVICE_MODEL',
  TRACKING_MODE: 'TRACKING_MODE',
  LOCATIONPERMISSION: 'LOCATIONPERMISSION',
  NETWORK_STATUS: 'NETWORK_STATUS',
  GPS_STATUS: 'GPS_STATUS',
  OS_VERSION: 'OS_VERSION',
  RECORDERD_AT: 'RECORDERD_AT',
  TZ_OFFSET: 'TZ_OFFSET'
}
const createUser = (description, successCallback, errorCallback) => {
  NativeModules.RNRoam.createUser(description, successCallback, errorCallback)
}
const getUser = (userid, successCallback, errorCallback) => {
  NativeModules.RNRoam.getUser(userid, successCallback, errorCallback)
}
const setDescription = (description) => {
  NativeModules.RNRoam.setDescription(description)
}
const toggleEvents = (
  geofence,
  trip,
  location,
  movingGeofence,
  successCallback,
  errorCallback
) => {
  NativeModules.RNRoam.toggleEvents(
    geofence,
    trip,
    location,
    movingGeofence,
    successCallback,
    errorCallback
  )
}
const toggleListener = (location, event, successCallback, errorCallback) => {
  NativeModules.RNRoam.toggleListener(
    location,
    event,
    successCallback,
    errorCallback
  )
}
const getEventsStatus = (successCallback, errorCallback) => {
  NativeModules.RNRoam.getEventsStatus(successCallback, errorCallback)
}
const getListenerStatus = (successCallback, errorCallback) => {
  NativeModules.RNRoam.getListenerStatus(successCallback, errorCallback)
}
const subscribe = (type, userid) => {
  NativeModules.RNRoam.subscribe(type, userid)
}
const unSubscribe = (type, userid) => {
  NativeModules.RNRoam.unSubscribe(type, userid)
}
const subscribeTripStatus = (tripId) => {
  NativeModules.RNRoam.subscribeTripStatus(tripId)
}
const unSubscribeTripStatus = (tripId) => {
  NativeModules.RNRoam.unSubscribeTripStatus(tripId)
}
const disableBatteryOptimization = () => {
  NativeModules.RNRoam.disableBatteryOptimization()
}
const isBatteryOptimizationEnabled = (callback) => {
  NativeModules.RNRoam.isBatteryOptimizationEnabled(callback)
}
const checkLocationPermission = (callback) => {
  NativeModules.RNRoam.checkLocationPermission(callback)
}
const checkLocationServices = (callback) => {
  NativeModules.RNRoam.checkLocationServices(callback)
}
const checkBackgroundLocationPermission = (callback) => {
  NativeModules.RNRoam.checkBackgroundLocationPermission(callback)
}
const locationPermissionStatus = (callback) => {
  NativeModules.RNRoam.locationPermissionStatus(callback)
}
const requestLocationPermission = () => {
  NativeModules.RNRoam.requestLocationPermission()
}
const requestLocationServices = () => {
  NativeModules.RNRoam.requestLocationServices()
}
const requestBackgroundLocationPermission = () => {
  NativeModules.RNRoam.requestBackgroundLocationPermission()
}
const createTrip = (offline, successCallback, errorCallback) => {
  NativeModules.RNRoam.createTrip(offline, successCallback, errorCallback)
}
const startTrip = (tripId, description, successCallback, errorCallback) => {
  NativeModules.RNRoam.startTrip(
    tripId,
    description,
    successCallback,
    errorCallback
  )
}
const resumeTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.resumeTrip(tripId, successCallback, errorCallback)
}
const pauseTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.pauseTrip(tripId, successCallback, errorCallback)
}
const getTripSummary = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.getTripSummary(tripId, successCallback, errorCallback)
}
const stopTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.stopTrip(tripId, successCallback, errorCallback)
}
const forceStopTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.forceStopTrip(tripId, successCallback, errorCallback)
}
const deleteTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.deleteTrip(tripId, successCallback, errorCallback)
}
const syncTrip = (tripId, successCallback, errorCallback) => {
  NativeModules.RNRoam.syncTrip(tripId, successCallback, errorCallback)
}
const activeTrips = (offline, successCallback, errorCallback) => {
  NativeModules.RNRoam.activeTrips(offline, successCallback, errorCallback)
}
const publishOnly = (array, jsonMetadata) => {
  NativeModules.RNRoam.publishOnly(array, jsonMetadata)
}
const publishAndSave = (jsonMetadata) => {
  NativeModules.RNRoam.publishAndSave(jsonMetadata)
}
const stopPublishing = () => {
  NativeModules.RNRoam.stopPublishing()
}
const startTracking = (trackingMode) => {
  NativeModules.RNRoam.startTracking(trackingMode)
}
const startTrackingCustom = (
  allowBackground,
  pauseAutomatic,
  activityType,
  desiredAccuracy,
  showBackIndicator,
  distanceFilter,
  accuracyFilter,
  updateInterval
) => {
  NativeModules.RNRoam.startTrackingCustom(
    allowBackground,
    pauseAutomatic,
    activityType,
    desiredAccuracy,
    showBackIndicator,
    distanceFilter,
    accuracyFilter,
    updateInterval
  )
}
const startSelfTrackingCustom = (
  allowBackground,
  pauseAutomatic,
  activityType,
  desiredAccuracy,
  showBackIndicator,
  distanceFilter,
  accuracyFilter,
  updateInterval
) => {
  NativeModules.RNRoam.startSelfTrackingCustom(
    allowBackground,
    pauseAutomatic,
    activityType,
    desiredAccuracy,
    showBackIndicator,
    distanceFilter,
    accuracyFilter,
    updateInterval
  )
}
const startTrackingTimeInterval = (timeInterval, desiredAccuracy) => {
  NativeModules.RNRoam.startTrackingTimeInterval(timeInterval, desiredAccuracy)
}
const startTrackingDistanceInterval = (
  distance,
  stationary,
  desiredAccuracy
) => {
  NativeModules.RNRoam.startTrackingDistanceInterval(
    distance,
    stationary,
    desiredAccuracy
  )
}
const stopTracking = () => {
  NativeModules.RNRoam.stopTracking()
}
const isLocationTracking = (callback) => {
  NativeModules.RNRoam.isLocationTracking(callback)
}
const allowMockLocation = (enabled) => {
  NativeModules.RNRoam.allowMockLocation(enabled)
}
const getCurrentLocationListener = (accuracy) => {
  NativeModules.RNRoam.getCurrentLocationListener(accuracy)
}
const getCurrentLocation = (
  desiredAccuracy,
  accuracy,
  successCallback,
  errorCallback
) => {
  NativeModules.RNRoam.getCurrentLocation(
    desiredAccuracy,
    accuracy,
    successCallback,
    errorCallback
  )
}
const updateCurrentLocation = (desiredAccuracy, accuracy) => {
  NativeModules.RNRoam.updateCurrentLocation(desiredAccuracy, accuracy)
}
const getCurrentLocationIos = (accuracy, successCallback, errorCallback) => {
  NativeModules.RNRoam.getCurrentLocationIos(
    accuracy,
    successCallback,
    errorCallback
  )
}
const updateCurrentLocationIos = (accuracy) => {
  NativeModules.RNRoam.updateCurrentLocationIos(accuracy)
}
const logout = (successCallback, errorCallback) => {
  NativeModules.RNRoam.logout(successCallback, errorCallback)
}
const setTrackingInAppState = (appState) => {
  NativeModules.RNRoam.setTrackingInAppState(appState)
}
const offlineLocationTracking = (enabled) => {
  NativeModules.RNRoam.offlineLocationTracking(enabled)
}
const startSelfTracking = (trackingMode) => {
  NativeModules.RNRoam.startSelfTracking(trackingMode)
}
const startSelfTrackingTimeInterval = (timeInterval, desiredAccuracy) => {
  NativeModules.RNRoam.startSelfTrackingTimeInterval(
    timeInterval,
    desiredAccuracy
  )
}
const startSelfTrackingDistanceInterval = (
  distance,
  stationary,
  desiredAccuracy
) => {
  NativeModules.RNRoam.startSelfTrackingDistanceInterval(
    distance,
    stationary,
    desiredAccuracy
  )
}
const stopSelfTracking = () => {
  NativeModules.RNRoam.stopSelfTracking()
}
const enableAccuracyEngine = () => {
  NativeModules.RNRoam.enableAccuracyEngine()
}
const disableAccuracyEngine = () => {
  NativeModules.RNRoam.disableAccuracyEngine()
}
const startListener = (event, callback) =>
  eventEmitter.addListener(event, callback)
const stopListener = (event, callback) => {
  if (callback) {
    eventEmitter.removeListener(event, callback)
  } else {
    eventEmitter.removeAllListeners(event)
  }
}
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
  getTripSummary
}
export default Roam
// # sourceMappingURL=index.js.map
