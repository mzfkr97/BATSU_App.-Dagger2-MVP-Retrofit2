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
import com.roman.batsu.utils.Constants;
import com.roman.batsu.utils.api.ApiClient;
import com.roman.batsu.utils.application.MyApplication;
import com.roman.batsu.utils.network.NetworkChecker;
import com.roman.batsu.utils.network.RXConnector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment implements NewsAdapter.DashboardItemClick {

    private RecyclerView recyclerView;
    private RXConnector rxConnector;
    private List<News> dashboardList = new ArrayList<>();
    private long mLastClickTime = 0;
    private CardView cardNotification;
    private Button button_error;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;


    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_swipe_refresh, container, false);
        viewInit(view);
        setUpRecyclerView(view);

        rxConnector =  MyApplication.getComponent().getRxConnector();
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

        //inputStream = getResources().openRawResource(R.raw.dashboard_information);

       // dashboardList = jsonReader.createListFromJsonDashboard(inputStream);
        progressBar.setVisibility(View.VISIBLE);
        netWorkCheck();
        getNewsData();

        button_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                netWorkCheck();
                getNewsData();

            }
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
        mLastClickTime = SystemClock.elapsedRealtime();

        ApiClient apiService = rxConnector.getScheduleApiInterface();
        Call<List<News>> call = apiService.getResponseDashBoard("dashboard_information.json");
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>>  call, Response<List<News>> response) {
                if( response.body() != null){
                    dashboardList = response.body();
                    createListData(dashboardList);
                } else {
                    onError();
                    Log.d("TAG", "onFailure: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                onError();
            }
        });
    }

    private void onError(){
        progressBar.setVisibility(View.GONE);
        cardNotification.setVisibility(View.VISIBLE);
        swipeContainer.setRefreshing(false);
    }

    private void createListData(List<News> listData) {
        NewsAdapter adapter = new NewsAdapter(listData);
        recyclerView.setAdapter(adapter);
        cardNotification.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        adapter.setAutoOnItemClickListener(this);
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
}