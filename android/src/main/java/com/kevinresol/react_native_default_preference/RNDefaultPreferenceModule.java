
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
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.Map;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class RNDefaultPreferenceModule extends ReactContextBaseJavaModule {
  private String preferencesName = "react-native";

  private final ReactApplicationContext reactContext;

  public RNDefaultPreferenceModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  private static WritableMap convertJsonToMap(JSONObject jsonObject) throws JSONException {
    if (jsonObject != null) {
      WritableNativeMap map = new WritableNativeMap();
      Iterator<String> iterator = jsonObject.keys();
      while (iterator.hasNext()) {
        String key = iterator.next();
        Object value = jsonObject.get(key);
        if (value == null) {
          map.putNull(key);
        } else if (value instanceof JSONObject) {
          map.putMap(key, convertJsonToMap((JSONObject) value));
        } else if (value instanceof JSONArray) {
          map.putArray(key, convertJsonToArray((JSONArray) value));
        } else if (value instanceof Boolean) {
          map.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
          map.putInt(key, (Integer) value);
        } else if (value instanceof Double) {
          map.putDouble(key, (Double) value);
        } else if (value instanceof String) {
          map.putString(key, (String) value);
        } else {
          map.putString(key, value.toString());
        }
      }
      return map;
    }
    return null;
  }

  private static WritableArray convertJsonToArray(JSONArray jsonArray) throws JSONException {
    if (jsonArray != null) {
      WritableNativeArray array = new WritableNativeArray();
      for (int i = 0; i < jsonArray.length(); i++) {
        Object value = jsonArray.get(i);
        if (value == null) {
          array.pushNull();
        } else if (value instanceof JSONObject) {
          array.pushMap(convertJsonToMap((JSONObject) value));
        } else if (value instanceof JSONArray) {
          array.pushArray(convertJsonToArray((JSONArray) value));
        } else if (value instanceof Boolean) {
          array.pushBoolean((Boolean) value);
        } else if (value instanceof Integer) {
          array.pushInt((Integer) value);
        } else if (value instanceof Double) {
          array.pushDouble((Double) value);
        } else if (value instanceof String) {
          array.pushString((String) value);
        } else {
          array.pushString(value.toString());
        }
      }
      return array;
    }
    return null;
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
  public void getObject(String key, Promise promise) {
    String value = getPreferences().getString(key, null);
    WritableMap object = null;
    try {
      if (value != null) {
        JSONObject json = new JSONObject(value);
        if (json != null) {
          object = convertJsonToMap(json);
        }
      }
    } catch (JSONException error) {}
    promise.resolve(object);
  }

  @ReactMethod
  public void getArray(String key, Promise promise) {
    String value = getPreferences().getString(key, null);
    WritableArray array = null;
    try {
      if (array != null) {
        JSONArray json = new JSONArray(value);
        if (json != null) {
          array = convertJsonToArray(json);
        }
      }
    } catch (JSONException error) {}
    promise.resolve(array);
  }

  @ReactMethod
  public void getBoolean(String key, Promise promise) {
    boolean value = false;
    try {
      value = getPreferences().getBoolean(key, false);
    } catch (ClassCastException error) {
      try {
        value = Boolean.valueOf(getPreferences().getString(key, "")).booleanValue();
      } catch (Exception e) {
        value = false;
      }
    }
    promise.resolve(value);
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
