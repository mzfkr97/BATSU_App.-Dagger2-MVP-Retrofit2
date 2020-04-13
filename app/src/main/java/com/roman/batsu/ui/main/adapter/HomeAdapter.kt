package com.roman.batsu.ui.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roman.batsu.R
import com.roman.batsu.ui.main.adapter.HomeAdapter.MyViewHolder
import com.roman.batsu.ui.model.Home
import com.roman.batsu.utils.ColorMaker

class HomeAdapter(
        private val mContext: Context,
        private val responseList: List<Home>
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = responseList[position].day
        holder.dateText.text = responseList[position].date
        holder.textDrop.setTextColor(ColorMaker(mContext).getRandomMaterialColor("400"))
        val empty = "size =   " + responseList[position].lessons.size
        try {
            holder.countFirstLesson.text = responseList[position].lessons[0].count
            holder.textFirstLess.text = responseList[position].lessons[0].lesson_name
            holder.countSecondLesson.text = responseList[position].lessons[1].count
            holder.textSecondLesson.text = responseList[position].lessons[1].lesson_name
            holder.countThreeLesson.text = responseList[position].lessons[2].count
            holder.textThreeLesson.text = responseList[position].lessons[2].lesson_name
            holder.countFourLesson.text = responseList[position].lessons[3].count
            holder.textFourLesson.text = responseList[position].lessons[3].lesson_name
        } catch (e: Exception) {
            Log.d("TAG", "onBindViewHolder: $e$empty")
        }
    }

    override fun getItemCount(): Int = responseList.size

    inner class MyViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val textDrop: TextView = view.findViewById(R.id.textDrop)
        val title: TextView = view.findViewById(R.id.title)
        val dateText: TextView = view.findViewById(R.id.dateText)
        val textFirstLess: TextView = view.findViewById(R.id.textFirstLess)
        val textSecondLesson: TextView = view.findViewById(R.id.textSecondLesson)
        val textThreeLesson: TextView = view.findViewById(R.id.textThreeLesson)
        val textFourLesson: TextView = view.findViewById(R.id.textFourLesson)
        val countFirstLesson: TextView = view.findViewById(R.id.countFirstLesson)
        val countSecondLesson: TextView = view.findViewById(R.id.countSecondLesson)
        val countThreeLesson: TextView = view.findViewById(R.id.countThreeLesson)
        val countFourLesson: TextView = view.findViewById(R.id.countFourLesson)

    }

}