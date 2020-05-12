package com.roman.batsu.ui.main

import com.roman.batsu.ui.model.Home

class HomePresenter internal constructor(
        private var fragmentView: HomeContract.View?
) :
        HomeContract.Presenter, HomeContract.Model.OnFinishedListener {

    //Этот класс имеет ссылку как на вью, так и на модель
    //Компоненты MVP приложения

    private val movieListModel: HomeContract.Model

    /**
     * Подаем ему вью и модель
     */
    //Обрати внимание на аргументы конструктора -
    // мы передаем экземпляр Activity Fragments, а Repository просто создаём конструктором.
    init {
        movieListModel = HomeModel()
    }

    override fun onDestroy() {
        fragmentView = null
    }

    override fun requestDataFromServer(filename: String) {
        fragmentView.let {
            it?.showProgress()
        }
        movieListModel.getMovieList(this, filename)
    }

    override fun onFinished(homeList: MutableList<Home>) {
        fragmentView?.setDataToRecyclerView(homeList)
        fragmentView.let {
            it?.hideProgress()

        }
    }


    override fun onFailure(t: Throwable) {
        fragmentView?.onResponseFailure(t)
        fragmentView.let {
            it?.hideProgress()

        }
    }

    companion object {
        //Presenter — прослойка между View и Model.
        //Presenter действует как посредник между видом и моделью.
        // Он в основном получает данные из Model и возвращает их в View для отображения. Цель.
        // View передаёт ему происходящие события, презентер обрабатывает их,
        // при необходимости обращается к Model и возращает View данные на отрисовку.
        private const val TAG = "MainPresenter"
    }


}