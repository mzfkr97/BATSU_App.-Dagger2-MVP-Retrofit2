package com.roman.batsu.ui.news.universe;

import com.roman.batsu.ui.model.UniverseNews;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface UniverseRepository {
    Observable<ArrayList<UniverseNews>> getArticles(String url);
}
