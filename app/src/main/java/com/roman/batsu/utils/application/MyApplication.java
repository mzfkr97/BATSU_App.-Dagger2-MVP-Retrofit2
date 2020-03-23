package com.roman.batsu.utils.application;

import android.app.Application;

import com.roman.batsu.dagger.AppComponent;
import com.roman.batsu.dagger.DaggerAppComponent;

public class MyApplication extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
//        SharedPreferences sharedPreferences =
//                PreferenceManager.getDefaultSharedPreferences(this);
//        String themePref = sharedPreferences.getString("themePref", ThemeHelper.DEFAULT_MODE);
//        ThemeHelper.applyTheme(themePref);

        component = DaggerAppComponent.create();
    }

    //databaseHelper = App.getComponent().getDatabaseHelper();
    public static AppComponent getComponent() {
        return component;
    }

}
