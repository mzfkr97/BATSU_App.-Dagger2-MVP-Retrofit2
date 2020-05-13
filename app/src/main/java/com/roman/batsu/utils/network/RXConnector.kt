package com.roman.batsu.utils.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.roman.batsu.utils.api.ApiClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RXConnector {
    private lateinit var retrofit: Retrofit

    /**
     *
     * get XML RetrofitClient xml
     * приходит в
     * некоторых ответах,
     * в частности
     * в поездах
     */
    private val retrofitClient: Retrofit?
        get() {
            val gSon: Gson = GsonBuilder()
                    .setLenient()
                    .create()
            retrofit = Retrofit.Builder()
                    .baseUrl(MAIN_URL).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gSon))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit
        }

    private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    /* Расписание и новости*/
    val scheduleApiInterface: ApiClient
        get() = retrofitClient!!.create(ApiClient::class.java)

    companion object {
        private const val MAIN_URL = "http://mir-dverei.by/"
    }
}