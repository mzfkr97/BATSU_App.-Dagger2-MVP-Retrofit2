package com.example.batsu.utils;

import android.text.Html;
import android.text.Spanned;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ToolbarDataSetter {

    //возвращает сегодняшнюю дату

    public String setDataInToolbar() {
        // Тут создадим объект SimpleDateFormat
        Spanned sp = Html.fromHtml("\t&#149;"); // Жирная точка между датой
        String currentDay = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());
        String currentCount = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        String currentMonth = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        return capitalizeData(currentDay) + " " + sp + " " + currentCount + " " + capitalizeData(currentMonth);
    }


    private String capitalizeData(String string){
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
