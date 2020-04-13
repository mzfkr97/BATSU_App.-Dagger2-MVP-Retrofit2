package com.roman.batsu.ui.main

import com.roman.batsu.ui.model.Home

interface HomeContract {
    //то что происходит в активити / fragments
    interface View {
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(movieArrayList: List<Home>)
        fun onResponseFailure(throwable: Throwable)
    }

    //то, что происходит в презентере
    interface Presenter {
        fun onDestroy()
        fun requestDataFromServer(filename: String)
    }

    interface Model {
        interface OnFinishedListener {
            fun onFinished(homeList: List<Home>)
            fun onFailure(t: Throwable)
        }

        fun getMovieList(onFinishedListener: OnFinishedListener, fileName: String)
    }
}