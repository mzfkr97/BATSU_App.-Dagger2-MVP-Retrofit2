package com.roman.batsu.ui.news.universe

import com.roman.batsu.ui.model.KurjerInfo
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.util.*

class JsoupRepository : UniverseRepository {
    //https://dajver.blogspot.com/2017/11/rx-android.html
    override fun getNewsKurjer(url: String): Observable<ArrayList<KurjerInfo>> {
        return Observable.create { observableEmitter: ObservableEmitter<ArrayList<KurjerInfo>> ->
            val articlesModels = ArrayList<KurjerInfo>()
            var doc: Document? = null
            try {
                doc = Jsoup.connect(url).get()
                //
//                Elements titleElement = doc.getElementsByClass("title");
//                Elements imageElement = doc.getElementsByClass("cover-image");
                val viewsRow = doc.select("div.views-row")


                //for(int i = 0; i < views_row.size(); i++) {
                for (element in viewsRow) {
                    val header = element.select("header")
                    val title = header.select("span").attr("content")
                    val description = element.select("div.field.field-name-body.field-type-text-with-summary.field-label-hidden").text()
                    val author = element
                            .select("div.field.field-name-field-novost-avtor.field-type-entityreference.field-label-hidden")
                            .select("a")
                            .text()
                    val date = element
                            .select("span.date-display-single")
                            .attr("content")
                    val img = element
                            .select("img.img-responsive")
                            .attr("abs:src")
                    val web_link = element
                            .select("article.node")
                            .attr("abs:about")
                    val universeNews = KurjerInfo(
                            img,
                            title,
                            description,
                            author,
                            date,
                            web_link
                    )
                    articlesModels.add(universeNews)
                }
                observableEmitter.onNext(articlesModels)
            } catch (e: IOException) {
                observableEmitter.onError(e)
            } finally {
                observableEmitter.onComplete()
            }
        }
    }
}