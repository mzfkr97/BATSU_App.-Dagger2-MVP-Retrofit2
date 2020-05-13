package com.roman.batsu.ui.main

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.roman.batsu.R
import com.roman.batsu.databinding.RecyclerSwipeRefreshBinding
import com.roman.batsu.ui.main.adapter.HomeAdapter
import com.roman.batsu.ui.model.Home
import com.roman.batsu.utils.network.NetworkChecker
import java.util.*

class HomeFragment : Fragment(), HomeContract.View {

    private var mLastClickTime: Long = 0
    private lateinit var movieListPresenter: HomePresenter
    private var moviesList: MutableList<Home> = ArrayList()
    lateinit var homeFragBindingUtil: RecyclerSwipeRefreshBinding
    private var adapter = HomeAdapter()
    private val fileNameFactory = FileNameFactory()

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeFragBindingUtil =
                DataBindingUtil.inflate(
                        inflater,
                        R.layout.recycler_swipe_refresh, container, false)
        // presenter
        movieListPresenter = HomePresenter(this)

        homeFragBindingUtil.apply {
            homeFragBindingUtil.recyclerView
            homeFragBindingUtil.progressBar.visibility = VISIBLE
            homeFragBindingUtil.errorView.cardNotification.visibility = GONE
        }

        homeFragBindingUtil.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            homeFragBindingUtil.recyclerView.adapter = adapter
        }

        homeFragBindingUtil.swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

        callToServer()
        homeFragBindingUtil.swipeContainer.setOnRefreshListener {
            homeFragBindingUtil.progressBar.visibility = VISIBLE
            callToServer()
        }
        homeFragBindingUtil.errorView.buttonError.setOnClickListener { callToServer() }
        return homeFragBindingUtil.root
    }

    private fun callToServer() {
        if (netWorkNotAvailable()) {
            val filenamePosition = arguments?.getInt(ARG_SECTION_NUMBER)
            getNewsData(fileNameFactory.fileName(filenamePosition))
        } else {
            showError()
        }
    }

    private fun showError() {
        homeFragBindingUtil.errorView.cardNotification.visibility = VISIBLE
        homeFragBindingUtil.progressBar.visibility = GONE
        homeFragBindingUtil.swipeContainer.isRefreshing = false
        moviesList.clear()
    }

    private fun netWorkNotAvailable(): Boolean = NetworkChecker.isNetworkAvailable(context)

    private fun getNewsData(fileName: String?) {
        homeFragBindingUtil.progressBar.visibility =VISIBLE
        if (SystemClock.elapsedRealtime() - mLastClickTime < 20000) {
            homeFragBindingUtil.swipeContainer.isRefreshing = false
            homeFragBindingUtil. progressBar.visibility = GONE
            return
        }
        fileName?.let { movieListPresenter.requestDataFromServer(it) }
        mLastClickTime = SystemClock.elapsedRealtime()
    }

    override fun onDestroy() {
        super.onDestroy()
        movieListPresenter.onDestroy()
    }

    override fun showProgress() {
        homeFragBindingUtil.progressBar.visibility = VISIBLE
    }

    override fun hideProgress() {
        homeFragBindingUtil.progressBar.visibility = GONE
        homeFragBindingUtil.errorView.cardNotification.visibility = GONE
        homeFragBindingUtil.swipeContainer.isRefreshing = false
    }

    override fun setDataToRecyclerView(movieArrayList: MutableList<Home>) {
        homeFragBindingUtil.recyclerView.adapter = adapter
        adapter.submitList(movieArrayList)
    }

    override fun onResponseFailure(throwable: Throwable) {
        showError()
    }

}