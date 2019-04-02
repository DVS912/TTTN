package com.example.fineart_ds.util;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferenceHelper {
    private static final String NEWS_ADS_PREFERENCES = "Cao_Duong";
    public static String ACOUNT = "ACOUNT";
    public static String PASSWORD = "PASSWORD";
    public static String NAME = "NAME";
    public static String ADDRESS = "ADDRESS";
    public static String PHONE = "PHONE";
    public static String CHINHSACH = "CHINHSACH";
    public static String DAMBAO = "DAMBAO";
    public static String DOITRA = "DOITRA";
    public static String VANCHUYEN = "VANCHUYEN";
    public static String IMAGE = "IMAGE";

    public static String getString(String key, Context context) {
        return getStringValue(key, context);
    }

    public static void setString(Context context, String key, String value) {
        putStringValue(key, value, context);
    }


    public static void putStringValue(String key, String s, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, s);
        editor.commit();
    }
    public static String getStringValue(String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        return pref.getString(key, "");
    }
}
