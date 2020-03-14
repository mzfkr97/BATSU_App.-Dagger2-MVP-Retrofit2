package com.roman.batsu.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChecker {




    private static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isNetworkAvailable(Context context){
        NetworkInfo info = NetworkChecker.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }





}