package com.indira.usedbooks;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

  public static String SAVED_USER_NAME = "saved_user";
  public static String SAVED_USER_ID = "saved_user_id";

  public static void set(final Context context, final String key, final Object value) {
    SharedPreferences.Editor sharedPreferenceEditor = PreferenceManager.getDefaultSharedPreferences(context)
        .edit();
    if (value instanceof String) {
      sharedPreferenceEditor.putString(key, (String) value);
    } else if (value instanceof Long) {
      sharedPreferenceEditor.putLong(key, (Long) value);
    } else if (value instanceof Integer) {
      sharedPreferenceEditor.putInt(key, (Integer) value);
    } else if (value instanceof Boolean) {
      sharedPreferenceEditor.putBoolean(key, (Boolean) value);
    } else {
      sharedPreferenceEditor.putString(key, (String) value);
    }
    sharedPreferenceEditor.commit();
  }

  public static String getStringPrefs(Context ctx, String key) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, "");
  }

  public static String getStringPrefs(Context ctx, String key, String defaultValue) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, defaultValue);
  }

  public static int getIntegerPrefs(Context ctx, String key) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(key, 0);
  }

  public static int getIntegerPrefs(Context ctx, String key, int defaultValue) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(key, defaultValue);
  }

  public static long getLongPrefs(Context ctx, String key) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getLong(key, 0);
  }

  public static long getLongPrefs(Context ctx, String key, int defaultValue) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getLong(key, defaultValue);
  }

  public static boolean getBooleanPrefs(Context ctx, String key, boolean defaultValue) {
    return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(key, defaultValue);
  }

  public static void clear(final Context context) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
    SharedPreferences prefs = context.getSharedPreferences("RayPreferences", Context.MODE_MULTI_PROCESS);
    prefs.edit().clear().commit();
  }

  public static void delete(final Context context, final String key) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();

    if (sharedPreferences.contains(key)) {
      sharedPreferenceEditor.remove(key);
      sharedPreferenceEditor.apply();
    }
  }

  public static void remove(final Context context, String key) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).commit();
  }

  public static boolean isLoggedIn(Context context) {
     return PreferenceUtils.getIntegerPrefs(context, SAVED_USER_ID , 0) > 0;
  }

}