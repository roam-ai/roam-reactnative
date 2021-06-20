package com.roam.reactnative;

import android.location.Location;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.geospark.lib.models.ActiveTrips;
import com.geospark.lib.models.GeoSparkError;
import com.geospark.lib.models.GeoSparkUser;
import com.geospark.lib.models.createtrip.Coordinates;
import com.geospark.lib.models.createtrip.Destination;
import com.geospark.lib.models.createtrip.GeoSparkCreateTrip;
import com.geospark.lib.models.createtrip.Origin;
import com.geospark.lib.models.tripsummary.GeoSparkTripSummary;
import com.geospark.lib.models.tripsummary.Route;

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

    static WritableMap mapForUser(GeoSparkUser geoSparkUser) {
        if (geoSparkUser == null) {
            return null;
        }
        WritableMap map = Arguments.createMap();
        map.putString("userId", geoSparkUser.getUserId());
        map.putString("description", geoSparkUser.getDescription());
        map.putBoolean("geofenceEvents", geoSparkUser.getGeofenceEvents());
        map.putBoolean("locationEvents", geoSparkUser.getLocationEvents());
        map.putBoolean("tripsEvents", geoSparkUser.getTripsEvents());
        map.putBoolean("movingGeofenceEvents", geoSparkUser.getMovingGeofenceEvents());
        map.putBoolean("eventListenerStatus", geoSparkUser.getEventListenerStatus());
        map.putBoolean("locationListenerStatus", geoSparkUser.getLocationListenerStatus());
        return map;
    }

    static WritableMap mapForTrip(GeoSparkTripSummary geoSparkTripSummary) {
        if (geoSparkTripSummary == null) {
            return null;
        }
        WritableMap map = Arguments.createMap();
        map.putDouble("distance", geoSparkTripSummary.getDistance_covered());
        map.putDouble("duration", geoSparkTripSummary.getDuration());
        map.putDouble("elevationGain", geoSparkTripSummary.getTotal_elevation_gain());
        if (geoSparkTripSummary.getRoute() != null && geoSparkTripSummary.getRoute().size() > 0) {
            WritableArray routeArray = Arguments.createArray();
            for (int i = 0; i < geoSparkTripSummary.getRoute().size(); i++) {
                WritableMap routeMap = Arguments.createMap();
                Route route = geoSparkTripSummary.getRoute().get(i);
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

    static WritableMap mapForCreateTrip(GeoSparkCreateTrip geoSparkCreateTrip) {
        if (geoSparkCreateTrip == null) {
            return null;
        }
        WritableMap map = Arguments.createMap();
        if (geoSparkCreateTrip.getId() != null) {
            map.putString("id", geoSparkCreateTrip.getId());
        }
        if (geoSparkCreateTrip.getUser_id() != null) {
            map.putString("userId", geoSparkCreateTrip.getUser_id());
        }
        if (geoSparkCreateTrip.getCreated_at() != null) {
            map.putString("createdAt", geoSparkCreateTrip.getCreated_at());
        }
        if (geoSparkCreateTrip.getUpdated_at() != null) {
            map.putString("updatedAt", geoSparkCreateTrip.getUpdated_at());
        }
        if (geoSparkCreateTrip.getIs_started() != null) {
            map.putBoolean("isStarted", geoSparkCreateTrip.getIs_started());
        }
        if (geoSparkCreateTrip.getIs_paused() != null) {
            map.putBoolean("isPaused", geoSparkCreateTrip.getIs_paused());
        }
        if (geoSparkCreateTrip.getIs_ended() != null) {
            map.putBoolean("isEnded", geoSparkCreateTrip.getIs_ended());
        }
        if (geoSparkCreateTrip.getIs_deleted() != null) {
            map.putBoolean("isDeleted", geoSparkCreateTrip.getIs_deleted());
        }
        if (geoSparkCreateTrip.getTrip_tracking_url() != null) {
            map.putString("tripTrackingUrl", geoSparkCreateTrip.getTrip_tracking_url());
        }
        if (geoSparkCreateTrip.getOrigins() != null && geoSparkCreateTrip.getOrigins().size() > 0) {
            WritableMap originMap = Arguments.createMap();
            for (int i = 0; i < geoSparkCreateTrip.getOrigins().size(); i++) {
                Origin origin = geoSparkCreateTrip.getOrigins().get(i);
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
        if (geoSparkCreateTrip.getDestinations() != null && geoSparkCreateTrip.getDestinations().size() > 0) {
            WritableMap destinationMap = Arguments.createMap();
            for (int i = 0; i < geoSparkCreateTrip.getDestinations().size(); i++) {
                Destination destination = geoSparkCreateTrip.getDestinations().get(i);
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
            ActiveTrips geoSparkActiveTrips = trips.get(i);
            mapData.putString("tripId", geoSparkActiveTrips.getTripId());
            mapData.putBoolean("isStarted", geoSparkActiveTrips.isStarted());
            mapData.putBoolean("isPaused", geoSparkActiveTrips.isPaused());
            mapData.putBoolean("isEnded", geoSparkActiveTrips.getEnded());
            mapData.putBoolean("isDeleted", geoSparkActiveTrips.getDeleted());
            mapData.putString("createdAt", geoSparkActiveTrips.getCreatedAt());
            mapData.putString("updatedAt", geoSparkActiveTrips.getUpdatedAt());
            mapData.putString("syncStatus", geoSparkActiveTrips.getSyncStatus());
            map.putMap(String.valueOf(i), mapData);
        }
        return map;
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

    static WritableMap mapForError(GeoSparkError geoSparkError) {
        WritableMap map = Arguments.createMap();
        map.putString("errorCode", geoSparkError.getCode());
        map.putString("errorMessage", geoSparkError.getMessage());
        return map;
    }
}