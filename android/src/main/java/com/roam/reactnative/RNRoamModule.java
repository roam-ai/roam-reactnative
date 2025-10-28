package com.roam.reactnative;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.gson.Gson;
import com.roam.sdk.Roam;
import com.roam.sdk.builder.RoamBatchPublish;
// import com.roam.sdk.builder.RoamPublish;
import com.roam.sdk.builder.RoamTrackingMode;
// import com.roam.sdk.callback.PublishCallback;
// import com.roam.sdk.callback.RoamBatchReceiverCallback;
// import com.roam.sdk.callback.RoamCallback;
import com.roam.sdk.callback.RoamLocationCallback;
// import com.roam.sdk.callback.RoamLogoutCallback;
// import com.roam.sdk.callback.RoamTrackingConfigCallback;
// import com.roam.sdk.callback.SubscribeCallback;
import com.roam.sdk.callback.TrackingCallback;
// import com.roam.sdk.enums.NetworkState;
// import com.roam.sdk.enums.Source;
// import com.roam.sdk.models.BatchReceiverConfig;
import com.roam.sdk.models.RoamError;
// import com.roam.sdk.models.RoamUser;
// import com.roam.sdk.models.TrackingConfig;
// import com.roam.sdk.trips_v2.RoamTrip;
// import com.roam.sdk.trips_v2.callback.RoamActiveTripsCallback;
// import com.roam.sdk.trips_v2.callback.RoamDeleteTripCallback;
// import com.roam.sdk.trips_v2.callback.RoamSyncTripCallback;
// import com.roam.sdk.trips_v2.callback.RoamTripCallback;
// import com.roam.sdk.trips_v2.models.Error;
// import com.roam.sdk.trips_v2.models.RoamActiveTripsResponse;
// import com.roam.sdk.trips_v2.models.RoamDeleteTripResponse;
// import com.roam.sdk.trips_v2.models.RoamSyncTripResponse;
// import com.roam.sdk.trips_v2.models.RoamTripResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@ReactModule(name = "RNRoam")
public class RNRoamModule extends ReactContextBaseJavaModule {
  ReactApplicationContext reactContext;
  private static RNRoamModule instance;

  public RNRoamModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    instance = this; // Store instance for RNRoamReceiver to use
  }

  // Static method to get the current instance for RNRoamReceiver
  public static RNRoamModule getInstance() {
    return instance;
  }

  // Method to emit events to JavaScript
  public void sendEvent(String eventName, Object params) {
    android.util.Log.d("RNRoamModule", "sendEvent called with eventName: " + eventName + ", params: " + params);
    try {
      if (reactContext != null && reactContext.hasActiveReactInstance()) {
        // Try multiple approaches for New Architecture compatibility
        boolean success = false;

        // Method 1: RCTNativeAppEventEmitter (preferred for New Architecture)
        try {
          reactContext.getJSModule(RCTNativeAppEventEmitter.class).emit(eventName, params);
          android.util.Log.d("RNRoamModule", "Successfully emitted event via RCTNativeAppEventEmitter: " + eventName);
          success = true;
        } catch (Exception e1) {
          android.util.Log.w("RNRoamModule", "RCTNativeAppEventEmitter failed: " + e1.getMessage());
        }

        // Method 2: DeviceEventManagerModule (fallback)
        if (!success) {
          try {
            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
            android.util.Log.d("RNRoamModule", "Successfully emitted event via DeviceEventManagerModule: " + eventName);
            success = true;
          } catch (Exception e2) {
            android.util.Log.w("RNRoamModule", "DeviceEventManagerModule also failed: " + e2.getMessage());
          }
        }

        if (!success) {
          android.util.Log.e("RNRoamModule", "All event emission methods failed for: " + eventName);
        }
      } else {
        android.util.Log.w("RNRoamModule", "React context not active, cannot emit event: " + eventName);
      }
    } catch (Exception e) {
      android.util.Log.e("RNRoamModule", "Failed to emit event: " + eventName, e);
    }
  }

  @Override
  public String getName() {
    return "RNRoam";
  }

  // Required for New Architecture event emitter support
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    return constants;
  }

  // Support for event names in New Architecture
  @VisibleForTesting
  public String[] getEventNames() {
    return new String[]{"location", "events", "location_received", "error", "locationAuthorizationChange", "connectivityChangeEvent"};
  }

  // Required methods for NativeEventEmitter support in New Architecture
  @ReactMethod
  public void addListener(String eventName) {
    // Keep: Required for RN built in Event Emitter Calls.
  }

  @ReactMethod
  public void removeListeners(double count) {
    // Keep: Required for RN built in Event Emitter Calls.
  }




  // @ReactMethod
  // public void createUser(String description, ReadableMap metadata, final Callback successCallback, final Callback errorCallback) {
  //   Roam.createUser(description, (metadata != null) ? new JSONObject(metadata.toHashMap()) : null, new RoamCallback() {
  //     @Override
  //     public void onSuccess(RoamUser roamUser) {
  //       successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }



  // @ReactMethod
  // public void getUser(String userId, final Callback successCallback, final Callback errorCallback) {
  //   Roam.getUser(userId, new RoamCallback() {
  //     @Override
  //     public void onSuccess(RoamUser roamUser) {
  //       successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void setDescription(String description) {
  //   Roam.setDescription(description, null);
  // }

  // @ReactMethod
  // public void toggleEvents(boolean geofence, boolean trip, boolean location, boolean movingGeofence, final Callback successCallback, final Callback errorCallback) {
  //   Roam.toggleEvents(geofence, trip, location, movingGeofence, new RoamCallback() {
  //     @Override
  //     public void onSuccess(RoamUser roamUser) {
  //       successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void toggleListener(boolean location, boolean event, final Callback successCallback, final Callback errorCallback) {
  //   Roam.toggleListener(location, event, new RoamCallback() {
  //     @Override
  //     public void onSuccess(RoamUser roamUser) {
  //       successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void getEventsStatus(final Callback successCallback, final Callback errorCallback) {
  //   Roam.getEventsStatus(new RoamCallback() {
  //     @Override
  //     public void onSuccess(RoamUser roamUser) {
  //       successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void getListenerStatus(final Callback successCallback, final Callback errorCallback) {
  //   Roam.getListenerStatus(new RoamCallback() {
  //     @Override
  //     public void onSuccess(RoamUser roamUser) {
  //       successCallback.invoke(RNRoamUtils.mapForUser(roamUser));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void subscribe(String type, String userId) {
  //   switch (type) {
  //     case "EVENTS":
  //       Roam.subscribe(Roam.Subscribe.EVENTS, userId, new SubscribeCallback() {
  //         @Override
  //         public void onSuccess(String s, String s1) {

  //         }

  //         @Override
  //         public void onError(RoamError roamError) {

  //         }
  //       });
  //       break;
  //     case "LOCATION":
  //       Roam.subscribe(Roam.Subscribe.LOCATION, userId, new SubscribeCallback() {
  //         @Override
  //         public void onSuccess(String s, String s1) {

  //         }

  //         @Override
  //         public void onError(RoamError roamError) {

  //         }
  //       });
  //       break;
  //     case "BOTH":
  //       Roam.subscribe(Roam.Subscribe.BOTH, userId, new SubscribeCallback() {
  //         @Override
  //         public void onSuccess(String s, String s1) {

  //         }

  //         @Override
  //         public void onError(RoamError roamError) {

  //         }
  //       });
  //       break;
  //   }
  // }

  // @ReactMethod
  // public void unSubscribe(String type, String userId) {
  //   switch (type) {
  //     case "EVENTS":
  //       Roam.unSubscribe(Roam.Subscribe.EVENTS, userId, new SubscribeCallback() {
  //         @Override
  //         public void onSuccess(String s, String s1) {

  //         }

  //         @Override
  //         public void onError(RoamError roamError) {

  //         }
  //       });
  //       break;
  //     case "LOCATION":
  //       Roam.unSubscribe(Roam.Subscribe.LOCATION, userId, new SubscribeCallback() {
  //         @Override
  //         public void onSuccess(String s, String s1) {

  //         }

  //         @Override
  //         public void onError(RoamError roamError) {

  //         }
  //       });
  //       break;
  //     case "BOTH":
  //       Roam.unSubscribe(Roam.Subscribe.BOTH, userId, new SubscribeCallback() {
  //         @Override
  //         public void onSuccess(String s, String s1) {

  //         }

  //         @Override
  //         public void onError(RoamError roamError) {

  //         }
  //       });
  //       break;
  //   }
  // }


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
public void requestPhoneStatePermission() {
    Activity currentActivity = getCurrentActivity();
    if (currentActivity != null && !Roam.checkPhoneStatePermission()) {
        Roam.requestPhoneStatePermission(currentActivity);
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
      Roam.startTracking(new TrackingCallback() {
          @Override
          public void onSuccess(String message) {

          }

          @Override
          public void onError(RoamError error) {

          }
      });
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
    Roam.startTracking(builder.build(), new TrackingCallback() {
      @Override
      public void onSuccess(String s) {

      }

      @Override
      public void onError(RoamError roamError) {

      }
    });
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
    Roam.startTracking(builder.build(), new TrackingCallback() {
      @Override
      public void onSuccess(String s) {

      }

      @Override
      public void onError(RoamError roamError) {

      }
    });
  }

  @ReactMethod
  public void stopTracking() {
    Roam.stopTracking(new TrackingCallback() {
      @Override
      public void onSuccess(String s) {

      }

      @Override
      public void onError(RoamError roamError) {

      }
    });
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
  public void setForegroundNotification(boolean enabled, String title, String description, String image, String activity, String roamService) {
    try{
      String[] split = image.split("/");
      String firstSubString = split[0];
      String secondSubString = split[1];
      int resId = reactContext.getResources().getIdentifier(
              secondSubString,
              firstSubString,
              reactContext.getPackageName()
      );
      Roam.setForegroundNotification(enabled, title, description, resId, activity);
    }catch (Exception e){
    }
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
      public void location(Location location, float direction) {
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
        Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.MEDIUM, accuracy);
        break;
      case "LOW":
        Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.LOW, accuracy);
        break;
      default:
        Roam.updateCurrentLocation(RoamTrackingMode.DesiredAccuracy.HIGH, accuracy);
        break;
    }

  }

  @ReactMethod
  public void updateLocationWhenStationary(int interval) {
    Roam.updateLocationWhenStationary(interval);
  }

  // @ReactMethod
  // public void setTrackingConfig(int accuracy, int timeout, String source, boolean discardLocation, final Callback successCallback, final Callback errorCallback){
  //   Source sourceObject = Source.ALL;
  //   switch (source){
  //     case "ALL":
  //       sourceObject = Source.ALL;
  //       break;
  //     case "LAST_KNOWN":
  //       sourceObject = Source.LAST_KNOWN;
  //       break;
  //     case "GPS":
  //       sourceObject = Source.GPS;
  //       break;
  //   }
  //   Roam.setTrackingConfig(accuracy, timeout, sourceObject, discardLocation, new RoamTrackingConfigCallback() {
  //     @Override
  //     public void onSuccess(TrackingConfig trackingConfig) {
  //       successCallback.invoke(RNRoamUtils.mapForTrackingConfig(trackingConfig));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void getTrackingConfig(final Callback successCallback, final Callback errorCallback){
  //   Roam.getTrackingConfig(new RoamTrackingConfigCallback() {
  //     @Override
  //     public void onSuccess(TrackingConfig trackingConfig) {
  //       successCallback.invoke(RNRoamUtils.mapForTrackingConfig(trackingConfig));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void resetTrackingConfig(final Callback successCallback, final Callback errorCallback){
  //   Roam.resetTrackingConfig(new RoamTrackingConfigCallback() {
  //     @Override
  //     public void onSuccess(TrackingConfig trackingConfig) {
  //       successCallback.invoke(RNRoamUtils.mapForTrackingConfig(trackingConfig));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }



  // -------- TRIPS V2 --------------

  // @ReactMethod
  // public void createTrip(final ReadableMap roamTrip, final Callback successCallback, final Callback errorCallback){
  //   Roam.createTrip(RNRoamUtils.decodeRoamTrip(roamTrip), new RoamTripCallback() {
  //     @Override
  //     public void onSuccess(RoamTripResponse roamTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamTripResponse(roamTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void startQuickTrip(final ReadableMap roamTrip, final String trackingMode, final ReadableMap customTrackingOption, final Callback successCallback, final Callback errorCallback){
  //   RoamTrackingMode roamTrackingMode;
  //   if (customTrackingOption == null){
  //     switch (trackingMode){
  //       case "ACTIVE": roamTrackingMode = RoamTrackingMode.ACTIVE;
  //       break;

  //       case "BALANCED": roamTrackingMode = RoamTrackingMode.BALANCED;
  //       break;

  //       default: roamTrackingMode = RoamTrackingMode.PASSIVE;
  //     }
  //   } else {
  //     String desiredAccuracyString = customTrackingOption.getString("desiredAccuracy");
  //     RoamTrackingMode.DesiredAccuracy desiredAccuracy;
  //     if (desiredAccuracyString == null){
  //       desiredAccuracy = RoamTrackingMode.DesiredAccuracy.HIGH;
  //     } else {
  //       switch (desiredAccuracyString){
  //         case "MEDIUM": desiredAccuracy = RoamTrackingMode.DesiredAccuracy.MEDIUM;
  //         break;

  //         case "LOW": desiredAccuracy = RoamTrackingMode.DesiredAccuracy.LOW;
  //         break;

  //         default: desiredAccuracy = RoamTrackingMode.DesiredAccuracy.HIGH;
  //       }
  //     }
  //     int updateInterval = customTrackingOption.getInt("updateInterval");
  //     int distanceFilter = customTrackingOption.getInt("distanceFilter");
  //     int stopDuration = customTrackingOption.getInt("stopDuration");
  //     if (updateInterval != 0){
  //       roamTrackingMode = new RoamTrackingMode.Builder(updateInterval)
  //               .setDesiredAccuracy(desiredAccuracy)
  //               .build();
  //     } else {
  //       roamTrackingMode = new RoamTrackingMode.Builder(distanceFilter, stopDuration)
  //               .setDesiredAccuracy(desiredAccuracy)
  //               .build();
  //     }
  //   }
  //   Roam.startTrip(RNRoamUtils.decodeRoamTrip(roamTrip), roamTrackingMode, new RoamTripCallback() {
  //     @Override
  //     public void onSuccess(RoamTripResponse roamTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamTripResponse(roamTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void startTrip(final String tripId, final Callback successCallback, final Callback errorCallback){
  //   Roam.startTrip(tripId, new RoamTripCallback() {
  //     @Override
  //     public void onSuccess(RoamTripResponse roamTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamTripResponse(roamTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void updateTrip(final ReadableMap roamTrip, final Callback successCallback, final Callback errorCallback){
  //   RoamTrip roamTrip1 = RNRoamUtils.decodeUpdateRoamTrip(roamTrip);
  //   Log.e("TAG", "updateTrip: " + new Gson().toJson(roamTrip1));
  //   Roam.updateTrip(roamTrip1, new RoamTripCallback() {
  //     @Override
  //     public void onSuccess(RoamTripResponse roamTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamTripResponse(roamTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void endTrip(final String tripId, final boolean forceStopTracking, final Callback successCallback, final Callback errorCallback){
  //   Roam.endTrip(tripId, forceStopTracking, new RoamTripCallback() {
  //     @Override
  //     public void onSuccess(RoamTripResponse roamTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamTripResponse(roamTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void pauseTrip(final String tripId, final Callback successCallback, final Callback errorCallback){
  //   Roam.pauseTrip(tripId, new RoamTripCallback() {
  //     @Override
  //     public void onSuccess(RoamTripResponse roamTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamTripResponse(roamTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void resumeTrip(final String tripId, final Callback successCallback, final Callback errorCallback){
  //   Roam.resumeTrip(tripId, new RoamTripCallback() {
  //     @Override
  //     public void onSuccess(RoamTripResponse roamTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamTripResponse(roamTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void syncTrip(final String tripId, final Callback successCallback, final Callback errorCallback){
  //   Roam.syncTrip(tripId, new RoamSyncTripCallback() {
  //     @Override
  //     public void onSuccess(RoamSyncTripResponse roamSyncTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamSyncTripResponse(roamSyncTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void getTrip(final String tripId, final Callback successCallback, final Callback errorCallback){
  //   Roam.getTrip(tripId, new RoamTripCallback() {
  //     @Override
  //     public void onSuccess(RoamTripResponse roamTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamTripResponse(roamTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void getActiveTrips(final boolean isLocal, final Callback successCallback, final Callback errorCallback){
  //   Roam.getActiveTrips(isLocal, new RoamActiveTripsCallback() {
  //     @Override
  //     public void onSuccess(RoamActiveTripsResponse roamActiveTripsResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForActiveTripsResponse(roamActiveTripsResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void getTripSummary(final String tripId, final Callback successCallback, final Callback errorCallback){
  //   Roam.getTripSummary(tripId, new RoamTripCallback() {
  //     @Override
  //     public void onSuccess(RoamTripResponse roamTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamTripResponse(roamTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void subscribeTripStatus(final String tripId){
  //   Roam.subscribeTrip(tripId);
  // }

  // @ReactMethod
  // public void unSubscribeTripStatus(final String tripId){
  //   if (tripId == null){
  //     Roam.unSubscribeTrip();
  //   } else {
  //     Roam.unSubscribeTrip(tripId);
  //   }
  // }

  // @ReactMethod
  // public void deleteTrip(final String tripId, final Callback successCallback, final Callback errorCallback){
  //   Roam.deleteTrip(tripId, new RoamDeleteTripCallback() {
  //     @Override
  //     public void onSuccess(RoamDeleteTripResponse roamDeleteTripResponse) {
  //       successCallback.invoke(RNRoamUtils.mapForRoamDeleteTripResponse(roamDeleteTripResponse));
  //     }

  //     @Override
  //     public void onError(Error error) {
  //       errorCallback.invoke(RNRoamUtils.mapForTripError(error));
  //     }
  //   });
  // }



  // -------- END -------------


  // @ReactMethod
  // public void logout(final Callback successCallback, final Callback errorCallback) {
  //   Roam.logout(new RoamLogoutCallback() {
  //     @Override
  //     public void onSuccess(String message) {
  //       WritableMap map = Arguments.createMap();
  //       map.putString("message", message);
  //       successCallback.invoke(map);
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }

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

  // @ReactMethod
  // public void setBatchReceiverConfig(String networkState,
  //                                    int batchCount,
  //                                    int batchWindow,
  //                                    final Callback successCallback,
  //                                    final Callback errorCallback){
  //   NetworkState state = NetworkState.BOTH;
  //   switch (networkState){
  //     case "OFFLINE":
  //       state = NetworkState.OFFLINE;
  //       break;
  //     case "ONLINE":
  //       state = NetworkState.ONLINE;
  //       break;
  //   }
  //   Roam.setBatchReceiverConfig(state, batchCount, batchWindow, new RoamBatchReceiverCallback() {
  //     @Override
  //     public void onSuccess(List<BatchReceiverConfig> list) {
  //       WritableArray writableArray = RNRoamUtils.mapForBatchReceiverConfig(list);
  //       successCallback.invoke(writableArray);
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void getBatchReceiverConfig(Callback successCallback, Callback errorCallback){
  //   Roam.getBatchReceiverConfig(new RoamBatchReceiverCallback() {
  //     @Override
  //     public void onSuccess(List<BatchReceiverConfig> list) {
  //       successCallback.invoke(RNRoamUtils.mapForBatchReceiverConfig(list));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }

  // @ReactMethod
  // public void resetBatchReceiverConfig(Callback successCallback, Callback errorCallback){
  //   Roam.resetBatchReceiverConfig(new RoamBatchReceiverCallback() {
  //     @Override
  //     public void onSuccess(List<BatchReceiverConfig> list) {
  //       successCallback.invoke(RNRoamUtils.mapForBatchReceiverConfig(list));
  //     }

  //     @Override
  //     public void onFailure(RoamError roamError) {
  //       errorCallback.invoke(RNRoamUtils.mapForError(roamError));
  //     }
  //   });
  // }


  // @ReactMethod
  // public void offlineLocationTracking(boolean value) {
  //   Roam.offlineLocationTracking(value);
  // }

  // @ReactMethod
  // public void publishAndSave(ReadableMap readableMap) {
  //   RoamPublish.Builder roamPublish = new RoamPublish.Builder();
  //   if (readableMap != null && readableMap.getMap(RNRoamUtils.METADATA) != null) {
  //     try {
  //       ReadableMap rm = readableMap.getMap(RNRoamUtils.METADATA);
  //       JSONObject jsonObject = new JSONObject(rm.toString());
  //       if (jsonObject.getJSONObject("NativeMap").length() > 0) {
  //         roamPublish.metadata(jsonObject.getJSONObject("NativeMap"));
  //       }
  //       roamPublish.metadata(jsonObject.getJSONObject("NativeMap"));
  //     } catch (JSONException e) {
  //     }
  //   }
  //   Roam.publishAndSave(roamPublish.build(), new PublishCallback() {
  //     @Override
  //     public void onSuccess(String s) {

  //     }

  //     @Override
  //     public void onError(RoamError roamError) {

  //     }
  //   });
  // }

  // @ReactMethod
  // public void publishOnly(ReadableArray readableArray, ReadableMap readableMap) {
  //   RoamPublish.Builder roamPublish = new RoamPublish.Builder();
  //   if (readableArray != null && readableArray.size() > 0) {
  //     for (int i = 0; i < readableArray.size(); i++) {
  //       if (readableArray.getString(i).equals(RNRoamUtils.APP_ID)) {
  //         roamPublish.appId();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.USER_ID)) {
  //         roamPublish.userId();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.GEOFENCE_EVENTS)) {
  //         roamPublish.geofenceEvents();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.LOCATION_EVENTS)) {
  //         roamPublish.locationEvents();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.NEARBY_EVENTS)) {
  //         roamPublish.nearbyEvents();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.TRIPS_EVENTS)) {
  //         roamPublish.tripsEvents();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.LOCATION_LISTENER)) {
  //         roamPublish.locationListener();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.EVENT_LISTENER)) {
  //         roamPublish.eventListener();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.ALTITUDE)) {
  //         roamPublish.altitude();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.COURSE)) {
  //         roamPublish.course();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.SPEED)) {
  //         roamPublish.speed();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.HORIZONTAL_ACCURACY)) {
  //         roamPublish.horizontalAccuracy();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.VERTICAL_ACCURACY)) {
  //         roamPublish.verticalAccuracy();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.APP_CONTEXT)) {
  //         roamPublish.appContext();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.ALLOW_MOCKED)) {
  //         roamPublish.allowMocked();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.BATTERY_REMAINING)) {
  //         roamPublish.batteryRemaining();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.BATTERY_SAVER)) {
  //         roamPublish.batterySaver();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.BATTERY_STATUS)) {
  //         roamPublish.batteryStatus();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.ACTIVITY)) {
  //         roamPublish.activity();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.AIRPLANE_MODE)) {
  //         roamPublish.airplaneMode();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.DEVICE_MANUFACTURE)) {
  //         roamPublish.deviceManufacturer();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.DEVICE_MODEL)) {
  //         roamPublish.deviceModel();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.TRACKING_MODE)) {
  //         roamPublish.trackingMode();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.LOCATIONPERMISSION)) {
  //         roamPublish.locationPermission();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.NETWORK_STATUS)) {
  //         roamPublish.networkStatus();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.GPS_STATUS)) {
  //         roamPublish.gpsStatus();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.OS_VERSION)) {
  //         roamPublish.osVersion();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.RECORDERD_AT)) {
  //         roamPublish.recordedAt();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.TZ_OFFSET)) {
  //         roamPublish.tzOffset();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.RECORDERD_AT)) {
  //         roamPublish.recordedAt();
  //         continue;
  //       }
  //       if (readableArray.getString(i).equals(RNRoamUtils.ACTIVITY)) {
  //         roamPublish.activity();
  //       }
  //     }
  //   }
  //   if (readableMap != null && readableMap.getMap(RNRoamUtils.METADATA) != null) {
  //     try {
  //       ReadableMap rm = readableMap.getMap(RNRoamUtils.METADATA);
  //       JSONObject jsonObject = new JSONObject(rm.toString());
  //       if (jsonObject.getJSONObject("NativeMap").length() > 0) {
  //         roamPublish.metadata(jsonObject.getJSONObject("NativeMap"));
  //       }
  //       roamPublish.metadata(jsonObject.getJSONObject("NativeMap"));
  //     } catch (JSONException e) {
  //     }
  //   }
  //   Roam.publishOnly(roamPublish.build(), new PublishCallback() {
  //     @Override
  //     public void onSuccess(String s) {

  //     }

  //     @Override
  //     public void onError(RoamError roamError) {

  //     }
  //   });
  // }



  // @ReactMethod
  // public void stopPublishing() {
  //   Roam.stopPublishing(new PublishCallback() {
  //     @Override
  //     public void onSuccess(String s) {

  //     }

  //     @Override
  //     public void onError(RoamError roamError) {

  //     }
  //   });
  // }

  @ReactMethod
  public void batchProcess(boolean enable, int syncHour) {
    try {

      // Using simplified enableAll() approach for batch publishing configuration
      RoamBatchPublish roamBatchPublish = new RoamBatchPublish.Builder().enableAll().build();
      Roam.setConfig(true, roamBatchPublish);
      Log.d("RNRoam", "batchProcess called with enable: " + enable + ", syncHour: " + syncHour + ", roamBatchPublish: " + roamBatchPublish);

    } catch (Exception e) {
      Log.e("RNRoam", "Error in batchProcess", e);
    }
  }

@ReactMethod
  public void enableAccuracyEngine(int accuracy) {
    Roam.enableAccuracyEngine(accuracy);
  }

  @ReactMethod
  public void disableAccuracyEngine() {
    Roam.disableAccuracyEngine();
  }

  @ReactMethod
  public void startSelfTracking(String trackingMode) {
    switch (trackingMode) {
      case "ACTIVE":
        Roam.startTracking(RoamTrackingMode.ACTIVE, new TrackingCallback() {
          @Override
          public void onSuccess(String s) {

          }

          @Override
          public void onError(RoamError roamError) {

          }
        });
        break;
      case "BALANCED":
        Roam.startTracking(RoamTrackingMode.BALANCED, new TrackingCallback() {
          @Override
          public void onSuccess(String s) {

          }

          @Override
          public void onError(RoamError roamError) {

          }
        });
        break;
      case "PASSIVE":
        Roam.startTracking(RoamTrackingMode.PASSIVE, new TrackingCallback() {
          @Override
          public void onSuccess(String s) {

          }

          @Override
          public void onError(RoamError roamError) {

          }
        });
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
    Roam.startTracking(builder.build(), new TrackingCallback() {
      @Override
      public void onSuccess(String s) {

      }

      @Override
      public void onError(RoamError roamError) {

      }
    });
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
    Roam.startTracking(builder.build(), new TrackingCallback() {
      @Override
      public void onSuccess(String s) {

      }

      @Override
      public void onError(RoamError roamError) {

      }
    });
  }

  @ReactMethod
  public void stopSelfTracking() {
    Roam.stopTracking(new TrackingCallback() {
      @Override
      public void onSuccess(String s) {

      }

      @Override
      public void onError(RoamError roamError) {

      }
    });
  }

  @ReactMethod
  public void checkActivityPermission(Callback callback){
    callback.invoke(RNRoamUtils.isGranted(Roam.checkActivityPermission()));
  }

  @ReactMethod
  public void requestActivityPermission(){
    Activity activity = getCurrentActivity();
    if (activity != null){
      Roam.requestActivityPermission(activity);
    }
  }


}