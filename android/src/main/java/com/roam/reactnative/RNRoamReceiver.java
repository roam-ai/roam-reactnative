package com.roam.reactnative;


import android.content.Context;
import android.content.Intent;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.roam.sdk.models.NetworkListener;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamLocation;
import com.roam.sdk.models.RoamLocationReceived;
// import com.roam.sdk.models.RoamTripStatus; // Not available in SDK 0.2.0
import com.roam.sdk.models.events.RoamEvent;
import com.roam.sdk.service.RoamReceiver;

import java.util.ArrayList;
import java.util.List;


public class RNRoamReceiver extends RoamReceiver {
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
    public void onLocationUpdated(Context context, RoamLocation roamLocation) {
        super.onLocationUpdated(context, roamLocation);
        ReactApplication reactApplication = (ReactApplication) context.getApplicationContext();
        mReactNativeHost = reactApplication.getReactNativeHost();
        // Create a single-item list for compatibility with mapForLocationList
        List<RoamLocation> locationList = new ArrayList<>();
        locationList.add(roamLocation);
        WritableArray array = RNRoamUtils.mapForLocationList(locationList);
        sendEvent("location", array);
    }

    @Override
    public void onEventReceived(Context context, RoamEvent roamEvent) {
        super.onEventReceived(context, roamEvent);
        ReactApplication reactApplication = (ReactApplication) context.getApplicationContext();
        mReactNativeHost = reactApplication.getReactNativeHost();
        WritableMap map = RNRoamUtils.mapForRoamEvent(roamEvent);
        sendEvent("events", map);
    }

    @Override
    public void onLocationReceived(Context context, RoamLocationReceived roamLocationReceived) {
        super.onLocationReceived(context, roamLocationReceived);
        ReactApplication reactApplication = (ReactApplication) context.getApplicationContext();
        mReactNativeHost = reactApplication.getReactNativeHost();
        WritableMap map = Arguments.createMap();
        if (roamLocationReceived.getUser_id() != null) {
            map.putString("userId", roamLocationReceived.getUser_id());
        }
        if (roamLocationReceived.getLocation_id() != null) {
            map.putString("locationId", roamLocationReceived.getLocation_id());
        }
        if (roamLocationReceived.getActivity() != null) {
            map.putString("activity", roamLocationReceived.getActivity());
        }
        if (roamLocationReceived.getEvent_source() != null) {
            map.putString("eventSource", roamLocationReceived.getEvent_source());
        }
        map.putInt("speed", roamLocationReceived.getSpeed());
        map.putDouble("altitude", roamLocationReceived.getAltitude());
        map.putDouble("horizontalAccuracy", roamLocationReceived.getHorizontal_accuracy());
        map.putDouble("verticalAccuracy", roamLocationReceived.getVertical_accuracy());
        map.putDouble("course", roamLocationReceived.getCourse());
        map.putDouble("latitude", roamLocationReceived.getLatitude());
        map.putDouble("longitude", roamLocationReceived.getLongitude());
        if (roamLocationReceived.getEvent_version() != null) {
            map.putString("eventVersion", roamLocationReceived.getEvent_version());
        }
        if (roamLocationReceived.getRecorded_at() != null) {
            map.putString("recordedAt", roamLocationReceived.getRecorded_at());
        }
        if (roamLocationReceived.getEvent_type() != null) {
            map.putString("eventType", roamLocationReceived.getEvent_type());
        }
        sendEvent("location_received", map);
    }

    // Disabled: RoamTripStatus class not available in SDK 0.2.0
    /*
    @Override
    public void onReceiveTrip(Context context, List<RoamTripStatus> list) {
        super.onReceiveTrip(context, list);
        ReactApplication reactApplication = (ReactApplication) context.getApplicationContext();
        mReactNativeHost = reactApplication.getReactNativeHost();
        sendEvent("trip_status", RNRoamUtils.mapForTripStatusListener(list));
    }
    */

    @Override
    public void onError(Context context, RoamError roamError) {
        super.onError(context, roamError);
        ReactApplication reactApplication = (ReactApplication) context.getApplicationContext();
        mReactNativeHost = reactApplication.getReactNativeHost();
        WritableMap map = Arguments.createMap();
        map.putString("code", roamError.getCode());
        map.putString("message", roamError.getMessage());
        if(roamError.getMessage().equalsIgnoreCase("The GPS is enabled.") || roamError.getMessage().equalsIgnoreCase("The location permission is enabled"))
        {
            map.putBoolean("locationServicesEnabled", true);
            sendEvent("locationAuthorizationChange", map);
        }
        else if (roamError.getMessage().equalsIgnoreCase("The GPS is disabled.") || roamError.getMessage().equalsIgnoreCase("The location permission is disabled"))
        {
            map.putBoolean("locationServicesEnabled", false);
            sendEvent("locationAuthorizationChange", map);
        }
        sendEvent("error", map);
    }

    @Override
    public void onConnectivityChange(Context context, NetworkListener networkListener) {
        super.onConnectivityChange(context, networkListener);
        ReactApplication reactApplication = (ReactApplication) context.getApplicationContext();
        mReactNativeHost = reactApplication.getReactNativeHost();
        WritableMap map = Arguments.createMap();
        map.putString("type", networkListener.getType());
        map.putBoolean("isConnected", networkListener.getIsConnected());
        sendEvent("connectivityChangeEvent", map);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            try{
                Intent headlessServiceIntent = new Intent(context, RNRoamHeadlessService.class);
                context.startService(headlessServiceIntent);
                HeadlessJsTaskService.acquireWakeLockNow(context);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}

