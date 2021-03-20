package com.roam.reactnative;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.geospark.lib.models.GeoSparkError;
import com.geospark.lib.models.GeoSparkLocation;
import com.geospark.lib.models.GeoSparkLocationReceived;
import com.geospark.lib.models.TripStatusListener;
import com.geospark.lib.models.createtrip.Coordinates;
import com.geospark.lib.service.GeoSparkReceiver;


public class RNRoamReceiver extends GeoSparkReceiver {
    private ReactNativeHost mReactNativeHost;

    private void invokeSendEvent(ReactContext reactContext, String eventName, Object data) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, data);
    }

    private void sendEvent(final String eventName, final Object data) {
        final ReactInstanceManager reactInstanceManager = mReactNativeHost.getReactInstanceManager();
        ReactContext reactContext = reactInstanceManager.getCurrentReactContext();
        if (reactContext == null) {
            reactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                @Override
                public void onReactContextInitialized(ReactContext reactContext) {
                    invokeSendEvent(reactContext, eventName, data);
                    reactInstanceManager.removeReactInstanceEventListener(this);
                }
            });
            if (!reactInstanceManager.hasStartedCreatingInitialContext()) {
                reactInstanceManager.createReactContextInBackground();
            }
        } else {
            invokeSendEvent(reactContext, eventName, data);
        }
    }

    @Override
    public void onLocationUpdated(Context context, GeoSparkLocation geoSparkLocation) {
        super.onLocationUpdated(context, geoSparkLocation);
        ReactApplication reactApplication = (ReactApplication) context.getApplicationContext();
        mReactNativeHost = reactApplication.getReactNativeHost();
        WritableMap map = Arguments.createMap();
        if (TextUtils.isEmpty(geoSparkLocation.getUserId())) {
            map.putString("userId", " ");
        } else {
            map.putString("userId", geoSparkLocation.getUserId());
        }
        map.putMap("location", RNRoamUtils.mapForLocation(geoSparkLocation.getLocation()));
        if (TextUtils.isEmpty(geoSparkLocation.getActivity())) {
            map.putString("activity", " ");
        } else {
            map.putString("activity", geoSparkLocation.getActivity());
        }
        map.putString("recordedAt", geoSparkLocation.getRecordedAt());
        map.putString("timezone", geoSparkLocation.getTimezoneOffset());
        sendEvent("location", map);
    }

    @Override
    public void onLocationReceived(Context context, GeoSparkLocationReceived geoSparkLocationReceived) {
        super.onLocationReceived(context, geoSparkLocationReceived);
        ReactApplication reactApplication = (ReactApplication) context.getApplicationContext();
        mReactNativeHost = reactApplication.getReactNativeHost();
        WritableMap map = Arguments.createMap();
        if (geoSparkLocationReceived.getUser_id() != null) {
            map.putString("userId", geoSparkLocationReceived.getUser_id());
        }
        if (geoSparkLocationReceived.getLocation_id() != null) {
            map.putString("locationId", geoSparkLocationReceived.getLocation_id());
        }
        if (geoSparkLocationReceived.getActivity() != null) {
            map.putString("activity", geoSparkLocationReceived.getActivity());
        }
        if (geoSparkLocationReceived.getEvent_source() != null) {
            map.putString("eventSource", geoSparkLocationReceived.getEvent_source());
        }
        map.putInt("speed", geoSparkLocationReceived.getSpeed());
        map.putDouble("altitude", geoSparkLocationReceived.getAltitude());
        map.putDouble("horizontalAccuracy", geoSparkLocationReceived.getHorizontal_accuracy());
        map.putDouble("verticalAccuracy", geoSparkLocationReceived.getVertical_accuracy());
        map.putDouble("course", geoSparkLocationReceived.getCourse());
        Coordinates coordinates = geoSparkLocationReceived.getCoordinates();
        if (coordinates != null && coordinates.getCoordinates().size() > 0) {
            map.putDouble("latitude", coordinates.getCoordinates().get(1));
            map.putDouble("longitude", coordinates.getCoordinates().get(0));
            if (coordinates.getType() != null) {
                map.putString("type", coordinates.getType());
            }
        }
        if (geoSparkLocationReceived.getEvent_version() != null) {
            map.putString("eventVersion", geoSparkLocationReceived.getEvent_version());
        }
        if (geoSparkLocationReceived.getRecorded_at() != null) {
            map.putString("recordedAt", geoSparkLocationReceived.getRecorded_at());
        }
        if (geoSparkLocationReceived.getEvent_type() != null) {
            map.putString("eventType", geoSparkLocationReceived.getEvent_type());
        }
        sendEvent("location_received", map);
    }

    @Override
    public void onReceiveTripStatus(Context context, TripStatusListener tripStatusListener) {
        super.onReceiveTripStatus(context, tripStatusListener);
        ReactApplication reactApplication = (ReactApplication) context.getApplicationContext();
        mReactNativeHost = reactApplication.getReactNativeHost();
        WritableMap map = Arguments.createMap();
        map.putString("tripId", tripStatusListener.getTripId());
        map.putDouble("latitude", tripStatusListener.getLatitude());
        map.putDouble("longitude", tripStatusListener.getLongitue());
        map.putString("startedTime", tripStatusListener.getStartedTime());
        map.putDouble("distance", tripStatusListener.getDistance());
        map.putDouble("duration", tripStatusListener.getDuration());
        map.putDouble("pace", tripStatusListener.getPace());
        map.putDouble("speed", tripStatusListener.getSpeed());
        sendEvent("trip_status", map);
    }

    @Override
    public void onError(Context context, GeoSparkError geoSparkError) {
        super.onError(context, geoSparkError);
        ReactApplication reactApplication = (ReactApplication) context.getApplicationContext();
        mReactNativeHost = reactApplication.getReactNativeHost();
        sendEvent("error", RNRoamUtils.mapForError(geoSparkError));
    }
}

