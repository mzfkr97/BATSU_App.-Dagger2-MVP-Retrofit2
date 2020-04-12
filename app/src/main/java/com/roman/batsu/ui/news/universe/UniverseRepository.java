package com.roman.batsu.ui.news.universe;

import com.roman.batsu.ui.model.KurjerInfo;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface UniverseRepository {
    Observable<ArrayList<KurjerInfo>> getNewsKurjer(String url);
}
