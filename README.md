
# react-native-default-preference

Use `SharedPreference` (Android) and `UserDefaults` (iOS) with React Native over a unified interface.
All data are stored as String, in case more complex data structure is needed, serialize it (e.g. JSON)

## Getting started

`$ npm install --save react-native-default-preference`

### Mostly automatic installation

`$ react-native link react-native-default-preference`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-default-preference` and add `RNDefaultPreference.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNDefaultPreference.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNDefaultPreferencePackage;` to the imports at the top of the file
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

```haxe
function get(key:String):Promise<String>;
function set(key:String, value:String):Promise<Void>;
function clear(key:String):Promise<Void>;
function getMultiple(keys:Array<String>):Promise<Object>;
function setMultiple(data:Object):Promise<Void>;
function clearMultiple(keys:Array<String>):Promise<Void>;
function getAll():Promise<Object>;
function clearAll():Promise<Void>;
```
  
