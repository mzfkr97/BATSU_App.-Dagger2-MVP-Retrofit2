package com.roman.batsu.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.roman.batsu.R;
import com.roman.batsu.ui.model.News;
import com.roman.batsu.ui.news.adapter.NewsAdapter;
import com.roman.batsu.utils.Constants;
import com.roman.batsu.utils.network.NetworkChecker;
import com.roman.batsu.utils.network.RXConnector;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements NewsAdapter.DashboardItemClick, NewsContract.View {

    private RecyclerView recyclerView;
    private RXConnector rxConnector;
    private List<News> dashboardList = new ArrayList<>();
    private long mLastClickTime = 0;
    private CardView cardNotification;
    private Button button_error;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;
    private NewsPresenter newsPresenter;
    private NewsAdapter adapter;


    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_swipe_refresh, container, false);
        newsPresenter = new NewsPresenter(this);

        viewInit(view);
        setUpRecyclerView(view);

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressBar.setVisibility(View.VISIBLE);
                getNewsData();
                netWorkCheck();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        progressBar.setVisibility(View.VISIBLE);
        netWorkCheck();
        getNewsData();

        button_error.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            netWorkCheck();
            getNewsData();

        });

        return view;
    }

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void viewInit(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        cardNotification = view.findViewById(R.id.cardNotification);
        button_error = view.findViewById(R.id.button_error);
    }

    private void netWorkCheck() {
        if (getActivity() != null) {
            if (!NetworkChecker.isNetworkAvailable(getActivity())) {
                cardNotification.setVisibility(View.VISIBLE);
                swipeContainer.setRefreshing(false);
            }
        }
    }

    private void getNewsData() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 20000) {
            swipeContainer.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            return;
        }
        newsPresenter.requestDataFromServer();
        mLastClickTime = SystemClock.elapsedRealtime();
    }


    @Override
    public void onClickPopup(News item, View view) {
        final String title = item.getTitle();
        final String description = item.getDescription();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + description );
        sendIntent.setType("text/plain");
        try {
            startActivity(Intent.createChooser(sendIntent, getString(R.string.share_popup)));
        } catch (android.content.ActivityNotFoundException ex) {
            Log.d(Constants.TAG, "ActivityNotFoundException" + ex);
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        cardNotification.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        cardNotification.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void setDataToRecyclerView(List<News> movieArrayList) {
        dashboardList.clear();
        dashboardList.addAll(movieArrayList);
        adapter = new NewsAdapter(dashboardList);
        recyclerView.setAdapter(adapter);
        adapter.setAutoOnItemClickListener(this);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        cardNotification.setVisibility(View.VISIBLE);
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsPresenter.onDestroy();
    }
}