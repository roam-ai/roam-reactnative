## 0.0.1

- A whole new React Native SDK which is based on much more stable GeoSpark native iOS and Android SDKs.

## 0.0.2

- Added support for Typescript

## 0.0.3

- Added support for Get Trip Summary (Android)

## 0.0.4

- Added support for total elevation gain to trip summary along with elevation gain, distance and duration for location date in trip summary. (Android)

## 0.0.5

- Refactored iOS SDK modules to support new Xcode v12.5.1

## 0.0.6

- Updated to latest native Roam SDK versions. Android v0.0.3 and iOS v0.0.7
- Fixed the auto linking for both iOS and Android.
- Updated the Readme.md docs with latest quickstart guides.

## 0.0.7

- Added support in android native SDK(0.0.7) to listen to location updates of user from different projects which are within same account.

## 0.0.8

- Code cleanup in RNRoamReceiver.java file

## 0.0.9

- Added support in android native SDK(0.0.8) to listen to location permission changes

## 0.0.10

- Added support in android native SDK(0.0.9)
- Ability to listen to trip status with distance, duration, elevation gain and total elevation gain
- Ability to create offline trips without user session

## 0.0.11

- Updated android native SDK(0.0.9)

## 0.0.12

- Refactored android native SDK (0.0.9) linking

## 0.0.13

- Added support in android native SDK(0.0.10)
- Fixed create trip when creating offline trip without user session.
- Added support to location permission events in `locationAuthorizationChange`

## 0.0.14

- Updated to latest native Roam SDK versions. Android v0.0.11 and iOS v0.0.13

## 0.0.15

- Updated to latest iOS native SDK version v0.0.14 which improves `the Roam.getCurrentLocation()` to return location faster.

## 0.0.16

- Updated to latest Android native SDK version v0.0.12 with support for controlling the foreground service notification.

## 0.0.17

- Fixed `Roam.setForegroundNotification()` to accept the custom icons.

## 0.0.18

- Updated to latest native Roam SDK versions. Android v0.0.13 and iOS v0.0.15
- Added iOS native bridge for `getTripSummary()` method.

## 0.0.19

- Updated to latest native Roam SDK versions. Android v0.0.15 and iOS v0.0.16
- Added option in `Roam.unSubscribe()` which will now unsubscribe all users if `user_id` is passed as null or empty.
- Added battery and network details as part of location in location receiver.
- Fixed logical error in calculation of elevation gain in trip summary.

## 0.0.20

- Removed the blue bar in iOS which was being displayed during active tracking mode.

## 0.0.21

- Added new method for `Roam.updateLocationWhenStationary(interval)` which updates location on given interval value in seconds.

## 0.0.22

- Updated to latest native Roam SDK versions. Android v0.0.17 and iOS v0.0.17.
- Fixed crashes for `endTrip`, `pauseTrip`, `resumeTrip` and `forceEndTrip` in iOS bridge.

## 0.0.23

- Updated to latest native Roam SDK version. iOS v0.0.18.
- Fixed the coordinates arrangement for `Roam.getTripSummary()` on local trips.

## 0.0.24

- Updated to latest native Roam SDK versions. Android v0.0.19 and iOS v0.0.19.
- Modified updateCurrentLocation method to support metadata as null.
- Updated location noise filter to remove inaccurate locations.
- Added individual distance, duration, and elevation gain for location data inside trip routes for local trips.
- Trip summary response for the local trip will have the route sorted by recorded timestamp.
- Fixed background location tracking for time-based tracking mode when location permission is given as 'Allow while using'

## 0.0.25

- Updated to latest native Roam SDK versions - iOS v0.0.20.
- Fixed calculation for distance and duration for individual location data in trip summary route.

## 0.0.26

Added:

- Added new methods for batch configurations in location receiver.

Modified:

- Location data in location receiver is changed from single location object to list of location updates.

Fixed:

- Trip error changed for few error scenarios to success callbacks.
  - Trip Already Started
  - Trip Already Ended

## 0.0.27

Modified:

- Added callbacks to `Roam.resetBatchReceiverConfig()` method to return default config values.
  Fixed:
- Fixed callback response for `Roam.getBatchReceiverConfig()` and `Roam.setBatchReceiverConfig()` methods.

## 0.0.28

Fixed:

- Fixed the method `Roam.resetBatchReceiverConfig()`.
- Autolinking for iOS.

## 0.0.29

Added:

- New listener for connectity change event with `Roam.startListener('connectivityChangeEvent', connectivityChangeEvent)`
- Updated to latest native Roam SDK versions - Android v0.0.22.

## 0.0.30

Added:

- Fixed location receiver when device restarted (Android).
- Added option to restart Roam Service with `Roam.setForegroundNotification()` method.
- Updated to latest native Roam SDK version - Android v0.0.23.

## 0.0.31

- Configured the SDK to support location receiver after device reboot.

## 0.0.32

Fixed:

- `Roam.enableAccuracyEngine(int)` method to accept integer value.
- Autolinking for iOS.

## 0.0.33

Added:

- Added batch config for "trip_status" listener

## 0.0.34

- Updated to latest native Roam SDK versions. Android v0.0.25 and iOS v0.0.25.
- Added `recorderAt` parameter for trip status listener.
- Added option to unsubscribe from all the trips in the method `Roam.unsubscribeTripStatus()`
- Fixed trip listener that works independently of the location listener in iOS.

## 0.0.35

- Fixed enableAccuracyEngine() for null accuracy value.

## 0.0.36

- Updated native Roam SDK versions. Android v0.0.26 and iOS v0.0.27
- Added tracking config methods `Roam.setTrackingConfig()`, `Roam.getTrackingConfig()` and `Roam.resetTrackingConfig()`
- Fixed blue bar for custom tracking in iOS.

## 0.0.37

- Updated native Roam SDK versions. Android v0.0.28 and iOS v0.0.28
- Added `discardLocation` parameter for `Roam.setTrackingConfig()`, `Roam.getTrackingConfig()` and `Roam.resetTrackingConfig()` methods.

## 0.0.38

- Added extra `distanceCovered` property in android for trip summary.

## 0.0.39

- Updated native Roam SDK versions. Android v0.0.30 and iOS v0.0.31
- Fixed `OFFLINE` input for Tracking config NetworkState

## 0.0.40

- Updated native Roam SDK iOS version v0.0.36
- Added `altitude`, `elevationGain`, and `totalElevationGain` in `trip_status` listener
