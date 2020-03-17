package com.roman.batsu.ui.news;

import com.roman.batsu.ui.model.News;

import java.util.List;

public interface NewsContract {

    //то что происходит в активити / fragments
    interface View {
        void showProgress();
        void hideProgress();
        void setDataToRecyclerView(List<News> movieArrayList);
        void onResponseFailure(Throwable throwable);
    }

    //то, что происходит в презентере
    interface Presenter {
        void onDestroy();
        void requestDataFromServer();
    }

    interface Model {
        interface OnFinishedListener {
            void onFinished(List<News> newsList);
            void onFailure(Throwable t);
        }
        void getMovieList(OnFinishedListener onFinishedListener);
    }
}
