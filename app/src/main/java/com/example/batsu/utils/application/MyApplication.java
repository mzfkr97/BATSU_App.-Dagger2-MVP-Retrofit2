package com.example.batsu.utils.application;

import android.app.Application;

import com.example.batsu.utils.LoaderFragment;
import com.example.batsu.utils.network.RXConnector;

public class MyApplication extends Application {


    private LoaderFragment loaderFragment;
    private RXConnector rxConnector;

    @Override
    public void onCreate() {
//        AppCompatDelegate.setDefaultNightMode(
//                AppCompatDelegate.MODE_NIGHT_NO);

//        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//
//        switch (currentNightMode) {
//            case Configuration.UI_MODE_NIGHT_NO:
//                // Night mode is not active, we're in day time
//            case Configuration.UI_MODE_NIGHT_YES:
//                // Night mode is active, we're at night!
//            case Configuration.UI_MODE_NIGHT_UNDEFINED:
//                // We don't know what mode we're in, assume notnight
//        }
        super.onCreate();
        loaderFragment = getLoaderFragment();
        rxConnector = getRxConnector();
    }

    public LoaderFragment getLoaderFragment(){
        if (loaderFragment == null){
            return new LoaderFragment();
        }
        return loaderFragment;
    }
    public RXConnector getRxConnector() {
        if (rxConnector == null){
            rxConnector =  new RXConnector();
        }
        return rxConnector;
    }

}
