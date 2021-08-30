/**
 * Sample Roam App
 * @format
 * @flow strict-local
 */

import AsyncStorage from '@react-native-community/async-storage';
import DeviceInfo from 'react-native-device-info';
import React, {
  useState,
  useEffect,
  useReducer,
  useRef,
  useCallback,
} from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  Alert,
  AppState,
  Platform,
} from 'react-native';

import Roam from 'roam-reactnative';
import {Button, TextField, Loader} from './components';
import {roam} from './services';

const App: () => React$Node = () => {
  //States
  const appStateRef = useRef(AppState.currentState);
  const [initialized, setInitialized] = useState(false);
  const [userId, setUserId] = useState();
  const [loadedUserId, setLoadedUserId] = useState();
  const [trackingStatus, setTrackingStatus] = useState();
  const [eventStatus, setEventStatus] = useState('Unknown');
  const [listenerStatus, setListenerStatus] = useState('Unknown');
  const [subscriptionStatus, setSubscriptionStatus] = useState('-');
  const [listenUpdatesStatus, setListenUpdatesStatus] = useState('-');
  const [updateCoutner, setUpdateCounter] = useState(0);
  const [tripId,setTripId] = useState(false);
  const [subscriptionTripStatus, setsubscriptionTripStatus] = useState('-');


  // Permissions
  const [permissions, setPermissions] = useReducer(
    (state, update) => ({
      ...state,
      ...update,
    }),
    {
      location: '',
      backgroundLocation: '',
      locationServices: '',
      backgroundLocationNeeded: null,
      locationServicesNeeded: null,
    },
    (state) => state,
  );

  //Initial configuration
  useEffect(() => {
    if (!initialized) {
      //Get stored userId
      AsyncStorage.getItem('userId').then((savedId) => {
        setUserId(savedId);
        setInitialized(true);
      });
      // Default roam configuration
      Roam.allowMockLocation(true);
      Roam.enableAccuracyEngine();
      Roam.isLocationTracking(setTrackingStatus);
      onCheckPermissions();
    }
  }, [initialized, onCheckPermissions]);

  // Refresh permissions on app state change
  useEffect(() => {
    const handleAppStateChange = (nextAppState) => {
      if (
        appStateRef.current.match(/inactive|background/) &&
        nextAppState === 'active'
      ) {
        onCheckPermissions();
      }
      appStateRef.current = nextAppState;
    };
    AppState.addEventListener('change', handleAppStateChange);
    return () => {
      AppState.removeEventListener('change', handleAppStateChange);
    };
  }, [onCheckPermissions]);

  // Actions
  const onCreateUserPress = () => {
    roam.createTestUser().then(setUserId);
  };

  const onLoadTestUser = () => {
    roam
      .loadTestUser(userId)
      .then(setLoadedUserId)
      .catch((error) => {
        if (error === roam.ErrorCodes.InvalidUserId) {
          Alert.alert('Invalid user id', 'Please create a test user before');
        }
      });
  };

  const onRequestPermission = (type) => {
    switch (type) {
      case 'location':
        Roam.requestLocationPermission();
        break;
      case 'locationServices':
        Roam.requestLocationServices();
        break;
      case 'backgroundLocation':
        Roam.requestBackgroundLocationPermission();
        break;
    }
  };

  const onCheckPermissions = useCallback(async () => {
    let {locationServicesNeeded, backgroundLocationNeeded} = permissions;

    // Check if location services and background are needed on this device
    if (locationServicesNeeded === null || backgroundLocationNeeded === null) {
      const apiLevel = await DeviceInfo.getApiLevel();
      locationServicesNeeded = Platform.OS === 'android';
      backgroundLocationNeeded = locationServicesNeeded && apiLevel >= 29;

      //Update requirements to avoid the check the next time
      let updatedPermissions = {};
      if (locationServicesNeeded === false) {
        updatedPermissions.locationServices = 'N/A';
      }
      if (backgroundLocationNeeded === false) {
        updatedPermissions.backgroundLocation = 'N/A';
      }
      setPermissions({
        locationServicesNeeded,
        backgroundLocationNeeded,
        ...updatedPermissions,
      });
    }

    Roam.checkLocationPermission((location) => {
      setPermissions({location});
    });
    if (locationServicesNeeded) {
      Roam.checkLocationServices((locationServices) => {
        setPermissions({locationServices});
      });
    }
    if (backgroundLocationNeeded) {
      Roam.checkBackgroundLocationPermission((backgroundLocation) => {
        setPermissions({backgroundLocation});
      });
    }
  }, [permissions]);

  const onToggleTracking = () => {
    Roam.isLocationTracking((status) => {
      if (status === 'ENABLED') {
        Roam.stopPublishing();
        Roam.stopTracking();
      } else {
        Roam.publishAndSave(null);
        Roam.startTrackingTimeInterval(2, "HIGH");
      }
      Roam.isLocationTracking(setTrackingStatus);
    });
  };

  const enableEvents = () => {
    // Just to make each flag explicit
    const Events = {
      geofenceEnabled: false,
      tripEnabled: true,
      locationEnabled: true,
      movingGeofenceEnabled: false,
    };

    Roam.toggleEvents(
      Events.geofenceEnabled,
      Events.tripEnabled,
      Events.locationEnabled,
      Events.movingGeofenceEnabled,
      ({locationEvents, tripsEvents}) => {
        const statusText =
          locationEvents && tripsEvents ? 'Enabled' : 'Disabled';
        setEventStatus(statusText);
      },
      (error) => {
        setEventStatus('Error');
      },
    );
  };

  const enableListeners = () => {
    // Just to make each flag explicit
    const Listeners = {
      locationListenerEnabled: true,
      eventListenerEnabled: true,
    };

    Roam.toggleListener(
      Listeners.locationListenerEnabled,
      Listeners.eventListenerEnabled,
      ({eventListenerStatus, locationListenerStatus}) => {
        const statusText =
          eventListenerStatus && locationListenerStatus
            ? 'Enabled'
            : 'Disabled';
        setListenerStatus(statusText);
      },
      (error) => {
        setListenerStatus('Error');
      },
    );
  };

  const onSubscribeLocation = () => {
    if (typeof loadedUserId === 'undefined') {
      Alert.alert('Invalid user id', 'Please load a test user before');
      return;
    }

    Roam.subscribe("LOCATION", loadedUserId);
    setSubscriptionStatus('Enabled');
  };

  const onListenUpdates = () => {
    if (subscriptionStatus !== 'Enabled') {
      Alert.alert('Error', 'Please, subscribe location before');
      return;
    }
    Roam.startListener('location', (location) => {
      console.log('Location', location);
      setUpdateCounter((count) => count + 1);
    });
    setListenUpdatesStatus('Enabled');
  };

  const createTrip = () => {
    roam.createTestTrip().then(setTripId);
  };

  const startTrip = () => {
    roam
    .startTestTrip(tripId)
    .then(setTripId)
    .catch((error) => {
      if (error === roam.ErrorCodes.InvalidUserId) {
        Alert.alert('Invalid user id', 'Please create a test user before');
      }
    });
  };

  const subscribeTripStatus = () => { 
    if (sub !== 'Enabled') {
      Alert.alert('Error', 'Please, subscribe location before');
      return;
    }


  };
  if (!initialized) {
    return <Loader />;
  }

  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <View style={styles.sectionContainer}>
            <Text style={styles.title}>User</Text>
            <View style={styles.row}>
              <Button onPress={onCreateUserPress}>Create test user</Button>
              <TextField>{userId}</TextField>
            </View>
            <View style={styles.row}>
              <Button title="" onPress={onLoadTestUser}>
                Load test user
              </Button>
              <TextField>
                {typeof loadedUserId === 'undefined' ? 'Empty' : loadedUserId}
              </TextField>
            </View>
          </View>
          <View style={styles.sectionContainer}>
            <View style={[styles.row, styles.actionRow]}>
              <Text style={styles.title}>Permissions</Text>
              <Button type="action" onPress={onCheckPermissions}>
                Refresh
              </Button>
            </View>
            <Text style={styles.item}>Location Permission</Text>
            <View style={styles.row}>
              <Button onPress={() => onRequestPermission('location')}>
                Request
              </Button>
              <TextField>{permissions.location}</TextField>
            </View>
            <Text style={styles.item}>Location Services</Text>
            <View style={styles.row}>
              <Button
                disabled={!permissions.locationServicesNeeded}
                onPress={() => onRequestPermission('locationServices')}>
                Request
              </Button>
              <TextField>{permissions.locationServices}</TextField>
            </View>
            <Text style={styles.item}>Background location</Text>
            <View style={styles.row}>
              <Button
                disabled={!permissions.backgroundLocationNeeded}
                onPress={() => onRequestPermission('backgroundLocation')}>
                Request
              </Button>
              <TextField>{permissions.backgroundLocation}</TextField>
            </View>
          </View>
          <View style={styles.sectionContainer}>
            <Text style={styles.title}>Actions</Text>
            <View style={styles.row}>
              <Button onPress={enableEvents}>Enable Events</Button>
              <TextField>{eventStatus}</TextField>
            </View>
            <View style={styles.row}>
              <Button onPress={enableListeners}>Enable Listeners</Button>
              <TextField>{listenerStatus}</TextField>
            </View>
            <View style={styles.row}>
              <Button onPress={onSubscribeLocation}>Subscribe Location</Button>
              <TextField>{subscriptionStatus}</TextField>
            </View>
             <View style={styles.row}>
              <Button onPress={subscribeTripStatus}>SubscribeTripStatus</Button>
              <TextField>{eventStatus}</TextField>
            </View>
            <View style={styles.row}>
              <Button onPress={onListenUpdates}>Listen updates</Button>
              <TextField>{listenUpdatesStatus}</TextField>
            </View>
            <View style={styles.row}>
              <Button onPress={onToggleTracking}>Toggle Tracking</Button>
              <TextField>{trackingStatus}</TextField>
            </View>
          </View>
          <View style={styles.sectionContainer}>
          <Text style={styles.title}>Trip</Text>
          <View style={styles.row}>
              <Button onPress={createTrip}>Create Trip</Button>
              <TextField>{tripId}</TextField>
            </View>
          <View style={styles.row}>
              <Button onPress={startTrip}>Start trip</Button>
              <TextField>{listenerStatus}</TextField>
            </View>
          </View>

          <View style={styles.sectionContainer}>
            <Text style={styles.counter}>
              Location updates: {updateCoutner}
            </Text>
          </View>
        </ScrollView>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: 'white',
  },
  row: {
    display: 'flex',
    flexDirection: 'row',
  },
  actionRow: {
    justifyContent: 'space-between',
  },
  sectionContainer: {
    paddingHorizontal: 24,
    marginTop: 20,
  },
  title: {
    fontSize: 22,
    fontWeight: 'bold',
  },
  item: {
    marginTop: 5,
    fontSize: 18,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
  counter: {
    fontSize: 22,
    fontWeight: 'bold',
    color: 'red',
  },
});

export default App;
