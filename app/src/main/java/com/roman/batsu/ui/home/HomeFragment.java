package com.roman.batsu.ui.home;

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
import com.roman.batsu.ui.home.adapter.HomeAdapter;
import com.roman.batsu.ui.model.Home;
import com.roman.batsu.utils.network.NetworkChecker;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private HomeAdapter adapter;
    private RecyclerView recyclerView;
    private CardView cardNotification;
    private Button button_error;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;
    private long mLastClickTime = 0;
    private HomePresenter movieListPresenter;
    private List<Home> moviesList;

    static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_swipe_refresh, container, false);

        initUI(view);
        //Initializing presenter
        movieListPresenter = new HomePresenter(this);
        getNewsData(getFileName());


        swipeContainer.setOnRefreshListener(() -> {
            progressBar.setVisibility(View.VISIBLE);
            getNewsData(getFileName());
            netWorkCheck();
        });



        progressBar.setVisibility(View.VISIBLE);
        netWorkCheck();

        button_error.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            netWorkCheck();



        });
        return view;
    }

    private void initUI(View view) {
        moviesList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomeAdapter(getActivity(), moviesList);
        recyclerView.setAdapter(adapter);

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        progressBar = view.findViewById(R.id.progressBar);
        cardNotification = view.findViewById(R.id.cardNotification);
        button_error = view.findViewById(R.id.button_error);
    }


    private String getFileName(){
        String fileName = null;
        try {
            assert getArguments() != null;
            int arg = getArguments().getInt(ARG_SECTION_NUMBER);

            switch (arg) {
                case 0:
                    fileName = "schedule_82.json";
                    break;
                case 1:
                    fileName = "schedule_83.json";
                    break;
                case 2:
                    fileName = "schedule_84.json";
                    break;
                    default:
            }
        }catch (Exception e){
            Log.d("TAG", "getMyInputStream: " + e);
        }
        return fileName;

    }



    private void netWorkCheck() {
        if (getActivity() != null) {
            if (!NetworkChecker.isNetworkAvailable(getActivity())) {
                cardNotification.setVisibility(View.VISIBLE);
                swipeContainer.setRefreshing(false);
            }
        }
    }


    private void getNewsData(String fileName) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 20000) {
            swipeContainer.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            return;

        }
        movieListPresenter.requestDataFromServer(fileName);
        mLastClickTime = SystemClock.elapsedRealtime();

    }

    private void onError(){
        progressBar.setVisibility(View.GONE);
        cardNotification.setVisibility(View.VISIBLE);
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieListPresenter.onDestroy();
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
    public void setDataToRecyclerView(List<Home> movieArrayList) {
        moviesList.clear();
        moviesList.addAll(movieArrayList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        onError();
        Log.d("TAG", "onResponseFailure: ");
    }
}