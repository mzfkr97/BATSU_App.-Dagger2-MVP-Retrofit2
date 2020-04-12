package com.roman.batsu.ui.news.news_group;

import android.util.Log;

import androidx.annotation.NonNull;

import com.roman.batsu.ui.model.News;
import com.roman.batsu.utils.api.ApiClient;
import com.roman.batsu.utils.network.RXConnector;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsModel implements NewsContract.Model {

    private final String TAG = "NewsModel";
    private final String JSON_FILE_NAME = "dashboard_information.json";
    private RXConnector rxConnector = new RXConnector();
    @Override
    public void getMovieList(OnFinishedListener onFinishedListener) {

        ApiClient apiService = rxConnector.getScheduleApiInterface();
        Call<List<News>> call = apiService.getResponseDashBoard(JSON_FILE_NAME);


        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<News>> call, @NonNull Response<List<News>> response) {
                List<News> jsonArray = response.body();
                onFinishedListener.onFinished(jsonArray);
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<News>> call, @NonNull Throwable throwable) {
                Log.e(TAG, throwable.toString());
                onFinishedListener.onFailure(throwable);
            }
        });
    }

}
