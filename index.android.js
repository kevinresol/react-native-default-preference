
import { NativeModules } from 'react-native';

const { RNDefaultPreference } = NativeModules;

const getArray = async key => JSON.parse(await RNDefaultPreference.getString(key));
const getObject = async key => JSON.parse(await RNDefaultPreference.getString(key));

export default {
  ...RNDefaultPreference,
  getArray,
  getObject,
};
