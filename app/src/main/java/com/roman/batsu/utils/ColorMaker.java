package com.roman.batsu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.preference.PreferenceManager;

public class ColorMaker {

    private Context context;

    public ColorMaker(Context context) {
        this.context = context;
    }


    /*
     возвращает случайный цвет из массива
    holder.text_dot.setTextColor(new ColorMaker(mContext).getRandomMaterialColor("400" ));
    */
    public int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = context.getResources()
                .getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }


    /*
    определяем ночную тему
    */
    public boolean nightMode() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String themeName = sharedPreferences.getString(Constants.THEME, Constants.THEME_LIGHT);
        boolean nightTheme = false;
        if (themeName.equals(Constants.THEME_DARK)) {
            nightTheme = true;
        }
        return nightTheme;
    }

    /*
    возвращает цвет в зависимости от установленной темы
     - ночь или день
    */
//    public int getNightModeColor(){
//        int  color = ContextCompat.getColor(context, R.color.colorPrimary);
//        if (nightMode()){
//            color = ContextCompat.getColor(context, R.color.colorPrimaryDark_night);
//        }
//        return color;
//    }

}
