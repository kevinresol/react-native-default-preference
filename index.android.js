
import { NativeModules } from 'react-native';

const { RNDefaultPreference } = NativeModules;

const getArray = key => JSON.parse(RNDefaultPreference.getString(key));
const getObject = key => JSON.parse(RNDefaultPreference.getString(key));

export default {
  ...RNDefaultPreference,
  getArray,
  getObject,
};
