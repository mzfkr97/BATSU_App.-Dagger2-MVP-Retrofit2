package com.roman.batsu.utils.api

import com.roman.batsu.ui.model.Home
import com.roman.batsu.ui.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {
    /*
     * { http://mir-dverei.by/image/schedule_82.json }
     */
    @GET("image/{fileName}")
    fun getSchedule(@Path("fileName") fileName: String?): Call<List<Home>>

    /*
     * { http://mir-dverei.by/image/dashboard_information.json }
     */
    @GET("image/{fileName}")
    fun getResponseDashBoard(@Path("fileName") fileName: String?): Call<List<News>>
}