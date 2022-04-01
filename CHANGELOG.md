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

- Updated to latest iOS native SDK version v0.0.14 which improves `the Roam.getCurrentLocation()`  to return location faster.

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
- Fixed the coordinates arrangment for `Roam.getTripSummary()` on local trips.