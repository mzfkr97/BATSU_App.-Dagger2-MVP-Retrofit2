package com.roman.batsu.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import com.roman.batsu.R;

public class ColorMaker {

    private Context context;

    private int[] myDraw = {
            R.drawable.a_arrow,
            R.drawable.a_books,
            R.drawable.a_calculator,
            R.drawable.a_hands,
            R.drawable.a_hourglass,
            R.drawable.a_landscape,
            R.drawable.a_money,
            R.drawable.a_stock
    };
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






}
