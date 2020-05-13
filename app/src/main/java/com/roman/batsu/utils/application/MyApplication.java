package com.roman.batsu.utils.application;

import android.app.Application;

import com.roman.batsu.utils.LoaderFragment;
import com.roman.batsu.utils.network.RXConnector;

public class MyApplication extends Application {

    private LoaderFragment loaderFragment;
    private RXConnector rxConnector;

    @Override
    public void onCreate() {
        super.onCreate();
        loaderFragment = getLoaderFragment();
        rxConnector = getRxConnector();
    }

    public LoaderFragment getLoaderFragment(){
        if (loaderFragment == null){
            loaderFragment = new LoaderFragment();
        }
        else {
            return loaderFragment;
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
