package com.roman.batsu.ui.home;

import com.roman.batsu.ui.home.pojo.Home;

import java.util.List;

public interface HomeContract {

    //то что происходит в активити / fragments
    interface View {
        void showProgress();
        void hideProgress();
        void setDataToRecyclerView(List<Home> movieArrayList);
        void onResponseFailure(Throwable throwable);
    }

    //то, что происходит в презентере
    interface Presenter {
        void onDestroy();
        void requestDataFromServer(String filename);
    }



    interface Model {
        interface OnFinishedListener {
            void onFinished(List<Home> homeList);
            void onFailure(Throwable t);
        }

        void getMovieList(OnFinishedListener onFinishedListener, String fileName);

    }

}
