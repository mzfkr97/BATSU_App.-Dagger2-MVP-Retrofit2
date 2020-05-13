package com.roman.batsu.ui.news.universe

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.roman.batsu.R
import com.roman.batsu.ui.model.KurjerInfo
import com.roman.batsu.ui.news.adapter.UniverseAdapter
import com.roman.batsu.utils.network.NetworkChecker
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class UniverseNewsFrag : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val dashboardList: MutableList<KurjerInfo> = ArrayList()
    private lateinit var adapter: UniverseAdapter
    private var mLastClickTime: Long = 0
    private lateinit var cardNotification: CardView
    private lateinit var buttonError: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_universe, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        cardNotification = view.findViewById(R.id.cardNotification)
        buttonError = view.findViewById(R.id.buttonError)
        setUpRecyclerView(view)
        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener(OnRefreshListener {
            progressBar.visibility = View.VISIBLE
            callToServer()
        })
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        progressBar.visibility = View.VISIBLE
        buttonError.setOnClickListener(View.OnClickListener { v: View? ->
            progressBar.visibility = View.VISIBLE
            callToServer()
        })
        callToServer()
        return view
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
        cardNotification.visibility = View.GONE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
        cardNotification.visibility = View.GONE
        swipeContainer.isRefreshing = false
    }

    private fun callToServer() {

        if (netWorkAvailable()) {
            newsData
        } else {
            showError()
        }
    }

    private val newsData: Unit
        get() {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 20000) {
                swipeContainer.isRefreshing = false
                progressBar.visibility = View.GONE
                return
            }
            JsoupRepository().getNewsKurjer(URL)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<ArrayList<KurjerInfo>> {
                        override fun onSubscribe(d: Disposable) {
                            showProgress()
                            Log.d("TAG", "onSubscribe: ")
                        }

                        override fun onNext(articlesModels: ArrayList<KurjerInfo>) {
                            adapter = UniverseAdapter(articlesModels)
                            recyclerView.adapter = adapter
                        }

                        override fun onError(e: Throwable) {
                            showError()
                        }

                        override fun onComplete() {
                            hideProgress()
                            Log.d("TAG", "onComplete: ")
                        }
                    })
            mLastClickTime = SystemClock.elapsedRealtime()
        }

    private fun netWorkAvailable(): Boolean = NetworkChecker.isNetworkAvailable(activity)

    private fun showError() {
        Log.d("TAG", "showError: ")
        cardNotification.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        swipeContainer.isRefreshing = false
        dashboardList.clear()
    }

    private fun setUpRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = UniverseAdapter(dashboardList)
    }

    companion object {
        const val URL = "http://www.bsatu.by/ru/novosti"
        fun newInstance(): UniverseNewsFrag = UniverseNewsFrag()
    }
}