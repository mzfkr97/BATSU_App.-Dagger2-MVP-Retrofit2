package com.roman.batsu.utils.api;

import com.roman.batsu.ui.news.News;
import com.roman.batsu.ui.home.Home;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiSchedule {

    /**
     * { http://mir-dverei.by/image/schedule_82.json }
     */

    @GET("image/{fileName}")
    Call <List<Home>> getSchedule(@Path("fileName") String fileName);

    /**
     * { http://mir-dverei.by/image/dashboard_information.json }
     */

    @GET("image/{fileName}")
    Call <List<News>> getResponseDashBoard(@Path("fileName") String fileName);


}
