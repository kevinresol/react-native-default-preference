
import { NativeModules, Platform } from 'react-native';

const { RNDefaultPreference } = NativeModules;
let DefaultPreference = RNDefaultPreference;

if (Platform.OS !== 'ios') {
  
  const setObject = (key, obj) => {
    RNDefaultPreference.set(key, JSON.stringify(obj));
  }
  const getObject = async (key) => {
    const stringifiedObject = await RNDefaultPreference.get(key);
    if (stringifiedObject) {
      return JSON.parse(stringifiedObject);
    }
    return null
  }
  DefaultPreference = {
    ...RNDefaultPreference,
    setObject,
    getObject,
    getArray: getObject,
    setArray: setObject, 
  }
} 
export default DefaultPreference;
