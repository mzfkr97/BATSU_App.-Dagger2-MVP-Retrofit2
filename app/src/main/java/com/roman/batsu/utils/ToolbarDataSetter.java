package com.roman.batsu.utils;

import android.text.Html;
import android.text.Spanned;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ToolbarDataSetter implements ToolbarDataSet {

//    //возвращает сегодняшнюю дату
//
//    public String setDataInToolbar() {
//        // Тут создадим объект SimpleDateFormat
//        Spanned sp = Html.fromHtml("\t&#149;"); // Жирная точка между датой
//        String currentDay = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());
//        String currentCount = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
//        String currentMonth = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
//        return capitalizeData(currentDay) + " " + sp + " " + currentCount + " " + capitalizeData(currentMonth);
//    }

    public static final String RESPONSE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"; // "2018-10-27T06:34:39+00:00",

    private String capitalizeData(String string){
        //месяц с большой буквы
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    @Override
    public String setData() {
        // Тут создадим объект SimpleDateFormat
        Spanned sp = Html.fromHtml("\t&#149;"); // Жирная точка между датой
        String currentDay = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());
        String currentCount = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        String currentMonth = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        return capitalizeData(currentDay) + " " + sp + " " + currentCount + " " + capitalizeData(currentMonth);
    }

    /*Принимает на вход дату в таком виде
     * 2020-03-18T06:00:00GMT+03:00
     *  и отдает Среда, 16 марта...*/
    public String dateFormatter(String oldFormat) {
        String outputFormat = "EEEE, dd MMMM";
        SimpleDateFormat utcFormat = new SimpleDateFormat(RESPONSE_FORMAT, Locale.ROOT);
        SimpleDateFormat displayedFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
        try {
            Date date = utcFormat.parse(oldFormat);
            return capitalizeData(displayedFormat.format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
