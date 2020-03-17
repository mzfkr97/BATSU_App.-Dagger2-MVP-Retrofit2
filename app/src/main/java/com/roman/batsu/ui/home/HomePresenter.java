package com.roman.batsu.ui.home;

import com.roman.batsu.ui.home.pojo.Home;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter, HomeContract.Model.OnFinishedListener {


    //Presenter — прослойка между View и Model.
    //Presenter действует как посредник между видом и моделью.
    // Он в основном получает данные из Model и возвращает их в View для отображения. Цель.
    // View передаёт ему происходящие события, презентер обрабатывает их,
    // при необходимости обращается к Model и возращает View данные на отрисовку.

    private static final String TAG = "MainPresenter";

    //Этот класс имеет ссылку как на вью, так и на модель
    //Компоненты MVP приложения
    private HomeContract.View fragmentView;
    private HomeContract.Model movieListModel;


/** Подаем ему вью и модель*/
    //Обрати внимание на аргументы конструктора -
    // мы передаем экземпляр Activity Fragments, а Repository просто создаём конструктором.
    public HomePresenter(HomeContract.View fragmentView) {
        this.fragmentView = fragmentView;
        this.movieListModel = new HomeModel();
    }

    @Override
    public void onDestroy() {
        this.fragmentView = null;
    }


    @Override
    public void requestDataFromServer(String filename) {
        if (fragmentView != null) {
            fragmentView.showProgress();
        }
        movieListModel.getMovieList(this, filename);
    }

    @Override
    public void onFinished(List<Home> homeList) {
        fragmentView.setDataToRecyclerView(homeList);
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
