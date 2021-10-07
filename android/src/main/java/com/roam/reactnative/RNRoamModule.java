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
import com.roam.sdk.Roam;
import com.roam.sdk.RoamPublish;
import com.roam.sdk.RoamTrackingMode;
import com.roam.sdk.callback.RoamActiveTripsCallback;
import com.roam.sdk.callback.RoamCallback;
import com.roam.sdk.callback.RoamCreateTripCallback;
import com.roam.sdk.callback.RoamDeleteTripCallback;
import com.roam.sdk.callback.RoamLocationCallback;
import com.roam.sdk.callback.RoamLogoutCallback;
import com.roam.sdk.callback.RoamSyncTripCallback;
import com.roam.sdk.callback.RoamTripCallback;
import com.roam.sdk.callback.RoamTripSummaryCallback;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamTrip;
import com.roam.sdk.models.RoamUser;
import com.roam.sdk.models.createtrip.RoamCreateTrip;
import com.roam.sdk.models.tripsummary.RoamTripSummary;

import org.json.JSONException;
import org.json.JSONObject;

public class RNRoamModule extends ReactContextBaseJavaModule {
  ReactApplicationContext reactContext;

  public RNRoamModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNRoam";
  }

  @ReactMethod
  public void createUser(String description, final Callback successCallback, final Callback errorCallback) {
    Roam.createUser(description, null, new RoamCallback() {
      @Override
      public void onSuccess(RoamUser roamUser) {
        successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }



  @ReactMethod
  public void getUser(String userId, final Callback successCallback, final Callback errorCallback) {
    Roam.getUser(userId, new RoamCallback() {
      @Override
      public void onSuccess(RoamUser roamUser) {
        successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void setDescription(String description) {
    Roam.setDescription(description);
  }

  @ReactMethod
  public void toggleEvents(boolean geofence, boolean trip, boolean location, boolean movingGeofence, final Callback successCallback, final Callback errorCallback) {
    Roam.toggleEvents(geofence, trip, location, movingGeofence, new RoamCallback() {
      @Override
      public void onSuccess(RoamUser roamUser) {
        successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void toggleListener(boolean location, boolean event, final Callback successCallback, final Callback errorCallback) {
    Roam.toggleListener(location, event, new RoamCallback() {
      @Override
      public void onSuccess(RoamUser roamUser) {
        successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void getEventsStatus(final Callback successCallback, final Callback errorCallback) {
    Roam.getEventsStatus(new RoamCallback() {
      @Override
      public void onSuccess(RoamUser roamUser) {
        successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void getListenerStatus(final Callback successCallback, final Callback errorCallback) {
    Roam.getListenerStatus(new RoamCallback() {
      @Override
      public void onSuccess(RoamUser roamUser) {
        successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void subscribe(String type, String userId) {
    switch (type) {
      case "EVENTS":
        Roam.subscribe(Roam.Subscribe.EVENTS, userId);
        break;
      case "LOCATION":
        Roam.subscribe(Roam.Subscribe.LOCATION, userId);
        break;
      case "BOTH":
        Roam.subscribe(Roam.Subscribe.BOTH, userId);
        break;
    }
  }

  @ReactMethod
  public void unSubscribe(String type, String userId) {
    switch (type) {
      case "EVENTS":
        Roam.unSubscribe(Roam.Subscribe.EVENTS, userId);
        break;
      case "LOCATION":
        Roam.unSubscribe(Roam.Subscribe.LOCATION, userId);
        break;
      case "BOTH":
        Roam.unSubscribe(Roam.Subscribe.BOTH, userId);
        break;
    }
  }

  @ReactMethod
  public void subscribeTripStatus(String tripId) {
    Roam.subscribeTripStatus(tripId);
  }

  @ReactMethod
  public void unSubscribeTripStatus(String tripId) {
    Roam.unSubscribeTripStatus(tripId);
  }

  @ReactMethod
  public void disableBatteryOptimization() {
    Roam.disableBatteryOptimization();
  }

  @ReactMethod
  public void isBatteryOptimizationEnabled(Callback callback) {
    callback.invoke(RNRoamUtils.checkEnabled(Roam.isBatteryOptimizationEnabled()));
  }

  @ReactMethod
  public void checkLocationPermission(Callback callback) {
    callback.invoke(RNRoamUtils.isGranted(Roam.checkLocationPermission()));
  }

  @ReactMethod
  public void checkLocationServices(Callback callback) {
    callback.invoke(RNRoamUtils.checkEnabled(Roam.checkLocationServices()));
  }

  @ReactMethod
  public void checkBackgroundLocationPermission(Callback callback) {
    callback.invoke(RNRoamUtils.isGranted(Roam.checkBackgroundLocationPermission()));
  }

  @ReactMethod
  public void requestLocationPermission() {
    Activity activity = getCurrentActivity();
    if (activity != null) {
      Roam.requestLocationPermission(activity);
    }
  }

  @ReactMethod
  public void requestLocationServices() {
    Activity activity = getCurrentActivity();
    if (activity != null) {
      Roam.requestLocationServices(activity);
    }
  }

  @ReactMethod
  public void requestBackgroundLocationPermission() {
    Activity activity = getCurrentActivity();
    if (activity != null) {
      Roam.requestBackgroundLocationPermission(activity);
    }
  }

  @ReactMethod
  public void startTracking(String trackingMode) {
    switch (trackingMode) {
      case "ACTIVE":
        Roam.startTracking(RoamTrackingMode.ACTIVE);
        break;
      case "BALANCED":
        Roam.startTracking(RoamTrackingMode.BALANCED);
        break;
      case "PASSIVE":
        Roam.startTracking(RoamTrackingMode.PASSIVE);
        break;
    }
  }

  @ReactMethod
  public void startTrackingTimeInterval(int timeInterval, String desiredAccuracy) {
    RoamTrackingMode.Builder builder = new RoamTrackingMode.Builder(timeInterval);
    switch (desiredAccuracy) {
      case "MEDIUM":
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.MEDIUM);
        break;
      case "LOW":
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.LOW);
        break;
      default:
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH);
        break;
    }
    Roam.startTracking(builder.build());
  }

  @ReactMethod
  public void startTrackingDistanceInterval(int distance, int stationary, String desiredAccuracy) {
    RoamTrackingMode.Builder builder = new RoamTrackingMode.Builder(distance, stationary);
    switch (desiredAccuracy) {
      case "MEDIUM":
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.MEDIUM);
        break;
      case "LOW":
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.LOW);
        break;
      default:
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH);
        break;
    }
    Roam.startTracking(builder.build());
  }

  @ReactMethod
  public void stopTracking() {
    Roam.stopTracking();
  }

  @ReactMethod
  public void isLocationTracking(Callback callback) {
    callback.invoke(RNRoamUtils.checkEnabled(Roam.isLocationTracking()));
  }

  @ReactMethod
  public void allowMockLocation(boolean value) {
    Roam.allowMockLocation(value);
  }

  @ReactMethod
  public void getCurrentLocationListener(int accuracy) {
    Roam.getCurrentLocation(accuracy);
  }

  @ReactMethod
  public void getCurrentLocation(String desired_Accuracy, int accuracy, final Callback successCallback, final Callback errorCallback) {
    RoamTrackingMode.DesiredAccuracy desiredAccuracy = null;
    switch (desired_Accuracy) {
      case "MEDIUM":
        desiredAccuracy = RoamTrackingMode.DesiredAccuracy.MEDIUM;
        break;
      case "LOW":
        desiredAccuracy = RoamTrackingMode.DesiredAccuracy.LOW;
        break;
      default:
        desiredAccuracy = RoamTrackingMode.DesiredAccuracy.HIGH;
        break;
    }
    Roam.getCurrentLocation(desiredAccuracy, accuracy, new RoamLocationCallback() {
      @Override
      public void location(Location location) {
        WritableMap map = Arguments.createMap();
        map.putDouble("latitude", location.getLatitude());
        map.putDouble("longitude", location.getLongitude());
        map.putDouble("accuracy", location.getAccuracy());
        successCallback.invoke(map);
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void updateCurrentLocation(String desiredAccuracy, int accuracy) {
    switch (desiredAccuracy) {
      case "MEDIUM":
        Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.MEDIUM, accuracy, null);
        break;
      case "LOW":
        Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.LOW, accuracy, null);
        break;
      default:
        Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.HIGH, accuracy, null);
        break;
    }

  }

  @ReactMethod
  public void startTrip(String tripId, String description, final Callback successCallback, final Callback errorCallback) {
    Roam.startTrip(tripId, description, new RoamTripCallback() {
      @Override
      public void onSuccess(String msg) {
        WritableMap map = Arguments.createMap();
        map.putString("message", msg);
        successCallback.invoke(map);
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void resumeTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
    Roam.resumeTrip(tripId, new RoamTripCallback() {
      @Override
      public void onSuccess(String msg) {
        WritableMap map = Arguments.createMap();
        map.putString("message", msg);
        successCallback.invoke(map);
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void pauseTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
    Roam.pauseTrip(tripId, new RoamTripCallback() {
      @Override
      public void onSuccess(String msg) {
        WritableMap map = Arguments.createMap();
        map.putString("message", msg);
        successCallback.invoke(map);
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void getTripSummary(String tripId, final Callback successCallback, final Callback errorCallback) {
    Roam.getTripSummary(tripId, new RoamTripSummaryCallback() {
      @Override
      public void onSuccess(RoamTripSummary roamTripSummary) {
        successCallback.invoke(RNRoamUtils.mapForTrip(roamTripSummary));
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void stopTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
    Roam.stopTrip(tripId, new RoamTripCallback() {
      @Override
      public void onSuccess(String msg) {
        WritableMap map = Arguments.createMap();
        map.putString("message", msg);
        successCallback.invoke(map);
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void forceStopTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
    Roam.forceStopTrip(tripId, new RoamTripCallback() {
      @Override
      public void onSuccess(String msg) {
        WritableMap map = Arguments.createMap();
        map.putString("message", msg);
        successCallback.invoke(map);
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void createTrip(boolean offline, final Callback successCallback, final Callback errorCallback) {
    Roam.createTrip(null, null, offline, null, new RoamCreateTripCallback() {
      @Override
      public void onSuccess(RoamCreateTrip roamCreateTrip) {
        successCallback.invoke(RNRoamUtils.mapForCreateTrip(roamCreateTrip));
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void deleteTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
    Roam.deleteTrip(tripId, new RoamDeleteTripCallback() {
      @Override
      public void onSuccess(String msg) {
        WritableMap map = Arguments.createMap();
        map.putString("message", msg);
        successCallback.invoke(map);
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void syncTrip(String tripId, final Callback successCallback, final Callback errorCallback) {
    Roam.syncTrip(tripId, new RoamSyncTripCallback() {
      @Override
      public void onSuccess(String msg) {
        WritableMap map = Arguments.createMap();
        map.putString("message", msg);
        successCallback.invoke(map);
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void activeTrips(boolean value, final Callback successCallback, final Callback errorCallback) {
    Roam.activeTrips(value, new RoamActiveTripsCallback() {
      @Override
      public void onSuccess(RoamTrip roamTrip) {
        WritableMap map = Arguments.createMap();
        map.putMap("activeTrips", RNRoamUtils.mapForTripList(roamTrip.getActiveTrips()));
        successCallback.invoke(map);
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void logout(final Callback successCallback, final Callback errorCallback) {
    Roam.logout(new RoamLogoutCallback() {
      @Override
      public void onSuccess(String message) {
        WritableMap map = Arguments.createMap();
        map.putString("message", message);
        successCallback.invoke(map);
      }

      @Override
      public void onFailure(RoamError roamError) {
        errorCallback.invoke(RNRoamUtils.mapForError(roamError));
      }
    });
  }

  @ReactMethod
  public void setTrackingInAppState(String appState) {
    try {
      if (appState != null) {
        switch (appState) {
          case "ALWAYS_ON":
            Roam.setTrackingInAppState(RoamTrackingMode.AppState.ALWAYS_ON);
            break;
          case "FOREGROUND":
            Roam.setTrackingInAppState(RoamTrackingMode.AppState.FOREGROUND);
            break;
          case "BACKGROUND":
            Roam.setTrackingInAppState(RoamTrackingMode.AppState.BACKGROUND);
            break;
        }
      }
    } catch (Exception e) {
    }
  }

  @ReactMethod
  public void offlineLocationTracking(boolean value) {
    Roam.offlineLocationTracking(value);
  }

  @ReactMethod
  public void publishAndSave(ReadableMap readableMap) {
    RoamPublish.Builder roamPublish = new RoamPublish.Builder();
    if (readableMap != null && readableMap.getMap(RNRoamUtils.METADATA) != null) {
      try {
        ReadableMap rm = readableMap.getMap(RNRoamUtils.METADATA);
        JSONObject jsonObject = new JSONObject(rm.toString());
        if (jsonObject.getJSONObject("NativeMap").length() > 0) {
          roamPublish.metadata(jsonObject.getJSONObject("NativeMap"));
        }
        roamPublish.metadata(jsonObject.getJSONObject("NativeMap"));
      } catch (JSONException e) {
      }
    }
    Roam.publishAndSave(roamPublish.build());
  }

  @ReactMethod
  public void publishOnly(ReadableArray readableArray, ReadableMap readableMap) {
    RoamPublish.Builder roamPublish = new RoamPublish.Builder();
    if (readableArray != null && readableArray.size() > 0) {
      for (int i = 0; i < readableArray.size(); i++) {
        if (readableArray.getString(i).equals(RNRoamUtils.APP_ID)) {
          roamPublish.appId();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.USER_ID)) {
          roamPublish.userId();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.GEOFENCE_EVENTS)) {
          roamPublish.geofenceEvents();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.LOCATION_EVENTS)) {
          roamPublish.locationEvents();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.NEARBY_EVENTS)) {
          roamPublish.nearbyEvents();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.TRIPS_EVENTS)) {
          roamPublish.tripsEvents();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.LOCATION_LISTENER)) {
          roamPublish.locationListener();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.EVENT_LISTENER)) {
          roamPublish.eventListener();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.ALTITUDE)) {
          roamPublish.altitude();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.COURSE)) {
          roamPublish.course();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.SPEED)) {
          roamPublish.speed();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.HORIZONTAL_ACCURACY)) {
          roamPublish.horizontalAccuracy();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.VERTICAL_ACCURACY)) {
          roamPublish.verticalAccuracy();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.APP_CONTEXT)) {
          roamPublish.appContext();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.ALLOW_MOCKED)) {
          roamPublish.allowMocked();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.BATTERY_REMAINING)) {
          roamPublish.batteryRemaining();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.BATTERY_SAVER)) {
          roamPublish.batterySaver();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.BATTERY_STATUS)) {
          roamPublish.batteryStatus();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.ACTIVITY)) {
          roamPublish.activity();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.AIRPLANE_MODE)) {
          roamPublish.airplaneMode();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.DEVICE_MANUFACTURE)) {
          roamPublish.deviceManufacturer();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.DEVICE_MODEL)) {
          roamPublish.deviceModel();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.TRACKING_MODE)) {
          roamPublish.trackingMode();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.LOCATIONPERMISSION)) {
          roamPublish.locationPermission();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.NETWORK_STATUS)) {
          roamPublish.networkStatus();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.GPS_STATUS)) {
          roamPublish.gpsStatus();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.OS_VERSION)) {
          roamPublish.osVersion();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.RECORDERD_AT)) {
          roamPublish.recordedAt();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.TZ_OFFSET)) {
          roamPublish.tzOffset();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.RECORDERD_AT)) {
          roamPublish.recordedAt();
          continue;
        }
        if (readableArray.getString(i).equals(RNRoamUtils.ACTIVITY)) {
          roamPublish.activity();
        }
      }
    }
    if (readableMap != null && readableMap.getMap(RNRoamUtils.METADATA) != null) {
      try {
        ReadableMap rm = readableMap.getMap(RNRoamUtils.METADATA);
        JSONObject jsonObject = new JSONObject(rm.toString());
        if (jsonObject.getJSONObject("NativeMap").length() > 0) {
          roamPublish.metadata(jsonObject.getJSONObject("NativeMap"));
        }
        roamPublish.metadata(jsonObject.getJSONObject("NativeMap"));
      } catch (JSONException e) {
      }
    }
    Roam.publishOnly(roamPublish.build());
  }

  @ReactMethod
  public void stopPublishing() {
    Roam.stopPublishing();
  }

  @ReactMethod
  public void enableAccuracyEngine() {
    Roam.enableAccuracyEngine();
  }

  @ReactMethod
  public void disableAccuracyEngine() {
    Roam.disableAccuracyEngine();
  }

  @ReactMethod
  public void startSelfTracking(String trackingMode) {
    switch (trackingMode) {
      case "ACTIVE":
        Roam.startTracking(RoamTrackingMode.ACTIVE);
        break;
      case "BALANCED":
        Roam.startTracking(RoamTrackingMode.BALANCED);
        break;
      case "PASSIVE":
        Roam.startTracking(RoamTrackingMode.PASSIVE);
        break;
    }
  }

  @ReactMethod
  public void startSelfTrackingTimeInterval(int timeInterval, String desiredAccuracy) {
    RoamTrackingMode.Builder builder = new RoamTrackingMode.Builder(timeInterval);
    switch (desiredAccuracy) {
      case "MEDIUM":
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.MEDIUM);
        break;
      case "LOW":
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.LOW);
        break;
      default:
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH);
        break;
    }
    Roam.startTracking(builder.build());
  }

  @ReactMethod
  public void startSelfTrackingDistanceInterval(int distance, int stationary, String desiredAccuracy) {
    RoamTrackingMode.Builder builder = new RoamTrackingMode.Builder(distance, stationary);
    switch (desiredAccuracy) {
      case "MEDIUM":
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.MEDIUM);
        break;
      case "LOW":
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.LOW);
        break;
      default:
        builder.setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH);
        break;
    }
    Roam.startTracking(builder.build());
  }

  @ReactMethod
  public void stopSelfTracking() {
    Roam.stopTracking();
  }
}