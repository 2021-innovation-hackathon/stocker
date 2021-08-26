package com.Hackathon;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

class SharedPrefsUtil {
    public static SharedPreferences getSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPrefs(context).edit();
    }

    public static String getString(Context context, String key) {
        return getSharedPrefs(context).getString(key, null);
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getSharedPrefs(context).getString(key, defaultValue);
    }

    public static Set<String> getStringSet(Context context, final String key, final Set<String> defValue) {
        SharedPreferences prefs = getSharedPrefs(context);
        return prefs.getStringSet(key, defValue);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getSharedPrefs(context).getInt(key, defaultValue);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return getSharedPrefs(context).getLong(key, defaultValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getSharedPrefs(context).getBoolean(key, defaultValue);
    }

    public static void putString(Context context, String key, String value) {
        getEditor(context).putString(key, value).apply();
    }

    /**
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @see SharedPreferences.Editor#putStringSet(String, Set)
     */
    public static void putStringSet(Context context, final String key, final Set<String> value) {
        final SharedPreferences.Editor editor = getEditor(context); //.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static void putInt(Context context, String key, int value) {
        getEditor(context).putInt(key, value).apply();
    }

    public static void putLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).apply();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).apply();
    }

    public static void clear(Context context, String key) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(key);
        editor.apply();
    }
}
