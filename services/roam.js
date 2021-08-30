import Roam from 'roam-reactnative';
import AsyncStorage from '@react-native-community/async-storage';

const Configuration = {
  eventListenerStatus: true,
  geofenceEvents: false,
  locationEvents: true,
  locationListenerStatus: true,
  movingGeofenceEvents: false,
  tripsEvents: true,
};

const ErrorCodes = {
  InvalidUserId: 'GS402',
};

const createTestUser = async () => {
  return new Promise((resolve, reject) => {
    const handleCreateUserCallback = async (success) => {
      AsyncStorage.setItem('userId', success.userId);
      resolve(success.userId);
    };

    const handleCreateUserError = (error) => {
      reject(error);
    };

    Roam.createUser(
      'test-user',
      handleCreateUserCallback,
      handleCreateUserError,
    );
  });
};

const loadTestUser = async (id) => {
  return new Promise((resolve, reject) => {
    const handleLoadUserCallback = async (success) => {
      resolve(success.userId);
    };

    const handleLoadUserError = (error) => {
      reject(error.errorCode);
    };
    Roam.getUser(id, handleLoadUserCallback, handleLoadUserError);
  });
};

const createTestTrip = async () => {
  return new Promise((resolve, reject) => {
    const handleCreateTripCallback = async (success) => {
      console.log(suc);
      AsyncStorage.setItem('tripId', success.tripId);
      resolve(success.userId);
    };

    const handleCreateTripError = (error) => {
      reject(error);
    };

    Roam.createTrip(false,
      handleCreateTripCallback,
      handleCreateTripError,
    );
 
  });
};


const startTestTrip = async (id) => {
  return new Promise((resolve, reject) => {
    const handleTripCallback = async (status) => {
      resolve(status);
    };

    const handleLoadUserError = (error) => {
      reject(error.errorCode);
    };
    Roam.startTrip(id,"test",handleTripCallback,handleLoadUserError)
  });

};
export const roam = {createTestUser, loadTestUser, createTestTrip,startTestTrip, Configuration, ErrorCodes};
