<p align="center">
  <a href="https://roam.ai" target="_blank" align="left">
    <img src="https://github.com/geosparks/roam-reactnative/blob/master/logo.png?raw=true" width="180">
  </a>
  <br />
</p>

[![npm version](https://d25lcipzij17d.cloudfront.net/badge.svg?id=js&type=6&v=0.0.1&x2=0)](https://badge.fury.io/js/roam-reactnative)
[![Npm Publish](https://github.com/geosparks/roam-reactnative/actions/workflows/main.yml/badge.svg?branch=0.0.1)](https://github.com/geosparks/roam-reactnative/actions/workflows/main.yml)

# React Native Location SDK
High accuracy and battery efficient location SDK for iOS and Android developed and maintained by Roam BV


### Featured Apps 

<a href="https://sprintcrowd.com"><img src="https://sprintcrowd.com/wp-content/uploads/2020/08/sc-logo_400.png" width="100"></a>


# Quick Start
The Roam React Native SDK makes it quick and easy to build a
location tracker for your React Native app. We provide powerful and
customizable tracking modes and features that can be used to collect
your users location updates.

**Install the module**
======================


In your project directory, install from npm, and then link it.

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
$ npm install roam-reactnative --save
$ react-native link roam-reactnative
```

**Installation** 
================


**iOS**

Install using Cocoapods, open `podfile` and add SDK to file.

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
pod 'GeoSpark'
```

Once you have updated your `podfile`, run `pod install` in your
terminal.

**Configure project**

To configure the location services, add following entries to the
**Info.plist** file.

![](https://gblobscdn.gitbook.com/assets%2F-LSIY5fR7w61d6wHf6iI%2F-LWA3HF19_HBBEZj1hrU%2F-LWA55gjYBsLYYd8f_Oi%2F4.png?alt=media&token=4feb38a5-d013-43ab-81a6-7b69f96e09c4)

Then, in your project settings, go to `Capabilities > Background Modes`
and turn on background fetch, location updates, remote-notifications.

![](https://gblobscdn.gitbook.com/assets%2F-LSIY5fR7w61d6wHf6iI%2F-LWA3HF19_HBBEZj1hrU%2F-LWA5AepQh8EoHoIB3xS%2F3.png?alt=media&token=9436cc91-33b6-4126-8629-c610d80bb281)

Then, go to Build Settings in the project targets and change 'Always
Embed Swift Standard Libraries' to 'Yes'.

**Manual Linking**

1.  Open the iOS module files, located inside
    `node_modules/react-native-geospark/ios/`.

2.  Open the app workspace file `(AppName.xcworkspace)` in Xcode.

3.  Move the `RNRoam.h` and `RNRoam.m` files to your project.
    When shown a popup window, select Create groups.

**Android**

Install the SDK to your project via `Gradle` in Android Studio, add the maven below in your `project build.gradle` file.

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: groovy; gutter: false; theme: Confluence" data-theme="Confluence"}
repositories { 
    maven { 
        url 'https://com-geospark-android.s3.amazonaws.com/' 
    } 
} 
```
add the dependencies below in your `app build.gradle` file.
``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: groovy; gutter: false; theme: Confluence" data-theme="Confluence"}
dependencies {
 implementation 'com.geospark.android:geospark:3.1.5'
}
```

**Initialize SDK** 
==================


Import the module in `App.js` file

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
import Roam from 'roam-reactnative'; 
```

**Android**

Initialize the SDK with your `publishable key`.

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
//In onCreate method of your Application class include the code below.
public class MainApplication extends Application implements ReactApplication {
  @Override   
  public void onCreate() {        
    super.onCreate();        
    SoLoader.init(this, /* native exopackage */ false);     
    GeoSpark.initialize(this, "PUBLISH_KEY");   
  }
};    
```

**iOS**

Import GeoSpark into your `AppDelegate` file.

**Objective-C**

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
#import <GeoSpark/GeoSpark.h>
```

Initialize the SDK in your `AppDelegate` class before calling any other
GeoSpark methods under this `application:didFinishLaunchingWithOptions:`

**Objective-C**

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {    
   [GeoSpark intialize:@"PUBLISHABLEKEY" :nil :nil :nil :nil :AWSRegionUnknown];  
    return YES;
}
```

Creating Users 
==============


Once the SDK is initialized, we need to *create* or *get a user* to
start the tracking and use other methods. Every user created will have a
unique Roam identifier which will be used later to login and access
developer APIs. We can call it as Roam userId.

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.createUser("Description", success => {
 // do something on success    
},
error => {
// do something on error
});
```

The option *user description* can be used to update your user
information such as name, address or add an existing user ID. Make sure
the information is encrypted if you are planning to save personal user
information like email or phone number.

You can always set or update user descriptions later using the below
code.

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.setDescription("SET USER DESCRIPTION HERE");
```

If you already have a GeoSpark userID which you would like to reuse
instead of creating a new user, use the below to get user session.

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.getUser("USER-ID", success => {
// do something on success    
},
error => {
// do something on error
});
```
You can subscribe to locations and events and use the data locally on your device or send it directly to your own backend server.

To do that, you need to set the location and event listener to `true` using the below method. By default the status will set to `false` and needs to be set to `true` in order to stream the location and events updates to the same device or other devices.

```
Roam.toggleListener(locations, events, success => {
 // do something on success    
},
error => {
// do something on error
});
```

**Request Permissions** 
=======================


Get location permission from the App user on the device. Also check if
the user has turned on location services for the device. In addition,
get motion permission for iOS.

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
// Call this method to check Location Permission for Android & iOS
Roam.checkLocationPermission( status => {    
// do something with status
});

​// Call this method to request Location Permission for Android & iOS
Roam.requestLocationPermission();
```

**Android**

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
// Call this method to check Location services for Android
Roam.checkLocationServices( status => {    
// do something with status
}); 
​// Call this method to request Location services for Android
Roam.requestLocationServices();
```

To start tracking the location above Android 10

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
// Call this method to check background location permission for Android
Roam.checkBackgroundLocationPermission( status => {        
// do something with status
});
// Call this method to request background location Permission for Android
Roam.requestBackgroundLocationPermission();
```

Location Tracking 
=================


### Start Tracking 

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.startTracking(TrackingMode);
```

Use the tracking modes while you use the startTracking method
`Roam.startTracking`

### **Tracking Modes** 

Roam has three default tracking modes along with a custom version.
They differ based on the frequency of location updates and battery
consumption. The higher the frequency, the higher is the battery
consumption. Android you must use [foreground
service](https://developer.android.com/about/versions/oreo/background-location-limits)
for continuous tracking.

<div class="table-wrap">

|          |                   |                    |                                |
| -------- | ----------------- | ------------------ | ------------------------------ |
| **Mode** | **Battery usage** | **Updates every ** | **Optimised for/advised for ** |
| Active   | 6% - 12%          | 25 ~ 250 meters    | Ride Hailing / Sharing         |
| Balanced | 3% - 6%           | 50 ~ 500 meters    | On Demand Services             |
| Passive  | 0% - 1%           | 100 ~ 1000 meters  | Social Apps                    |

</div>

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
//active tracking
Roam.startTracking(Roam.TrackingMode.ACTIVE);
// balanced tracking
Roam.startTracking(Roam.TrackingMode.BALANCED);
// passive tracking
Roam.startTracking(Roam.TrackingMode.PASSIVE);
```

### Custom Tracking Modes 

The SDK also allows you define a custom tracking mode that allows you to
customize and build your own tracking modes.

**Android**

<div class="table-wrap">

| **Type**          | **Unit** | **Unit Range** |
| ----------------- | -------- | -------------- |
| Distance Interval | Meters   | 1m ~ 2500m     |
| Time Interval     | Seconds  | 10s ~ 10800s   |

</div>

**Distance between location updates example code:**

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
//Update location based on distance between locations.
Roam.startTrackingDistanceInterval("DISTANCE IN METERS", "STATIONARY DURATION IN SECONDS", Roam.DesiredAccuracy.HIGH);
```

**Time between location updates example code:**

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
//Update location based on time interval.
Roam.startTrackingTimeInterval("INTERVAL IN SECONDS", Roam.DesiredAccuracy.HIGH);
```

**iOS**

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.startTrackingCustom(allowBackground,pauseAutomatic,activityType,
                              desiredAccuracy,showBackIndicator,distanceFilter,accuracyFilter);
```

Example

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.startTrackingCustom(true,true,Roam.ActivityType.FITNESS,
          Roam.DesiredAccuracyIOS.BEST,true,10,10);
```

You may see a delay if the user's device is in low power mode or has
connectivity issues.

Stop Tracking 
-------------

To stop the tracking use the below method.

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.stopTracking();
```

Publish Messages 
=================

It will both publish location data and these data will be sent to Roam servers for further processing and data will be saved in our database servers.
We will now have an option to send meta-data as a parameter along with location updates in the below json format.

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.publishAndSave(metadataJSON);
```
Example
``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
//Metadata is not mandatory
let metadataJSON = {"METADATA": {"1": true, "2": true, "3":true}};

Roam.publishAndSave(metadataJSON);
(optional)
Roam.publishAndSave(null);
```
Stop Publishing
================

It will stop publishing the location data to other clients.
``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.stopPublishing();
```

Subscribe Messages
=================

Now that you have enabled the location listener, use the below method to subscribe to your own or other user's location updates and events. 

**Subscribe**
``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.subscribe(TYPE, "USER-ID");
```
**UnSubscribe**
``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
Roam.unSubscribe(TYPE, "USER-ID");
```

<div class="table-wrap">

| **Type**                        | **Description** | 
| --------------------------------| -------- | 
| Roam.SubscribeListener.LOCATION | Subscribe to your own location (or) other user's location updates.   | 
| Roam.SubscribeListener.EVENTS   | Subscribe to your own events.  | 
| Roam.SubscribeListener.BOTH     | Subscribe to your own events and location (or) other user's location updates.  | 


## Documentation

Please visit our [Developer Center](https://github.com/geosparks/roam-reactnative/wiki) for instructions on other SDK methods.

## Contributing
- For developing the SDK, please visit our [CONTRIBUTING.md](https://github.com/geosparks/roam-reactnative/blob/master/CONTRIBUTING.md) to get started.

## Need Help?
If you have any problems or issues over our SDK, feel free to create a github issue or submit a request on [Roam Help](https://geosparkai.atlassian.net/servicedesk/customer/portal/2).
