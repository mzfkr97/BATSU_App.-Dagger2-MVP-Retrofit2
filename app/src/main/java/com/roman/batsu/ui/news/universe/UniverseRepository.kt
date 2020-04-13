package com.roman.batsu.ui.news.universe

import com.roman.batsu.ui.model.KurjerInfo
import io.reactivex.Observable
import java.util.*

interface UniverseRepository {
    fun getNewsKurjer(url: String): Observable<ArrayList<KurjerInfo>>
}