package com.roam.reactnative;

import android.location.Location;
import android.text.TextUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.roam.sdk.models.ActiveTrips;
import com.roam.sdk.models.BatchReceiverConfig;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamLocation;
import com.roam.sdk.models.RoamUser;
import com.roam.sdk.models.TripStatusListener;
import com.roam.sdk.models.createtrip.Coordinates;
import com.roam.sdk.models.createtrip.Destination;
import com.roam.sdk.models.createtrip.Origin;
import com.roam.sdk.models.createtrip.RoamCreateTrip;
import com.roam.sdk.models.tripsummary.RoamTripSummary;
import com.roam.sdk.models.tripsummary.Route;

import java.util.List;


class RNRoamUtils {

    public static final String APP_ID = "APP_ID";
    public static final String USER_ID = "USER_ID";
    public static final String GEOFENCE_EVENTS = "GEOFENCE_EVENTS";
    public static final String LOCATION_EVENTS = "LOCATION_EVENTS";
    public static final String NEARBY_EVENTS = "NEARBY_EVENTS";
    public static final String TRIPS_EVENTS = "TRIPS_EVENTS";
    public static final String LOCATION_LISTENER = "LOCATION_LISTENER";
    public static final String EVENT_LISTENER = "EVENT_LISTENER";
    public static final String ALTITUDE = "ALTITUDE";
    public static final String COURSE = "COURSE";
    public static final String SPEED = "SPEED";
    public static final String VERTICAL_ACCURACY = "VERTICAL_ACCURACY";
    public static final String HORIZONTAL_ACCURACY = "HORIZONTAL_ACCURACY";
    public static final String APP_CONTEXT = "APP_CONTEXT";
    public static final String ALLOW_MOCKED = "ALLOW_MOCKED";
    public static final String BATTERY_REMAINING = "BATTERY_REMAINING";
    public static final String BATTERY_SAVER = "BATTERY_SAVER";
    public static final String BATTERY_STATUS = "BATTERY_STATUS";
    public static final String ACTIVITY = "ACTIVITY";
    public static final String AIRPLANE_MODE = "AIRPLANE_MODE";
    public static final String DEVICE_MANUFACTURE = "DEVICE_MANUFACTURE";
    public static final String DEVICE_MODEL = "DEVICE_MODEL";
    public static final String TRACKING_MODE = "TRACKING_MODE";
    public static final String LOCATIONPERMISSION = "LOCATIONPERMISSION";
    public static final String NETWORK_STATUS = "NETWORK_STATUS";
    public static final String GPS_STATUS = "GPS_STATUS";
    public static final String OS_VERSION = "OS_VERSION";
    public static final String RECORDERD_AT = "RECORDERD_AT";
    public static final String TZ_OFFSET = "TZ_OFFSET";
    public static final String METADATA = "METADATA";


    static String isGranted(boolean hasGranted) {
        if (hasGranted) {
            return "GRANTED";
        }
        return "DENIED";
    }

    static String checkEnabled(boolean hasEnabled) {
        if (hasEnabled) {
            return "ENABLED";
        }
        return "DISABLED";
    }

    static WritableMap mapForUser(RoamUser roamUser) {
        if (roamUser == null) {
            return null;
        }
        WritableMap map = Arguments.createMap();
        map.putString("userId", roamUser.getUserId());
        map.putString("description", roamUser.getDescription());
        map.putBoolean("geofenceEvents", roamUser.getGeofenceEvents());
        map.putBoolean("locationEvents", roamUser.getLocationEvents());
        map.putBoolean("tripsEvents", roamUser.getTripsEvents());
        map.putBoolean("movingGeofenceEvents", roamUser.getMovingGeofenceEvents());
        map.putBoolean("eventListenerStatus", roamUser.getEventListenerStatus());
        map.putBoolean("locationListenerStatus", roamUser.getLocationListenerStatus());
        return map;
    }

    static WritableMap mapForTrip(RoamTripSummary roamTripSummary) {
        if (roamTripSummary == null) {
            return null;
        }
        WritableMap map = Arguments.createMap();
        map.putDouble("distance", roamTripSummary.getDistance_covered());
        map.putDouble("duration", roamTripSummary.getDuration());
        map.putDouble("elevationGain", roamTripSummary.getTotal_elevation_gain());
        if (roamTripSummary.getRoute() != null && roamTripSummary.getRoute().size() > 0) {
            WritableArray routeArray = Arguments.createArray();
            for (int i = 0; i < roamTripSummary.getRoute().size(); i++) {
                WritableMap routeMap = Arguments.createMap();
                Route route = roamTripSummary.getRoute().get(i);
                if (route.getRecorded_at() != null) {
                    routeMap.putString("recordedAt", route.getRecorded_at());
                }
                if (route.getActivity() != null) {
                    routeMap.putString("activity", route.getActivity());
                }
                if (route.getAltitude() != 0) {
                    routeMap.putDouble("altitude", route.getAltitude());
                }
                if (route.getDistance() != 0) {
                    routeMap.putDouble("distance", route.getDistance());
                }
                if (route.getDuration() != 0) {
                    routeMap.putDouble("duration", route.getDuration());
                }
                if (route.getElevation_gain() != 0) {
                    routeMap.putDouble("elevationGain", route.getElevation_gain());
                }
                Coordinates coordinates = route.getCoordinates();
                if (coordinates != null && coordinates.getCoordinates().size() > 0) {
                    routeMap.putDouble("latitude", coordinates.getCoordinates().get(1));
                    routeMap.putDouble("longitude", coordinates.getCoordinates().get(0));
                }
                routeArray.pushMap(routeMap);
            }
            map.putArray("route",routeArray);
        }
        return map;
    }

    static WritableMap mapForCreateTrip(RoamCreateTrip roamCreateTrip) {
        if (roamCreateTrip == null) {
            return null;
        }
        WritableMap map = Arguments.createMap();
        if (roamCreateTrip.getId() != null) {
            map.putString("id", roamCreateTrip.getId());
        }
        if (roamCreateTrip.getUser_id() != null) {
            map.putString("userId", roamCreateTrip.getUser_id());
        }
        if (roamCreateTrip.getCreated_at() != null) {
            map.putString("createdAt", roamCreateTrip.getCreated_at());
        }
        if (roamCreateTrip.getUpdated_at() != null) {
            map.putString("updatedAt", roamCreateTrip.getUpdated_at());
        }
        if (roamCreateTrip.getIs_started() != null) {
            map.putBoolean("isStarted", roamCreateTrip.getIs_started());
        }
        if (roamCreateTrip.getIs_paused() != null) {
            map.putBoolean("isPaused", roamCreateTrip.getIs_paused());
        }
        if (roamCreateTrip.getIs_ended() != null) {
            map.putBoolean("isEnded", roamCreateTrip.getIs_ended());
        }
        if (roamCreateTrip.getIs_deleted() != null) {
            map.putBoolean("isDeleted", roamCreateTrip.getIs_deleted());
        }
        if (roamCreateTrip.getTrip_tracking_url() != null) {
            map.putString("tripTrackingUrl", roamCreateTrip.getTrip_tracking_url());
        }
        if (roamCreateTrip.getOrigins() != null && roamCreateTrip.getOrigins().size() > 0) {
            WritableMap originMap = Arguments.createMap();
            for (int i = 0; i < roamCreateTrip.getOrigins().size(); i++) {
                Origin origin = roamCreateTrip.getOrigins().get(i);
                if (origin.getId() != null) {
                    map.putString("id", origin.getId());
                }
                if (origin.getTrip_id() != null) {
                    map.putString("tripId", origin.getTrip_id());
                }
                if (origin.getCreated_at() != null) {
                    map.putString("createdAt", origin.getCreated_at());
                }
                if (origin.getUpdated_at() != null) {
                    map.putString("updatedAt", origin.getUpdated_at());
                }
                if (origin.getLoc_type() != null) {
                    map.putString("getLocType", origin.getLoc_type());
                }
                if (origin.getReached() != null) {
                    map.putBoolean("isPaused", origin.getReached());
                }
                Coordinates coordinates = origin.getCoordinates();
                if (coordinates != null && coordinates.getCoordinates().size() > 0) {
                    map.putDouble("latitude", coordinates.getCoordinates().get(1));
                    map.putDouble("longitude", coordinates.getCoordinates().get(0));
                    if (coordinates.getType() != null) {
                        map.putString("type", coordinates.getType());
                    }
                }
            }
            map.putMap("origin", originMap);
        }
        if (roamCreateTrip.getDestinations() != null && roamCreateTrip.getDestinations().size() > 0) {
            WritableMap destinationMap = Arguments.createMap();
            for (int i = 0; i < roamCreateTrip.getDestinations().size(); i++) {
                Destination destination = roamCreateTrip.getDestinations().get(i);
                if (destination.getId() != null) {
                    map.putString("id", destination.getId());
                }
                if (destination.getTrip_id() != null) {
                    map.putString("tripId", destination.getTrip_id());
                }
                if (destination.getCreated_at() != null) {
                    map.putString("createdAt", destination.getCreated_at());
                }
                if (destination.getUpdated_at() != null) {
                    map.putString("updatedAt", destination.getUpdated_at());
                }
                if (destination.getLoc_type() != null) {
                    map.putString("getLocType", destination.getLoc_type());
                }
                if (destination.getReached() != null) {
                    map.putBoolean("isPaused", destination.getReached());
                }
                Coordinates coordinates = destination.getCoordinates();
                if (coordinates != null && coordinates.getCoordinates().size() > 0) {
                    map.putDouble("latitude", coordinates.getCoordinates().get(1));
                    map.putDouble("longitude", coordinates.getCoordinates().get(0));
                    if (coordinates.getType() != null) {
                        map.putString("type", coordinates.getType());
                    }
                }
            }
            map.putMap("destination", destinationMap);
        }
        return map;
    }

    static WritableMap mapForTripList(List<ActiveTrips> trips) {
        if (trips == null && trips.size() == 0) {
            return null;
        }
        WritableMap map = Arguments.createMap();
        for (int i = 0; i < trips.size(); i++) {
            WritableMap mapData = Arguments.createMap();
            ActiveTrips roamActiveTrips = trips.get(i);
            mapData.putString("tripId", roamActiveTrips.getTripId());
            mapData.putBoolean("isStarted", roamActiveTrips.isStarted());
            mapData.putBoolean("isPaused", roamActiveTrips.isPaused());
            mapData.putBoolean("isEnded", roamActiveTrips.getEnded());
            mapData.putBoolean("isDeleted", roamActiveTrips.getDeleted());
            mapData.putString("createdAt", roamActiveTrips.getCreatedAt());
            mapData.putString("updatedAt", roamActiveTrips.getUpdatedAt());
            mapData.putString("syncStatus", roamActiveTrips.getSyncStatus());
            map.putMap(String.valueOf(i), mapData);
        }
        return map;
    }

    static WritableArray mapForBatchReceiverConfig(List<BatchReceiverConfig> configs){
        WritableArray array = Arguments.createArray();
        for (BatchReceiverConfig config: configs){
            WritableMap map = Arguments.createMap();
            map.putInt("batchCount", config.getBatchCount());
            map.putInt("batchWindow", config.getBatchWindow().intValue());
            map.putString("networkState", config.getNetworkState());
            array.pushMap(map);
        }
        return array;
    }

    static WritableArray mapForLocationList(List<RoamLocation> locationList){
        WritableArray array = Arguments.createArray();
        for (RoamLocation roamLocation: locationList){
            WritableMap map = Arguments.createMap();
            if (TextUtils.isEmpty(roamLocation.getUserId())) {
                map.putString("userId", " ");
            } else {
                map.putString("userId", roamLocation.getUserId());
            }
            map.putMap("location", RNRoamUtils.mapForLocation(roamLocation.getLocation()));
            if (TextUtils.isEmpty(roamLocation.getActivity())) {
                map.putString("activity", " ");
            } else {
                map.putString("activity", roamLocation.getActivity());
            }
            map.putString("recordedAt", roamLocation.getRecordedAt());
            map.putString("timezone", roamLocation.getTimezoneOffset());
            array.pushMap(map);
        }
        return array;
    }
    static WritableArray mapForTripStatusListnerList(List<TripStatusListener> tripStatusListener){
        WritableArray array = Arguments.createArray();
        for (TripStatusListener tripStatus: tripStatusListener){
            WritableMap map = Arguments.createMap();
            map.putString("tripId", tripStatus.getTripId());
            map.putDouble("latitude", tripStatus.getLatitude());
            map.putDouble("longitude", tripStatus.getLongitue());
            map.putString("startedTime", tripStatus.getStartedTime());
            map.putDouble("distance", tripStatus.getDistance());
            map.putDouble("duration", tripStatus.getDuration());
            map.putDouble("pace", tripStatus.getPace());
            map.putDouble("speed", tripStatus.getSpeed());
            map.putDouble("altitude", tripStatus.getAltitude());
            map.putDouble("elevationGain", tripStatus.getElevationGain());
            map.putDouble("totalElevationGain", tripStatus.getTotalElevation());
            map.putString("recordedAt", tripStatus.getRecordedAt());
            array.pushMap(map);
        }
        return array;
    }

    static WritableMap mapForLocation(Location location) {
        if (location == null) {
            return null;
        }
        WritableMap map = Arguments.createMap();
        map.putDouble("latitude", location.getLatitude());
        map.putDouble("longitude", location.getLongitude());
        map.putDouble("accuracy", location.getAccuracy());
        map.putDouble("altitude", location.getAltitude());
        map.putDouble("speed", location.getSpeed());
        return map;
    }

    static WritableMap mapForError(RoamError roamError) {
        WritableMap map = Arguments.createMap();
        map.putString("code", roamError.getCode());
        map.putString("message", roamError.getMessage());
        return map;
    }
}