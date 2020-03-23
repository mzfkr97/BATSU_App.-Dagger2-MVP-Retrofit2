package com.roman.batsu.ui.news.universe;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.roman.batsu.R;
import com.roman.batsu.ui.model.UniverseNews;
import com.roman.batsu.ui.news.adapter.UniverseAdapter;
import com.roman.batsu.utils.network.NetworkChecker;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UniverseNewsFrag extends Fragment {
    private RecyclerView recyclerView;
    private List<UniverseNews> dashboardList = new ArrayList<>();
    private UniverseAdapter adapter;
    private long mLastClickTime = 0;
    private CardView cardNotification;
    private Button button_error;
    private ProgressBar progressBar;

    private SwipeRefreshLayout swipeContainer;
    public static final String URL = "http://www.bsatu.by/ru/novosti";



    public static UniverseNewsFrag newInstance() {

        return new UniverseNewsFrag();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_swipe_refresh, container, false);
        viewInit(view);
        setUpRecyclerView(view);



        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(() -> {
            progressBar.setVisibility(View.VISIBLE);
            callToServer();
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        progressBar.setVisibility(View.VISIBLE);


        button_error.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            callToServer();

        });

        callToServer();
        return view;
    }



    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        cardNotification.setVisibility(View.GONE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        cardNotification.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
    }

    private void getNewsData() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 20000) {
            swipeContainer.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            return;
        }
        showProgress();
        new RepositoryImpl().getArticles(URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<UniverseNews>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(ArrayList<UniverseNews> articlesModels) {
                        UniverseAdapter articlesRecyclerAdapter = new UniverseAdapter( articlesModels);
                        recyclerView.setAdapter(articlesRecyclerAdapter);
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showError();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        mLastClickTime = SystemClock.elapsedRealtime();
    }

    private void callToServer() {
        if (netWorkAvailable()) {
            getNewsData();

        } else {
            showError();
        }
    }




    private boolean netWorkAvailable() {
        return NetworkChecker.isNetworkAvailable(getActivity());
    }


    private void showError() {
        cardNotification.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        dashboardList.clear();
    }

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UniverseAdapter(dashboardList);
    }

    private void viewInit(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        cardNotification = view.findViewById(R.id.cardNotification);
        button_error = view.findViewById(R.id.button_error);
    }

}
