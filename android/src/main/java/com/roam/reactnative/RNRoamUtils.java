package com.roam.reactnative;

import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.roam.sdk.models.BatchLocation;
import com.roam.sdk.models.BatchReceiverConfig;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamLocation;
import com.roam.sdk.models.RoamTripStatus;
import com.roam.sdk.models.RoamUser;
import com.roam.sdk.models.TrackingConfig;
import com.roam.sdk.models.centroid.Centroid;
import com.roam.sdk.models.centroid.CentroidCoordinate;
import com.roam.sdk.models.centroid.Positions;
import com.roam.sdk.models.createtrip.Coordinates;
import com.roam.sdk.models.events.RoamEvent;
import com.roam.sdk.trips_v2.RoamTrip;
import com.roam.sdk.trips_v2.models.EndLocation;
import com.roam.sdk.trips_v2.models.Error;
import com.roam.sdk.trips_v2.models.Errors;
import com.roam.sdk.trips_v2.models.Events;
import com.roam.sdk.trips_v2.models.Geometry;
import com.roam.sdk.trips_v2.models.RoamActiveTripsResponse;
import com.roam.sdk.trips_v2.models.RoamDeleteTripResponse;
import com.roam.sdk.trips_v2.models.RoamSyncTripResponse;
import com.roam.sdk.trips_v2.models.RoamTripResponse;
import com.roam.sdk.trips_v2.models.Routes;
import com.roam.sdk.trips_v2.models.StartLocation;
import com.roam.sdk.trips_v2.models.Stop;
import com.roam.sdk.trips_v2.models.TripDetails;
import com.roam.sdk.trips_v2.models.Trips;
import com.roam.sdk.trips_v2.models.User;
import com.roam.sdk.trips_v2.request.RoamTripStops;

import org.json.JSONObject;

import java.util.ArrayList;
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
        if (roamUser.getGeofenceEvents() != null){
            map.putBoolean("geofenceEvents", roamUser.getGeofenceEvents());
        }
        if (roamUser.getLocationEvents() != null){
            map.putBoolean("locationEvents", roamUser.getLocationEvents());
        }
        if (roamUser.getTripsEvents() != null){
            map.putBoolean("tripsEvents", roamUser.getTripsEvents());
        }
        if (roamUser.getMovingGeofenceEvents() != null){
            map.putBoolean("movingGeofenceEvents", roamUser.getMovingGeofenceEvents());
        }
        if (roamUser.getEventListenerStatus() != null){
            map.putBoolean("eventListenerStatus", roamUser.getEventListenerStatus());
        }
        if (roamUser.getLocationListenerStatus() != null){
            map.putBoolean("locationListenerStatus", roamUser.getLocationListenerStatus());
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

static WritableArray mapForLocationList(List<RoamLocation> locationList) {
    WritableArray array = Arguments.createArray();
    
    for (RoamLocation roamLocation : locationList) {
        WritableMap map = Arguments.createMap();
        map.putString("userId", TextUtils.isEmpty(roamLocation.getUserId()) ? " " : roamLocation.getUserId());
        
        if (locationList.size() > 1) {
            map.putMap("location", RNRoamUtils.mapForBatchLocation(roamLocation.getBatchLocations()));
        } else {
            map.putMap("location", RNRoamUtils.mapForLocation(roamLocation.getLocation()));
        }

        map.putString("activity", TextUtils.isEmpty(roamLocation.getActivity()) ? " " : roamLocation.getActivity());

        WritableArray installedApplicationsArray = Arguments.createArray();
        for (String app : roamLocation.getInstalledApplications()) {
            installedApplicationsArray.pushString(app);
        }
        
        map.putArray("installedApplications", installedApplicationsArray);
        map.putString("recordedAt", roamLocation.getRecordedAt());
        map.putString("timezone", roamLocation.getTimezoneOffset());
        map.putString("trackingMode", roamLocation.getTrackingMode());
        map.putString("appContext", roamLocation.getAppContext());
        map.putString("batteryStatus", roamLocation.getBatteryStatus());
        map.putInt("batteryRemaining", roamLocation.getBatteryRemaining());
        map.putBoolean("batterySaver", roamLocation.getBatterySaver());
        map.putBoolean("networkStatus", roamLocation.getNetworkStatus());
        map.putString("networkState", roamLocation.getNetworkState());
        map.putBoolean("locationPermission", roamLocation.getLocationPermission());
        map.putString("deviceModel", roamLocation.getDeviceModel());
        map.putString("networkType", roamLocation.getNetworkType());
        map.putString("buildID", roamLocation.getBuildId());
        map.putString("kernelVersion", roamLocation.getKernelVersion());
        map.putString("ipAddress", roamLocation.getIpAddress());
        map.putString("publicIpAddress", roamLocation.getPublicIpAddress());
        map.putString("deviceName", roamLocation.getDeviceName());
        map.putString("manufacturer", roamLocation.getManufacturer());
        map.putInt("androidSdkVersion", roamLocation.getAndroidSdkVersion());
        map.putString("androidReleaseVersion", roamLocation.getAndroidReleaseVersion());
        map.putString("buildType", roamLocation.getBuildType());
        map.putString("buildVersionIncremental", roamLocation.getBuildVersionIncremental());
        map.putString("aaid", roamLocation.getAaid());
        map.putString("wifiSSID", roamLocation.getWifiSsid());
        map.putString("localeCountry", roamLocation.getLocaleCountry());
        map.putString("localeLanguage", roamLocation.getLocaleLanguage());
        map.putString("carrierName", roamLocation.getCarrierName());
        map.putString("appInstallationDate", roamLocation.getAppInstallationDate());
        map.putString("appVersion", roamLocation.getAppVersion());
        map.putString("testCentroid", roamLocation.getTestCentroid());
        map.putString("appName", roamLocation.getAppName());
        map.putString("systemName", roamLocation.getSystemName());
        map.putString("sdkVersion", roamLocation.getSdkVersion());
        map.putString("locationId", roamLocation.getLocationId());
        map.putString("appId", roamLocation.getAppId());
        map.putInt("androidSdkVersion", roamLocation.getAndroidSdkVersion());
        map.putString("androidReleaseVersion", roamLocation.getAndroidReleaseVersion());
        map.putString("buildVersionIncremental", roamLocation.getBuildVersionIncremental());
        map.putString("packageName", roamLocation.getPackageName());
        array.pushMap(map);
    }
    return array;
}

static WritableMap mapForCentroid(Centroid centroid) {
       System.out.println("Centroid..."+centroid);
     if (centroid == null) {
         return null;
     }

     WritableMap map = Arguments.createMap();
    CentroidCoordinate centroidCoordinate = centroid.getCentroid();
    if (centroidCoordinate != null) {
        WritableMap centroidCoordinateMap = Arguments.createMap();
        centroidCoordinateMap.putDouble("latitude", centroidCoordinate.getLatitude());
        centroidCoordinateMap.putDouble("longitude", centroidCoordinate.getLongitude());
        map.putMap("centroidCoordinate", centroidCoordinateMap);
    }

    List<Positions> positions = centroid.getPositions();
    if (positions != null) {
        WritableArray positionsArray = Arguments.createArray();
        for (int i = 0; i < positions.size(); i++) {
            Positions position = positions.get(i);
            if (position != null) {
                WritableMap positionMap = Arguments.createMap();
                positionMap.putDouble("latitude", position.getLatitude());
                positionMap.putDouble("longitude", position.getLongitude());
                positionsArray.pushMap(positionMap);
            }
        }
        map.putArray("positions", positionsArray);
    }
    return map;
}




    static WritableArray mapForTripStatusListener(List<RoamTripStatus> list){
        WritableArray array = Arguments.createArray();
        for (RoamTripStatus roamTripStatus: list) {
            WritableMap map = Arguments.createMap();
            map.putString("tripId", roamTripStatus.getTripId());
            map.putDouble("latitude", roamTripStatus.getLatitude());
            map.putDouble("longitude", roamTripStatus.getLongitude());
            map.putInt("speed", roamTripStatus.getSpeed());
            map.putDouble("distance", roamTripStatus.getDistance());
            map.putDouble("duration", roamTripStatus.getDuration());
            map.putDouble("pace", roamTripStatus.getPace());
            map.putDouble("totalElevation", roamTripStatus.getTotalElevation());
            map.putDouble("elevationGain", roamTripStatus.getElevationGain());
            map.putDouble("altitude", roamTripStatus.getAltitude());
            map.putString("startedTime", roamTripStatus.getStartedTime());
            map.putString("state", roamTripStatus.getState());
            array.pushMap(map);
        }
        return array;
    }

    static WritableMap mapForTrackingConfig(TrackingConfig config){
        WritableMap writableMap = Arguments.createMap();
        writableMap.putInt("accuracy", config.getAccuracy());
        writableMap.putInt("timeout", config.getTimeout());
        writableMap.putString("source", config.getSource());
        writableMap.putBoolean("discardLocation", config.getDiscardLocation());
        return writableMap;
    }

    static WritableMap mapForRoamEvent(RoamEvent roamEvent){
        if (roamEvent == null) return null;

        WritableMap map = Arguments.createMap();
        map.putString("tripId", roamEvent.getTrip_id());
        map.putString("nearbyUserId", roamEvent.getNearby_user_id());
        map.putString("userId", roamEvent.getUser_id());
        map.putString("geofenceId", roamEvent.getGeofence_id());
        map.putString("locationId", roamEvent.getLocation_id());
        map.putString("activity", roamEvent.getActivity());
        map.putDouble("distance", roamEvent.getDistance());
        if (roamEvent.getLocation().getCoordinates().size() == 2){
            map.putDouble("longitude", roamEvent.getLocation().getCoordinates().get(0));
            map.putDouble("latitude", roamEvent.getLocation().getCoordinates().get(1));
            map.putString("type", roamEvent.getLocation().getType());
        }
        map.putDouble("horizontalAccuracy", roamEvent.getHorizontal_accuracy());
        map.putDouble("veritcalAccuracy", roamEvent.getVertical_accuracy());
        map.putInt("speed", roamEvent.getSpeed());
        map.putDouble("course", roamEvent.getCourse());
        map.putString("createdAt", roamEvent.getCreated_at());
        map.putString("recordedAt", roamEvent.getRecorded_at());
        map.putString("eventSource", roamEvent.getEvent_source());
        map.putString("eventVersion", roamEvent.getEvent_version());
        map.putString("eventDescription", roamEvent.getDescription());
        map.putString("eventType", roamEvent.getEvent_type());

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
        map.putDouble("verticalAccuracy", location.getVerticalAccuracyMeters());
        map.putDouble("course", location.getBearing());
        return map;
    }

    static WritableMap mapForBatchLocation(BatchLocation location) {
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

    static WritableMap mapForRoamTripResponse(RoamTripResponse roamTripResponse){
        if (roamTripResponse == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("code", roamTripResponse.getCode().toString());
        map.putString("message", roamTripResponse.getMessage());
        map.putString("description", roamTripResponse.getDescription());
        map.putMap("trip", mapForTripDetails(roamTripResponse.getTripDetails()));
        return map;
    }

    static WritableMap mapForTripDetails(TripDetails tripDetails){
        if (tripDetails == null) return null;
        WritableMap trip = Arguments.createMap();
        trip.putString("tripId", tripDetails.getTripId());
        trip.putString("name", tripDetails.getTripName());
        trip.putString("tripDescription", tripDetails.getTripDescription());
        trip.putString("tripState", tripDetails.getTripState());
        trip.putDouble("totalDistance", tripDetails.getTotalDistance());
        trip.putDouble("totalDuration", tripDetails.getTotalDuration());
        trip.putDouble("totalElevationGain", tripDetails.getTotalElevationGain());
        trip.putString("metadata", tripDetails.getMetadata() != null ? tripDetails.getMetadata().toString() : null);
        trip.putMap("startLocation", mapForTripLocation(tripDetails.getStartLocation()));
        trip.putMap("endLocation", mapForTripLocation(tripDetails.getEndLocation()));
        trip.putMap("user", mapForTripUser(tripDetails.getUser()));
        trip.putString("startedAt", tripDetails.getStartedAt());
        trip.putString("endedAt", tripDetails.getEndedAt());
        trip.putString("createdAt", tripDetails.getCreatedAt());
        trip.putString("updatedAt", tripDetails.getUpdatedAt());
        trip.putBoolean("isLocal", tripDetails.getIsLocal());
        trip.putBoolean("hasMore", tripDetails.getHasMore());
        if (tripDetails.getStops() != null){
            WritableArray stopsArray = Arguments.createArray();
            for (Stop stop: tripDetails.getStops()){
                stopsArray.pushMap(mapForStop(stop));
            }
            trip.putArray("stops", stopsArray);
        } else {
            trip.putArray("stops", null);
        }
        if (tripDetails.getEvents() != null){
            WritableArray eventsArray = Arguments.createArray();
            for (Events events: tripDetails.getEvents()){
                eventsArray.pushMap(mapForEvent(events));
            }
            trip.putArray("events", eventsArray);
        } else {
            trip.putArray("events", null);
        }
        if (tripDetails.getRoutes() != null){
            WritableArray routesArray = Arguments.createArray();
            for (Routes routes: tripDetails.getRoutes()){
                routesArray.pushMap(mapForRoutes(routes));
            }
            trip.putArray("routes", routesArray);
        } else {
            trip.putArray("routes", null);
        }
        trip.putString("routeIndex", tripDetails.getRouteIndex() != null ? tripDetails.getRouteIndex().toString() : null);
        if (tripDetails.getLocationCount() != null) {
            trip.putInt("locationCount", tripDetails.getLocationCount());
        }
        return trip;
    }

    static WritableMap mapForTripLocation(StartLocation startLocation){
        if (startLocation == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("id", startLocation.getId());
        map.putString("name", startLocation.getName());
        map.putString("description", startLocation.getDescription());
        map.putString("address", startLocation.getAddress());
        map.putString("metadata", startLocation.getMetadata() != null ? startLocation.getMetadata().toString() : null);
        map.putString("recordedAt", startLocation.getRecordedAt());
        map.putMap("geometry", mapForGeometry(startLocation.getGeometry()));
        return map;
    }

    static WritableMap mapForTripLocation(EndLocation endLocation){
        if (endLocation == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("id", endLocation.getId());
        map.putString("name", endLocation.getName());
        map.putString("description", endLocation.getDescription());
        map.putString("address", endLocation.getAddress());
        map.putString("metadata", endLocation.getMetadata() != null ? endLocation.getMetadata().toString() : null);
        map.putString("recordedAt", endLocation.getRecordedAt());
        map.putMap("geometry", mapForGeometry(endLocation.getGeometry()));
        return map;
    }

    static WritableMap mapForGeometry(Geometry geometry){
        if (geometry == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("type", geometry.getType());
        WritableArray coordinates = Arguments.createArray();
        for(Double value: geometry.getCoordinates()){
            coordinates.pushDouble(value);
        }
        map.putArray("coordinates", coordinates);
        return map;
    }

    static WritableMap mapForCoordinates(Coordinates coordinates){
        if (coordinates == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("type", coordinates.getType());
        WritableArray coordinatesArray = Arguments.createArray();
        for(Double value: coordinates.getCoordinates()){
            coordinatesArray.pushDouble(value);
        }
        map.putArray("coordinates", coordinatesArray);
        return map;
    }

    static WritableMap mapForTripUser(User user){
        if (user == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("name", user.getName());
        map.putString("description", user.getDescription());
        map.putString("metadata", user.getMetadata() != null ? user.getMetadata().toString() : null);
        map.putString("id", user.getId());
        return map;
    }

    static WritableMap mapForStop(Stop stop){
        if (stop == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("id", stop.getId());
        map.putString("name", stop.getStopName());
        map.putString("description", stop.getStopDescription());
        map.putString("address", stop.getAddress());
        map.putString("metadata", stop.getMetadata() != null ? stop.getMetadata().toString() : null);
        map.putDouble("geometryRadius", stop.getGeometryRadius());
        map.putString("createdAt", stop.getCreatedAt());
        map.putString("updatedAt", stop.getUpdatedAt());
        map.putString("arrivedAt", stop.getArrivedAt());
        map.putString("departedAt", stop.getDepartedAt());
        map.putMap("geometry", mapForGeometry(stop.getGeometry()));
        return map;
    }

    static WritableMap mapForEvent(Events events){
        if (events == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("id", events.getId());
        map.putString("tripId", events.getTripId());
        map.putString("userId", events.getUserId());
        map.putString("eventType", events.getEventType());
        map.putString("createAt", events.getCreatedAt());
        map.putString("eventSource", events.getEventSource());
        map.putString("eventVersion", events.getEventVersion());
        map.putString("locationId", events.getLocationId());
        return map;
    }

    static WritableMap mapForRoutes(Routes routes){
        if (routes == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("metadata", routes.getMetadata() != null ? routes.getMetadata().toString() : null);
        map.putString("activity", routes.getActivity());
        map.putDouble("speed", routes.getSpeed());
        map.putDouble("altitude", routes.getAltitude());
        map.putDouble("distance", routes.getDistance());
        map.putDouble("duration", routes.getDuration());
        map.putDouble("elevationGain", routes.getElevationGain());
        map.putMap("coordinates", mapForCoordinates(routes.getCoordinates()));
        map.putString("recordedAt", routes.getRecordedAt());
        map.putString("locationId", routes.getLocationId());
        map.putDouble("bearing", routes.getBearing());
        return map;
    }

    static WritableMap mapForTripError(Error error){
        if (error == null) return null;
        WritableMap map = Arguments.createMap();
        if (error.getErrors() != null){
            WritableArray errorsArray = Arguments.createArray();
            for (Errors errors: error.getErrors()){
                WritableMap errorsMap = Arguments.createMap();
                errorsMap.putString("field", errors.getField());
                errorsMap.putString("message", errors.getMessage());
                errorsArray.pushMap(errorsMap);
            }
            map.putArray("errors", errorsArray);
        } else {
            map.putArray("errors", null);
        }
        map.putInt("code", error.getErrorCode());
        map.putString("message", error.getErrorMessage());
        map.putString("description", error.getErrorDescription());
        return map;
    }

    static WritableMap mapForRoamSyncTripResponse(RoamSyncTripResponse roamSyncTripResponse){
        if (roamSyncTripResponse == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("msg", roamSyncTripResponse.getMessage());
        map.putString("description", roamSyncTripResponse.getDescription());
        map.putInt("code", roamSyncTripResponse.getCode());
        WritableMap data = Arguments.createMap();
        if (roamSyncTripResponse.getData() != null) {
            data.putString("tripId", roamSyncTripResponse.getData().getTrip_id());
            if (roamSyncTripResponse.getData().getIsSynced() != null){
                data.putBoolean("isSynced", roamSyncTripResponse.getData().getIsSynced());
            }
        }
        map.putMap("data", data);
        return map;
    }

    static WritableMap mapForActiveTripsResponse(RoamActiveTripsResponse roamActiveTripsResponse){
        if (roamActiveTripsResponse == null) return null;
        WritableMap map = Arguments.createMap();
        map.putInt("code", roamActiveTripsResponse.getCode());
        map.putString("message", roamActiveTripsResponse.getMessage());
        map.putString("description", roamActiveTripsResponse.getDescription());
        map.putBoolean("hasMore", roamActiveTripsResponse.isHas_more());
        if (roamActiveTripsResponse.getTrips() != null){
            WritableArray tripsArray = Arguments.createArray();
            for(Trips trips: roamActiveTripsResponse.getTrips()){
                tripsArray.pushMap(mapForTrips(trips));
            }
            map.putArray("trips", tripsArray);
        } else {
            map.putArray("trips", null);
        }
        return map;
    }

    static WritableMap mapForTrips(Trips trips){
        if (trips == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("id", trips.getTripId());
        map.putString("tripState", trips.getTripState());
        map.putDouble("totalDistance", trips.getTotalDistance());
        map.putDouble("totalDuration", trips.getTotalDuration());
        map.putDouble("totalElevationGain", trips.getTotalElevationGain());
        map.putString("metadata", trips.metadata() != null ? trips.metadata().toString() : null);
        map.putMap("user", mapForTripUser(trips.getUser()));
        map.putString("startedAt", trips.getStartedAt());
        map.putString("endedAt", trips.getEndedAt());
        map.putString("createdAt", trips.getCreatedAt());
        map.putString("updatedAt", trips.getUpdatedAt());
        if (trips.getStop() != null){
            WritableArray stopsArray = Arguments.createArray();
            for (Stop stop: trips.getStop()){
                stopsArray.pushMap(mapForStop(stop));
            }
            map.putArray("stops", stopsArray);
        } else {
            map.putArray("stops", null);
        }
        if (trips.getEvents() != null){
            WritableArray eventsArray = Arguments.createArray();
            for (Events events: trips.getEvents()){
                eventsArray.pushMap(mapForEvent(events));
            }
            map.putArray("events", eventsArray);
        } else {
            map.putArray("events", null);
        }
        map.putString("syncStatus", trips.getSyncStatus());
        return map;
    }

    static WritableMap mapForRoamDeleteTripResponse(RoamDeleteTripResponse roamDeleteTripResponse){
        if (roamDeleteTripResponse == null) return null;
        WritableMap map = Arguments.createMap();
        map.putString("message", roamDeleteTripResponse.getMessage());
        map.putString("description", roamDeleteTripResponse.getDescription());
        map.putInt("code", roamDeleteTripResponse.getCode());
        WritableMap trip = Arguments.createMap();
        if (roamDeleteTripResponse.getTrip() != null) {
            trip.putString("id", roamDeleteTripResponse.getTrip().getId());
            if (roamDeleteTripResponse.getTrip().getIs_deleted() != null){
                trip.putBoolean("isDeleted", roamDeleteTripResponse.getTrip().getIs_deleted());
            }
        }
        map.putMap("trip", trip);
        return map;
    }



    static RoamTripStops decodeRoamTripsStop(ReadableMap map){
        String id = map.hasKey("RoamTripStop") ? map.getString("RoamTripStop") : null;
        String stopName = map.hasKey("stopName") ? map.getString("stopName") : null;
        String stopDescription = map.hasKey("stopDescription") ? map.getString("stopDescription") : null;
        String address = map.hasKey("address") ? map.getString("address") : null;
        Double geometryRadius = map.hasKey("geometryRadius") ? map.getDouble("geometryRadius") : null;
        ReadableArray geometryCoordinates = map.hasKey("geometryCoordinates") ? map.getArray("geometryCoordinates") : null;
        List<Double> geometryCoordinatesList = new ArrayList<>();
        if (geometryCoordinates != null && geometryCoordinates.size() == 2){
            geometryCoordinatesList.add(geometryCoordinates.getDouble(0));
            geometryCoordinatesList.add(geometryCoordinates.getDouble(1));
        }
        ReadableMap metadata = map.hasKey("metadata") ? map.getMap("metadata") : null;
        RoamTripStops stop = new RoamTripStops();
        if (id != null){
            stop.setStopId(id);
        }
        if (stopName != null){
            stop.setStopName(stopName);
        }
        if (stopDescription != null){
            stop.setStopDescription(stopDescription);
        }
        if (address != null){
            stop.setAddress(address);
        }
        stop.setGeometryRadius(geometryRadius);
        if (geometryCoordinatesList.size() == 2){
            stop.setGeometry(geometryCoordinatesList);
        }
        if (metadata != null){
            JSONObject jsonObject = new JSONObject(metadata.toHashMap());
            stop.setMetadata(jsonObject);
        }
        return stop;
    }

    static RoamTrip decodeRoamTrip(ReadableMap map){
        Log.e("TAG", map.toString());
        try{
            Log.e("TAG", map.getString("tripId"));
        }catch (Exception e){
            e.printStackTrace();
        }
        String tripId = map.hasKey("tripId") ? map.getString("tripId") : null;
        Log.e("TAG", "tripId: " + tripId);
        String tripDescription = map.hasKey("tripDescription") ? map.getString("tripDescription") : null;
        String tripName = map.hasKey("tripName") ? map.getString("tripName") : null;
        ReadableMap metadata = map.hasKey("metadata") ? map.getMap("metadata") : null;
        Boolean isLocal = map.hasKey("isLocal") ? map.getBoolean("isLocal") : null;
        ReadableArray stops = map.hasKey("stops") ? map.getArray("stops") : null;
        String userId = map.hasKey("userId") ? map.getString("userId") : null;
        RoamTrip.Builder roamTripBuilder = new RoamTrip.Builder();
        if (tripId != null){
            Log.e("TAG", "tripId: " + tripId);
            roamTripBuilder.setTripId(tripId);
        }
        if (tripDescription != null){
            roamTripBuilder.setTripDescription(tripDescription);
        }
        if (tripName != null){
            roamTripBuilder.setTripName(tripName);
        }
        if (metadata != null){
            roamTripBuilder.setMetadata(new JSONObject(metadata.toHashMap()));
        }
        if(isLocal != null){
            roamTripBuilder.setIsLocal(isLocal);
        }
        if (stops != null){
            List<RoamTripStops> stopsList = new ArrayList<>();
            for(int i=0; i<stops.size(); i++){
                ReadableMap stopMap = stops.getMap(i);
                stopsList.add(decodeRoamTripsStop(stopMap));
            }
            roamTripBuilder.setStop(stopsList);
        }
        if (userId != null){
            roamTripBuilder.setUserId(userId);
        }
        return roamTripBuilder.build();
    }

    static RoamTrip decodeUpdateRoamTrip(ReadableMap map){
        Log.e("TAG", map.toString());
        try{
            Log.e("TAG", map.getString("tripId"));
        }catch (Exception e){
            e.printStackTrace();
        }
        String tripId = map.hasKey("tripId") ? map.getString("tripId") : null;
        String tripDescription = map.hasKey("tripDescription") ? map.getString("tripDescription") : null;
        String tripName = map.hasKey("tripName") ? map.getString("tripName") : null;
        ReadableMap metadata = map.hasKey("metadata") ? map.getMap("metadata") : null;
        ReadableArray stops = map.hasKey("stops") ? map.getArray("stops") : null;
        String userId = map.hasKey("userId") ? map.getString("userId") : null;
        Boolean isLocal = map.hasKey("isLocal") ? map.getBoolean("isLocal") : null;
        RoamTrip.Builder roamTripBuilder = new RoamTrip.Builder();
        if (tripId != null){
            roamTripBuilder.setTripId(tripId);
        }
        if (tripDescription != null){
            roamTripBuilder.setTripDescription(tripDescription);
        }
        if (tripName != null){
            roamTripBuilder.setTripName(tripName);
        }
        if (metadata != null){
            roamTripBuilder.setMetadata(new JSONObject(metadata.toHashMap()));
        }
        if (stops != null){
            List<RoamTripStops> stopsList = new ArrayList<>();
            for(int i=0; i<stops.size(); i++){
                ReadableMap stopMap = stops.getMap(i);
                stopsList.add(decodeRoamTripsStop(stopMap));
            }
            roamTripBuilder.setStop(stopsList);
        }
        if (userId != null){
            roamTripBuilder.setUserId(userId);
        }
        if (isLocal != null){
            roamTripBuilder.setIsLocal(isLocal);
        }
        return roamTripBuilder.build();
    }

}
