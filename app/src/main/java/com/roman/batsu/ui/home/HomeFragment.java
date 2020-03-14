package com.roman.batsu.ui.home;

import android.os.Bundle;
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
import com.roman.batsu.ui.home.pojos.InputResult;
import com.roman.batsu.utils.api.ApiSchedule;
import com.roman.batsu.utils.application.MyApplication;
import com.roman.batsu.utils.network.NetworkChecker;
import com.roman.batsu.utils.network.RXConnector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RXConnector rxConnector;
    private ArrayList<InputResult> articlesList = new ArrayList<>();
    private HomeAdapter adapter;
    private RecyclerView recyclerView;
    private CardView cardNotification;
    private Button button_error;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;

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
        viewInit(view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        rxConnector =  MyApplication.getComponent().getRxConnector();

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(() -> {
            progressBar.setVisibility(View.VISIBLE);
            getNewsData(getFileName());
            netWorkCheck();
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        progressBar.setVisibility(View.VISIBLE);
        netWorkCheck();
        getNewsData(getFileName());

        button_error.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            netWorkCheck();
            getNewsData(getFileName());

        });
        return view;
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


    private void viewInit(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        cardNotification = view.findViewById(R.id.cardNotification);
        button_error = view.findViewById(R.id.button_error);

    }


    private void getNewsData(String fileName) {
        ApiSchedule apiService = rxConnector.getScheduleApiInterface();
        Call<List<InputResult>> call = apiService.getSchedule(fileName);
        call.enqueue(new Callback<List<InputResult>> () {
            @Override
            public void onResponse(@NonNull Call<List<InputResult>>  call, @NonNull Response<List<InputResult>>  response) {

                if( response.body() != null){
                    List<InputResult> jsonArray = response.body();
                    adapter = new HomeAdapter(getActivity(), jsonArray);
                    recyclerView.setAdapter(adapter);
                    cardNotification.setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }else {
                    onError();
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<InputResult>> call, @NonNull Throwable t) {
                onError();
            }

        });


    }

    private void onError(){
        progressBar.setVisibility(View.GONE);
        cardNotification.setVisibility(View.VISIBLE);
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}