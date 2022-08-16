# Migration Guide

## Roam 0.0.25 to Roam 0.0.26

- Location data in location receiver is changed from single location object to list of location updates.

## Roam 0.0.29 to Roam 0.0.30

- Trip data in trip receiver is changed from single trip object to list of trip updates for Android only.
- New parameter added to `Roam.setForegroundNotification()` method.

## Roam 0.0.37 to Roam 0.0.38

- The parameter for distance covered in trip summary method is changed from `distance` to `distanceCovered` for Android. This is to match the iOS trip response.
