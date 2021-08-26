package com.Hackathon;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeUtil {

    static final String THEME_KEY = "theme_value";

    public static void applyTheme(@NonNull Context context) {
        int option = SharedPrefsUtil.getInt(context, THEME_KEY, 0);
        applyTheme(context, option);
    }

    // 0 : light, 1 : dark, 2 : daytime
    public static void applyTheme(@NonNull Context context, int option) {
        // 테마 값 저장
        SharedPrefsUtil.putInt(context, THEME_KEY, option);

        if (option == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            return;
        }

        if (option == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
        }
    }
}