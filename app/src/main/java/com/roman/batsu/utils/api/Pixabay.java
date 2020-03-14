package com.roman.batsu.utils.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Pixabay {
    // key 15597369-5567e6bdc1cae1f204a520e49
    // user: slutskapp@gmail.com

    // docs https://pixabay.com/api/docs/

    // example   https://pixabay.com/api/
    // ?key=15597369-5567e6bdc1cae1f204a520e49&q=green+flowers&image_type=photo&pretty=false


    @GET("image/{fileName}")
    Call<List<PixabayResponse>> getPixabayPhoto(@Path("fileName") String fileName);
}
