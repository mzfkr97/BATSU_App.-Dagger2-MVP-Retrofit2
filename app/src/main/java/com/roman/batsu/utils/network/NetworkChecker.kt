package com.roman.batsu.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity

object NetworkChecker {


    @JvmStatic
    fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager=context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

}