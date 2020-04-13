package com.roman.batsu.utils

import android.text.Html
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ToolbarDataSetter : ToolbarDataSet {


    companion object {
        const val RESPONSE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss" // "2018-10-27T06:34:39+00:00",
    }

    //месяц с большой буквы
    private fun capitalizeData(string: String): String =
            string.substring(0, 1).toUpperCase(Locale.ROOT) + string.substring(1)


    override fun setData(): String {
        // Тут создадим объект SimpleDateFormat
        val sp = Html.fromHtml("\t&#149;") // Жирная точка между датой
        val currentDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
        val currentCount = SimpleDateFormat("dd", Locale.getDefault()).format(Date())
        val currentMonth = SimpleDateFormat("MMMM", Locale.getDefault()).format(Date())
        return capitalizeData(currentDay) + " " + sp + " " + currentCount + " " + capitalizeData(currentMonth)
    }

    /*Принимает на вход дату в таком виде
     * 2020-03-18T06:00:00GMT+03:00
     *  и отдает Среда, 16 марта...*/
    fun dateFormatter(oldFormat: String?): String {
        val outputFormat: String = "EEEE, dd MMMM"
        val utcFormat = SimpleDateFormat(RESPONSE_FORMAT, Locale.ROOT)
        val displayedFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        return try {
            val date = utcFormat.parse(oldFormat)
            capitalizeData(displayedFormat.format(date))
        } catch (e: ParseException) {
            throw RuntimeException(e)
        }
    }
}