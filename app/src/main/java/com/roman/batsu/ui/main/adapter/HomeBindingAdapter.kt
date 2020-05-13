package com.roman.batsu.ui.main.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.roman.batsu.ui.model.Home
import com.roman.batsu.utils.ColorMaker


@BindingAdapter("setBackgroundColor")
fun TextView.setBackgroundColor(itemHome: Home?) {
    itemHome?.let {
        setBackgroundColor(ColorMaker(context).getRandomMaterialColor("400"))
    }
}
