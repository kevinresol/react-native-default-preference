
package com.kevinresol.react_native_default_preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.Map;

public class RNDefaultPreferenceModule extends ReactContextBaseJavaModule {
  private String preferencesName = "react-native";

  private final ReactApplicationContext reactContext;

  public RNDefaultPreferenceModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNDefaultPreference";
  }

  @ReactMethod
  public void get(String key, Promise promise) {
    promise.resolve(getPreferences().getString(key, null));
  }

  @ReactMethod
  public void set(String key, String value, Promise promise) {
    getEditor().putString(key, value).commit();
    promise.resolve(null);
  }

  @ReactMethod
  public void clear(String key, Promise promise) {
    getEditor().remove(key).commit();
    promise.resolve(null);
  }

  @ReactMethod
  public void getMultiple(ReadableArray keys, Promise promise) {
    WritableArray result = Arguments.createArray();
    for(int i = 0; i < keys.size(); i++) {
      result.pushString(getPreferences().getString(keys.getString(i), null));
    }
    promise.resolve(result);
  }

  @ReactMethod
  public void setMultiple(ReadableMap data, Promise promise) {
    SharedPreferences.Editor editor = getEditor();
    ReadableMapKeySetIterator iter = data.keySetIterator();
    while(iter.hasNextKey()) {
      String key = iter.nextKey();
      editor.putString(key, data.getString(key)).commit();
    }
    promise.resolve(null);
  }

  @ReactMethod
  public void clearMultiple(ReadableArray keys, Promise promise) {
    SharedPreferences.Editor editor = getEditor();
    for(int i = 0; i < keys.size(); i++) {
      editor.remove(keys.getString(i));
    }
    editor.commit();
    promise.resolve(null);
  }

  @ReactMethod
  public void getAll(Promise promise) {
    WritableMap result = Arguments.createMap();
    Map<String, ?> allEntries = getPreferences().getAll();
    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
      result.putString(entry.getKey(), entry.getValue().toString());
    }
    promise.resolve(result);
  }

  @ReactMethod
  public void clearAll(Promise promise) {
    SharedPreferences.Editor editor = getEditor();
    Map<String, ?> allEntries = getPreferences().getAll();
    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
      editor.remove(entry.getKey());
    }
    editor.commit();
    promise.resolve(null);
  }

  @ReactMethod
  public void setName(String preferencesName, Promise promise) {
    this.preferencesName = preferencesName;
    promise.resolve(null);
  }

  @ReactMethod
  public void getName(Promise promise) {
    promise.resolve(preferencesName);
  }

  private SharedPreferences getPreferences() {
    return getReactApplicationContext().getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
  }
  private SharedPreferences.Editor getEditor() {
    return getPreferences().edit();
  }
}
