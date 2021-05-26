[![npm version](https://badge.fury.io/js/react-native-default-preference.svg)](https://badge.fury.io/js/react-native-default-preference)

# react-native-default-preference


Use `SharedPreferences` (Android) and `UserDefaults` (iOS) with React Native over a unified interface.
All data is stored as a string. If you need to support more complex data structures, use serialization/deserialization (e.g. JSON).

## Getting started

`$ npm install react-native-default-preference --save`

### React Native >= 0.60

`$ cd ios && pod install`

### React Native <= 0.59

`$ react-native link react-native-default-preference`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-default-preference` and add `RNDefaultPreference.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNDefaultPreference.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.kevinresol.react_native_default_preference.RNDefaultPreferencePackage;` to the imports at the top of the file
  - Add `new RNDefaultPreferencePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-default-preference'
  	project(':react-native-default-preference').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-default-preference/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-default-preference')
  	```

## Usage
```javascript
import DefaultPreference from 'react-native-default-preference';

DefaultPreference.get('my key').then(function(value) {console.log(value)});
DefaultPreference.set('my key', 'my value').then(function() {console.log('done')});
```

## API

```typescript
function get(key: string): Promise<string | undefined | null>;
function set(key: string, value: string): Promise<void>;
function clear(key: string): Promise<void>;
function getMultiple(keys: string[]): Promise<string[]>;
function setMultiple(data: {[key: string]: string}): Promise<void>;
function clearMultiple(keys: string[]): Promise<void>;
function getAll(): Promise<{[key: string]: string}>;
function clearAll(): Promise<void>;

/** Gets and sets the current preferences file name (android) or user default suite name (ios) **/
function getName(): Promise<string>;
function setName(name: string): Promise<void>;
```

## Cordova Native Storage Compatibility
This module is compatible with cordova-plugin-native-storage.

### Android
You need to use the same SharedPreference as in the cordova plugin, to do so add
the following line:

```js
import { Platform } from 'react-native';
// ...
if (Platform.OS === 'android') DefaultPreference.setName('NativeStorage');
```

### iOS
You don't need to change the name, as cordova-plugin-native-storage uses the default
value.
