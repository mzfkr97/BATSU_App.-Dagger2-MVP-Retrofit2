package com.roman.batsu.ui.news.universe;

import com.roman.batsu.ui.model.KurjerInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;

public class JsoupRepository implements UniverseRepository {


    //https://dajver.blogspot.com/2017/11/rx-android.html
    @Override
    public Observable<ArrayList<KurjerInfo>> getNewsKurjer(String urlLink) {
        return Observable.create(observableEmitter -> {
            ArrayList<KurjerInfo> articlesModels = new ArrayList<>();
            Document doc = null;
            try {
                doc = Jsoup.connect(urlLink).get();
//
//                Elements titleElement = doc.getElementsByClass("title");
//                Elements imageElement = doc.getElementsByClass("cover-image");

                Elements views_row = doc.select("div.views-row");


                //for(int i = 0; i < views_row.size(); i++) {
                for (Element element : views_row) {
                    Elements header = element.select("header");
                    String title = header.select("span").attr("content");
                    String description = element.select("div.field.field-name-body.field-type-text-with-summary.field-label-hidden").text();
                    String author = element
                            .select("div.field.field-name-field-novost-avtor.field-type-entityreference.field-label-hidden")
                            .select("a")
                            .text();
                    String date = element
                            .select("span.date-display-single")
                            .attr("content");
                    String img = element
                            .select("img.img-responsive")
                            .attr("abs:src");
                    String web_link = element
                            .select("article.node")
                            .attr("abs:about");

                    KurjerInfo universeNews = new KurjerInfo(
                            img,
                            title,
                            description,
                            author,
                            date,
                            web_link
                    );


                    articlesModels.add(universeNews);
                }
                observableEmitter.onNext(articlesModels);
            } catch (IOException e) {
                observableEmitter.onError(e);
            } finally {
                observableEmitter.onComplete();
            }
        });
    }
}
