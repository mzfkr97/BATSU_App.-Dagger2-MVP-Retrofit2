package com.example.batsu.utils.api;

import com.example.batsu.ui.dashboard.ResponceDashboard.ResponseDashboard;
import com.example.batsu.ui.home.pojos.InputResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiSchedule {

    /**
     * { http://mir-dverei.by/image/schedule_82.json }
     */

    @GET("image/{fileName}")
    Call <List<InputResult>> getSchedule(@Path("fileName") String fileName);

    /**
     * { http://mir-dverei.by/image/dashboard_information.json }
     */

    @GET("image/{fileName}")
    Call <List<ResponseDashboard>> getResponseDashBoard(@Path("fileName") String fileName);


}
