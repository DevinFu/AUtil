package com.frozy.autil.android;

import android.content.Context;
import android.content.SharedPreferences;

@SuppressWarnings("unused")
public class SharedPrefenrenceUtil {

    private static SharedPrefenrenceUtil mInstance;

    private SharedPreferences mSharedPreferences;

    public synchronized static SharedPrefenrenceUtil getSingleInstance(Context pContext) {
        if (mInstance == null) {
            mInstance = new SharedPrefenrenceUtil(pContext);
        }
        return mInstance;
    }

    private SharedPrefenrenceUtil(Context pContext) {
        this.mSharedPreferences = pContext.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public String getStringValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void storeStringValue(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public long getLongValue(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public void storeLongValue(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    public void storeBooleanValue(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBooleanValue(String key, boolean defauleValue) {
        return mSharedPreferences.getBoolean(key, defauleValue);
    }

}
