package com.roman.batsu.ui.news.news_group;

import com.roman.batsu.ui.model.News;

import java.util.List;

public class NewsPresenter implements NewsContract.Presenter, NewsContract.Model.OnFinishedListener {

    // View передаёт ему происходящие события, презентер обрабатывает их,
    // при необходимости обращается к Model и возращает View данные на отрисовку.

    private static final String TAG = "NewsPresenter";

    //Этот класс имеет ссылку как на вью, так и на модель
    //Компоненты MVP приложения
    private NewsContract.View fragmentView;
    private NewsContract.Model newsListModel;

    NewsPresenter(NewsContract.View fragmentView) {
        this.fragmentView = fragmentView;
        this.newsListModel = new NewsModel();
    }

    @Override
    public void onDestroy() {
        this.fragmentView = null;
    }

    @Override
    public void requestDataFromServer() {
        if (fragmentView != null) {
            fragmentView.showProgress();
        }
        newsListModel.getMovieList(this);
    }

    @Override
    public void onFinished(List<News> newsList) {
        fragmentView.setDataToRecyclerView(newsList);
        if (fragmentView != null) {
            fragmentView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        fragmentView.onResponseFailure(t);
        if (fragmentView != null) {
            fragmentView.hideProgress();
        }
    }
}
