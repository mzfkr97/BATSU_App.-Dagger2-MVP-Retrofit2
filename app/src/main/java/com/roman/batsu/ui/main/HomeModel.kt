package com.roman.batsu.ui.main

import android.util.Log
import com.roman.batsu.ui.model.Home
import com.roman.batsu.utils.network.RXConnector
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeModel : HomeContract.Model {
    //Model (Activity) — уровень данных.
    // Не люблю использовать термин «бизнес логика»,
    // поэтому в своих приложениях я называю его Repository и
    // он общается с базой данных и сетью.
    //Repository должен заниматься ТОЛЬКО записью и чтением
    //тот класс будет иметь актуальную бизнес-логику для извлечения данных с сервера.
    // Этот класс будет реализовывать интерфейс модели из интерфейса контракта, указанного выше.
    private val TAG = "MovieListModel"
    private val rxConnector = RXConnector()

    /**
     * Эта функция будет извлекать данные файлов
     * @param onFinishedListener
     * @param fileName: Имя файла.
     */
    override fun getMovieList(onFinishedListener: HomeContract.Model.OnFinishedListener, fileName: String) {
        val apiService = rxConnector.scheduleApiInterface
        val call = apiService.getSchedule(fileName)
        call.enqueue(object : Callback<List<Home>> {
            override fun onResponse(call: Call<List<Home>>, response: Response<List<Home>>) {
                val jsonArray = response.body()
                jsonArray?.let { onFinishedListener.onFinished(it) }
            }

            override fun onFailure(call: Call<List<Home>>, throwable: Throwable) {
                Log.e(TAG, throwable.toString())
                onFinishedListener.onFailure(throwable)
            }
        })
    }
}