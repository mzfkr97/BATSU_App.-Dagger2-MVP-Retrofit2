package com.roman.batsu.ui.news.universe;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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
import com.roman.batsu.utils.network.NetworkChecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private UniverseNews universeNews;
    private Parser parser;
    String imageB;

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
            getNewsData();
            netWorkCheck();
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

    private void netWorkCheck() {
        if (getActivity() != null) {
            if (!NetworkChecker.isNetworkAvailable(getActivity())) {
                cardNotification.setVisibility(View.VISIBLE);
                swipeContainer.setRefreshing(false);
            }
        }
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
        parser = new Parser();
        parser.execute();
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

    @SuppressLint("StaticFieldLeak")
    class Parser extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            dashboardList.clear();
            Document doc = null;
            try {
                doc = Jsoup.connect(URL)
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com")
                        .get();

                Elements views_row = doc.select("div.views-row");


                //Elements h2 = article.select("h2");
                // Elements header = article.select("h2");
                for (Element element : views_row) {

                    Elements header = element.select("header");
                    String title = header.select("span").attr("content");
                    String description = element.select("div.field.field-name-body.field-type-text-with-summary.field-label-hidden").text();
                    String author = element.select("div.field.field-name-field-novost-avtor.field-type-entityreference.field-label-hidden").select("a").text();
                    String date = element.select("span.date-display-single").attr("content");



                    String img = element.select("img.img-responsive").attr("abs:src");
                    String web_link = element.select("article.node").attr("abs:about");

                    universeNews = new UniverseNews(
                            img,
                            title,
                            description,
                            author,
                            date,
                            web_link
                    );
                    dashboardList.add(universeNews);
                }


            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            hideProgress();
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
