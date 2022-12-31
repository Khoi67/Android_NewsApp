package com.example.thenewsapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private static final String PREFS_NAME = "share_prefs";
    private static SharedPrefs mInstance;
    private SharedPreferences mSharedPreferences;

    private SharedPrefs() {
        mSharedPreferences = MainApplication.self().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefs getInstance() {
        if (mInstance == null) {
            mInstance = new SharedPrefs();
        }
        return mInstance;
    }

    public <T> void put(LocalKey key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (data instanceof String) {
            editor.putString(key.getValue(), (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key.getValue(), (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key.getValue(), (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key.getValue(), (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key.getValue(), (Long) data);
        } else {
            editor.putString(key.getValue(), MainApplication.self().getGson().toJson(data));
        }
        editor.apply();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(LocalKey key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key.getValue(), "");
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key.getValue(), false));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key.getValue(), 0));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key.getValue(), 0));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key.getValue(), 0));
        } else {
            return (T) MainApplication.self().getGson().fromJson(mSharedPreferences.getString(key.getValue(), ""), anonymousClass);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(LocalKey key, Class<T> anonymousClass, T defaultValue) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key.getValue(), (String) defaultValue);
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key.getValue(), (Boolean) defaultValue));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key.getValue(), (Float) defaultValue));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key.getValue(), (Integer) defaultValue));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key.getValue(), (Long) defaultValue));
        } else {
            return (T) MainApplication.self()
                    .getGson()
                    .fromJson(mSharedPreferences.getString(key.getValue(), ""), anonymousClass);
        }
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}

