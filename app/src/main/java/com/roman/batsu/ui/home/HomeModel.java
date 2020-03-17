package com.roman.batsu.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;

import com.roman.batsu.ui.home.pojo.Home;
import com.roman.batsu.utils.api.ApiClient;
import com.roman.batsu.utils.application.MyApplication;
import com.roman.batsu.utils.network.RXConnector;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class HomeModel implements HomeContract.Model {

    //Model (Activity) — уровень данных.
    // Не люблю использовать термин «бизнес логика»,
    // поэтому в своих приложениях я называю его Repository и
    // он общается с базой данных и сетью.
    //Repository должен заниматься ТОЛЬКО записью и чтением
    //тот класс будет иметь актуальную бизнес-логику для извлечения данных с сервера.
    // Этот класс будет реализовывать интерфейс модели из интерфейса контракта, указанного выше.

    private final String TAG = "MovieListModel";
    private RXConnector rxConnector;

    /**
     Эта функция будет извлекать данные фильмов
     * @param onFinishedListener
     * @param fileName: Имя файла.
     */

    @Override
    public void getMovieList(OnFinishedListener onFinishedListener, String fileName) {
        rxConnector =  MyApplication.getComponent().getRxConnector();
        ApiClient apiService = rxConnector.getScheduleApiInterface();

        retrofit2.Call<List<Home>> call = apiService.getSchedule(fileName);
        call.enqueue(new Callback<List<Home>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Home>> call, @NonNull Response<List<Home>> response) {


                if( response.body() != null){
                    List<Home> jsonArray = response.body();
                    onFinishedListener.onFinished(jsonArray);
                }else {
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Home>> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }
}
