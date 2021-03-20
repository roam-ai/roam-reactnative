package com.roam.reactnative;

import android.app.Activity;
import android.location.Location;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.geospark.lib.GeoSpark;
import com.geospark.lib.GeoSparkPublish;
import com.geospark.lib.GeoSparkTrackingMode;
import com.geospark.lib.callback.GeoSparkActiveTripsCallback;
import com.geospark.lib.callback.GeoSparkCallback;
import com.geospark.lib.callback.GeoSparkCreateTripCallback;
import com.geospark.lib.callback.GeoSparkDeleteTripCallback;
import com.geospark.lib.callback.GeoSparkLocationCallback;
import com.geospark.lib.callback.GeoSparkLogoutCallback;
import com.geospark.lib.callback.GeoSparkSyncTripCallback;
import com.geospark.lib.callback.GeoSparkTripCallback;
import com.geospark.lib.models.GeoSparkError;
import com.geospark.lib.models.GeoSparkTrip;
import com.geospark.lib.models.GeoSparkUser;
import com.geospark.lib.models.createtrip.GeoSparkCreateTrip;

import org.json.JSONException;
import org.json.JSONObject;

public class RNRoam extends ReactContextBaseJavaModule {
    ReactApplicationContext reactContext;

    public RNRoam(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNRoam";
    }

    @ReactMethod
    public void createUser(String description, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.createUser(description, new GeoSparkCallback() {
            @Override
            public void onSuccess(GeoSparkUser geoSparkUser) {
                successCallback.invoke(RNRoamUtils.mapForUser(geoSparkUser));
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void getUser(String userId, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.getUser(userId, new GeoSparkCallback() {
            @Override
            public void onSuccess(GeoSparkUser geoSparkUser) {
                successCallback.invoke(RNRoamUtils.mapForUser(geoSparkUser));
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void setDescription(String description) {
        GeoSpark.setDescription(description);
    }

    @ReactMethod
    public void toggleEvents(boolean geofence, boolean trip, boolean location, boolean movingGeofence, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.toggleEvents(geofence, trip, location, movingGeofence, new GeoSparkCallback() {
            @Override
            public void onSuccess(GeoSparkUser geoSparkUser) {
                successCallback.invoke(RNRoamUtils.mapForUser(geoSparkUser));
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void toggleListener(boolean location, boolean event, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.toggleListener(location, event, new GeoSparkCallback() {
            @Override
            public void onSuccess(GeoSparkUser geoSparkUser) {
                successCallback.invoke(RNRoamUtils.mapForUser(geoSparkUser));
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void getEventsStatus(final Callback successCallback, final Callback errorCallback) {
        GeoSpark.getEventsStatus(new GeoSparkCallback() {
            @Override
            public void onSuccess(GeoSparkUser geoSparkUser) {
                successCallback.invoke(RNRoamUtils.mapForUser(geoSparkUser));
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void getListenerStatus(final Callback successCallback, final Callback errorCallback) {
        GeoSpark.getListenerStatus(new GeoSparkCallback() {
            @Override
            public void onSuccess(GeoSparkUser geoSparkUser) {
                successCallback.invoke(RNRoamUtils.mapForUser(geoSparkUser));
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void subscribe(String type, String userId) {
        switch (type) {
            case "EVENTS":
                GeoSpark.subscribe(GeoSpark.Subscribe.EVENTS, userId);
                break;
            case "LOCATION":
                GeoSpark.subscribe(GeoSpark.Subscribe.LOCATION, userId);
                break;
            case "BOTH":
                GeoSpark.subscribe(GeoSpark.Subscribe.BOTH, userId);
                break;
        }
    }

    @ReactMethod
    public void unSubscribe(String type, String userId) {
        switch (type) {
            case "EVENTS":
                GeoSpark.unSubscribe(GeoSpark.Subscribe.EVENTS, userId);
                break;
            case "LOCATION":
                GeoSpark.unSubscribe(GeoSpark.Subscribe.LOCATION, userId);
                break;
            case "BOTH":
                GeoSpark.unSubscribe(GeoSpark.Subscribe.BOTH, userId);
                break;
        }
    }

    @ReactMethod
    public void subscribeTripStatus(String tripId) {
        GeoSpark.subscribeTripStatus(tripId);
    }

    @ReactMethod
    public void unSubscribeTripStatus(String tripId) {
        GeoSpark.unSubscribeTripStatus(tripId);
    }

    @ReactMethod
    public void disableBatteryOptimization() {
        GeoSpark.disableBatteryOptimization();
    }

    @ReactMethod
    public void isBatteryOptimizationEnabled(Callback callback) {
        callback.invoke(RNRoamUtils.checkEnabled(GeoSpark.isBatteryOptimizationEnabled()));
    }

    @ReactMethod
    public void checkLocationPermission(Callback callback) {
        callback.invoke(RNRoamUtils.isGranted(GeoSpark.checkLocationPermission()));
    }

    @ReactMethod
    public void checkLocationServices(Callback callback) {
        callback.invoke(RNRoamUtils.checkEnabled(GeoSpark.checkLocationServices()));
    }

    @ReactMethod
    public void checkBackgroundLocationPermission(Callback callback) {
        callback.invoke(RNRoamUtils.isGranted(GeoSpark.checkBackgroundLocationPermission()));
    }

    @ReactMethod
    public void requestLocationPermission() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            GeoSpark.requestLocationPermission(activity);
        }
    }

    @ReactMethod
    public void requestLocationServices() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            GeoSpark.requestLocationServices(activity);
        }
    }

    @ReactMethod
    public void requestBackgroundLocationPermission() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            GeoSpark.requestBackgroundLocationPermission(activity);
        }
    }

    @ReactMethod
    public void startTracking(String trackingMode) {
        switch (trackingMode) {
            case "ACTIVE":
                GeoSpark.startTracking(GeoSparkTrackingMode.ACTIVE);
                break;
            case "BALANCED":
                GeoSpark.startTracking(GeoSparkTrackingMode.BALANCED);
                break;
            case "PASSIVE":
                GeoSpark.startTracking(GeoSparkTrackingMode.PASSIVE);
                break;
        }
    }

    @ReactMethod
    public void startTrackingTimeInterval(int timeInterval, String desiredAccuracy) {
        GeoSparkTrackingMode.Builder builder = new GeoSparkTrackingMode.Builder(timeInterval);
        switch (desiredAccuracy) {
            case "MEDIUM":
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.MEDIUM);
                break;
            case "LOW":
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.LOW);
                break;
            default:
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.HIGH);
                break;
        }
        GeoSpark.startTracking(builder.build());
    }

    @ReactMethod
    public void startTrackingDistanceInterval(int distance, int stationary, String desiredAccuracy) {
        GeoSparkTrackingMode.Builder builder = new GeoSparkTrackingMode.Builder(distance, stationary);
        switch (desiredAccuracy) {
            case "MEDIUM":
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.MEDIUM);
                break;
            case "LOW":
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.LOW);
                break;
            default:
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.HIGH);
                break;
        }
        GeoSpark.startTracking(builder.build());
    }

    @ReactMethod
    public void stopTracking() {
        GeoSpark.stopTracking();
    }

    @ReactMethod
    public void isLocationTracking(Callback callback) {
        callback.invoke(RNRoamUtils.checkEnabled(GeoSpark.isLocationTracking()));
    }

    @ReactMethod
    public void allowMockLocation(boolean value) {
        GeoSpark.allowMockLocation(value);
    }

    @ReactMethod
    public void getCurrentLocationListener(int accuracy) {
        GeoSpark.getCurrentLocation(accuracy);
    }

    @ReactMethod
    public void getCurrentLocation(String desired_Accuracy, int accuracy, final Callback successCallback, final Callback errorCallback) {
        GeoSparkTrackingMode.DesiredAccuracy desiredAccuracy = null;
        switch (desired_Accuracy) {
            case "MEDIUM":
                desiredAccuracy = GeoSparkTrackingMode.DesiredAccuracy.MEDIUM;
                break;
            case "LOW":
                desiredAccuracy = GeoSparkTrackingMode.DesiredAccuracy.LOW;
                break;
            default:
                desiredAccuracy = GeoSparkTrackingMode.DesiredAccuracy.HIGH;
                break;
        }
        GeoSpark.getCurrentLocation(desiredAccuracy, accuracy, new GeoSparkLocationCallback() {
            @Override
            public void location(Location location) {
                WritableMap map = Arguments.createMap();
                map.putDouble("latitude", location.getLatitude());
                map.putDouble("longitude", location.getLongitude());
                map.putDouble("accuracy", location.getAccuracy());
                successCallback.invoke(map);
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void updateCurrentLocation(String desiredAccuracy, int accuracy) {
        switch (desiredAccuracy) {
            case "MEDIUM":
                GeoSpark.updateCurrentLocation(GeoSparkTrackingMode.DesiredAccuracy.MEDIUM, accuracy);
                break;
            case "LOW":
                GeoSpark.updateCurrentLocation(GeoSparkTrackingMode.DesiredAccuracy.LOW, accuracy);
                break;
            default:
                GeoSpark.updateCurrentLocation(GeoSparkTrackingMode.DesiredAccuracy.HIGH, accuracy);
                break;
        }

    }

    @ReactMethod
    public void startTrip(String tripId, String description, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.startTrip(tripId, description, new GeoSparkTripCallback() {
            @Override
            public void onSuccess(String msg) {
                WritableMap map = Arguments.createMap();
                map.putString("message", msg);
                successCallback.invoke(map);
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void resumeTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.resumeTrip(tripId, new GeoSparkTripCallback() {
            @Override
            public void onSuccess(String msg) {
                WritableMap map = Arguments.createMap();
                map.putString("message", msg);
                successCallback.invoke(map);
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void pauseTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.pauseTrip(tripId, new GeoSparkTripCallback() {
            @Override
            public void onSuccess(String msg) {
                WritableMap map = Arguments.createMap();
                map.putString("message", msg);
                successCallback.invoke(map);
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void stopTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.stopTrip(tripId, new GeoSparkTripCallback() {
            @Override
            public void onSuccess(String msg) {
                WritableMap map = Arguments.createMap();
                map.putString("message", msg);
                successCallback.invoke(map);
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void forceStopTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.forceStopTrip(tripId, new GeoSparkTripCallback() {
            @Override
            public void onSuccess(String msg) {
                WritableMap map = Arguments.createMap();
                map.putString("message", msg);
                successCallback.invoke(map);
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void createTrip(boolean offline, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.createTrip(null, null, offline, new GeoSparkCreateTripCallback() {
            @Override
            public void onSuccess(GeoSparkCreateTrip geoSparkCreateTrip) {
                successCallback.invoke(RNRoamUtils.mapForCreateTrip(geoSparkCreateTrip));
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void deleteTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.deleteTrip(tripId, new GeoSparkDeleteTripCallback() {
            @Override
            public void onSuccess(String msg) {
                WritableMap map = Arguments.createMap();
                map.putString("message", msg);
                successCallback.invoke(map);
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void syncTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.syncTrip(tripId, new GeoSparkSyncTripCallback() {
            @Override
            public void onSuccess(String msg) {
                WritableMap map = Arguments.createMap();
                map.putString("message", msg);
                successCallback.invoke(map);
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void activeTrips(boolean value, final Callback successCallback, final Callback errorCallback) {
        GeoSpark.activeTrips(value, new GeoSparkActiveTripsCallback() {
            @Override
            public void onSuccess(GeoSparkTrip geoSparkTrip) {
                WritableMap map = Arguments.createMap();
                map.putMap("activeTrips", RNRoamUtils.mapForTripList(geoSparkTrip.getActiveTrips()));
                successCallback.invoke(map);
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void logout(final Callback successCallback, final Callback errorCallback) {
        GeoSpark.logout(new GeoSparkLogoutCallback() {
            @Override
            public void onSuccess(String message) {
                WritableMap map = Arguments.createMap();
                map.putString("message", message);
                successCallback.invoke(map);
            }

            @Override
            public void onFailure(GeoSparkError geoSparkError) {
                errorCallback.invoke(RNRoamUtils.mapForError(geoSparkError));
            }
        });
    }

    @ReactMethod
    public void setTrackingInAppState(String appState) {
        try {
            if (appState != null) {
                switch (appState) {
                    case "ALWAYS_ON":
                        GeoSpark.setTrackingInAppState(GeoSparkTrackingMode.AppState.ALWAYS_ON);
                        break;
                    case "FOREGROUND":
                        GeoSpark.setTrackingInAppState(GeoSparkTrackingMode.AppState.FOREGROUND);
                        break;
                    case "BACKGROUND":
                        GeoSpark.setTrackingInAppState(GeoSparkTrackingMode.AppState.BACKGROUND);
                        break;
                }
            }
        } catch (Exception e) {
        }
    }

    @ReactMethod
    public void offlineLocationTracking(boolean value) {
        GeoSpark.offlineLocationTracking(value);
    }

    @ReactMethod
    public void publishAndSave(ReadableMap readableMap) {
        GeoSparkPublish.Builder geoSparkPublish = new GeoSparkPublish.Builder();
        if (readableMap != null && readableMap.getMap(RNRoamUtils.METADATA) != null) {
            try {
                ReadableMap rm = readableMap.getMap(RNRoamUtils.METADATA);
                JSONObject jsonObject = new JSONObject(rm.toString());
                if (jsonObject.getJSONObject("NativeMap").length() > 0) {
                    geoSparkPublish.metadata(jsonObject.getJSONObject("NativeMap"));
                }
                geoSparkPublish.metadata(jsonObject.getJSONObject("NativeMap"));
            } catch (JSONException e) {
            }
        }
        GeoSpark.publishAndSave(geoSparkPublish.build());
    }

    @ReactMethod
    public void publishOnly(ReadableArray readableArray, ReadableMap readableMap) {
        GeoSparkPublish.Builder geoSparkPublish = new GeoSparkPublish.Builder();
        if (readableArray != null && readableArray.size() > 0) {
            for (int i = 0; i < readableArray.size(); i++) {
                if (readableArray.getString(i).equals(RNRoamUtils.APP_ID)) {
                    geoSparkPublish.appId();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.USER_ID)) {
                    geoSparkPublish.userId();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.GEOFENCE_EVENTS)) {
                    geoSparkPublish.geofenceEvents();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.LOCATION_EVENTS)) {
                    geoSparkPublish.locationEvents();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.NEARBY_EVENTS)) {
                    geoSparkPublish.nearbyEvents();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.TRIPS_EVENTS)) {
                    geoSparkPublish.tripsEvents();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.LOCATION_LISTENER)) {
                    geoSparkPublish.locationListener();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.EVENT_LISTENER)) {
                    geoSparkPublish.eventListener();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.ALTITUDE)) {
                    geoSparkPublish.altitude();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.COURSE)) {
                    geoSparkPublish.course();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.SPEED)) {
                    geoSparkPublish.speed();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.HORIZONTAL_ACCURACY)) {
                    geoSparkPublish.horizontalAccuracy();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.VERTICAL_ACCURACY)) {
                    geoSparkPublish.verticalAccuracy();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.APP_CONTEXT)) {
                    geoSparkPublish.appContext();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.ALLOW_MOCKED)) {
                    geoSparkPublish.allowMocked();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.BATTERY_REMAINING)) {
                    geoSparkPublish.batteryRemaining();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.BATTERY_SAVER)) {
                    geoSparkPublish.batterySaver();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.BATTERY_STATUS)) {
                    geoSparkPublish.batteryStatus();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.ACTIVITY)) {
                    geoSparkPublish.activity();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.AIRPLANE_MODE)) {
                    geoSparkPublish.airplaneMode();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.DEVICE_MANUFACTURE)) {
                    geoSparkPublish.deviceManufacturer();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.DEVICE_MODEL)) {
                    geoSparkPublish.deviceModel();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.TRACKING_MODE)) {
                    geoSparkPublish.trackingMode();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.LOCATIONPERMISSION)) {
                    geoSparkPublish.locationPermission();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.NETWORK_STATUS)) {
                    geoSparkPublish.networkStatus();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.GPS_STATUS)) {
                    geoSparkPublish.gpsStatus();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.OS_VERSION)) {
                    geoSparkPublish.osVersion();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.RECORDERD_AT)) {
                    geoSparkPublish.recordedAt();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.TZ_OFFSET)) {
                    geoSparkPublish.tzOffset();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.RECORDERD_AT)) {
                    geoSparkPublish.recordedAt();
                    continue;
                }
                if (readableArray.getString(i).equals(RNRoamUtils.ACTIVITY)) {
                    geoSparkPublish.activity();
                }
            }
        }
        if (readableMap != null && readableMap.getMap(RNRoamUtils.METADATA) != null) {
            try {
                ReadableMap rm = readableMap.getMap(RNRoamUtils.METADATA);
                JSONObject jsonObject = new JSONObject(rm.toString());
                if (jsonObject.getJSONObject("NativeMap").length() > 0) {
                    geoSparkPublish.metadata(jsonObject.getJSONObject("NativeMap"));
                }
                geoSparkPublish.metadata(jsonObject.getJSONObject("NativeMap"));
            } catch (JSONException e) {
            }
        }
        GeoSpark.publishOnly(geoSparkPublish.build());
    }

    @ReactMethod
    public void stopPublishing() {
        GeoSpark.stopPublishing();
    }

    @ReactMethod
    public void enableAccuracyEngine() {
        GeoSpark.enableAccuracyEngine();
    }

    @ReactMethod
    public void disableAccuracyEngine() {
        GeoSpark.disableAccuracyEngine();
    }

    @ReactMethod
    public void startSelfTracking(String trackingMode) {
        switch (trackingMode) {
            case "ACTIVE":
                GeoSpark.startSelfTracking(GeoSparkTrackingMode.ACTIVE);
                break;
            case "BALANCED":
                GeoSpark.startSelfTracking(GeoSparkTrackingMode.BALANCED);
                break;
            case "PASSIVE":
                GeoSpark.startSelfTracking(GeoSparkTrackingMode.PASSIVE);
                break;
        }
    }

    @ReactMethod
    public void startSelfTrackingTimeInterval(int timeInterval, String desiredAccuracy) {
        GeoSparkTrackingMode.Builder builder = new GeoSparkTrackingMode.Builder(timeInterval);
        switch (desiredAccuracy) {
            case "MEDIUM":
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.MEDIUM);
                break;
            case "LOW":
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.LOW);
                break;
            default:
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.HIGH);
                break;
        }
        GeoSpark.startSelfTracking(builder.build());
    }

    @ReactMethod
    public void startSelfTrackingDistanceInterval(int distance, int stationary, String desiredAccuracy) {
        GeoSparkTrackingMode.Builder builder = new GeoSparkTrackingMode.Builder(distance, stationary);
        switch (desiredAccuracy) {
            case "MEDIUM":
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.MEDIUM);
                break;
            case "LOW":
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.LOW);
                break;
            default:
                builder.setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.HIGH);
                break;
        }
        GeoSpark.startSelfTracking(builder.build());
    }

    @ReactMethod
    public void stopSelfTracking() {
        GeoSpark.stopSelfTracking();
    }
}