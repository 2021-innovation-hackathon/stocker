package com.Hackathon;

import android.app.Application;

class ThemeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ThemeUtil.applyTheme(this);
    }
}
