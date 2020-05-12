package com.roman.batsu.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roman.batsu.R
import com.roman.batsu.ui.main.adapter.HomeAdapter.MyViewHolder
import com.roman.batsu.ui.model.Home
import com.roman.batsu.utils.ColorMaker

class HomeAdapter : ListAdapter<Home, MyViewHolder>(HomeDiffUtillCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class MyViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {
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


        fun bind(itemHome: Home) {
            title.text = itemHome.day
            dateText.text = itemHome.date
            textDrop.setTextColor(ColorMaker(textDrop.context).getRandomMaterialColor("400"))
            val empty = "size =   " + itemHome.lessons?.size
            try {
                countFirstLesson.text = itemHome.lessons?.get(0)?.count
                textFirstLess.text = itemHome.lessons?.get(0)?.lessonName
                countSecondLesson.text = itemHome.lessons?.get(1)?.count
                textSecondLesson.text = itemHome.lessons?.get(1)?.lessonName
                countThreeLesson.text = itemHome.lessons?.get(2)?.count
                textThreeLesson.text = itemHome.lessons?.get(2)?.lessonName
                countFourLesson.text = itemHome.lessons?.get(3)?.count
                textFourLesson.text = itemHome.lessons?.get(3)?.lessonName
            } catch (e: Exception) {
                Log.d("TAG", "onBindViewHolder: $e$empty")
            }
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
                return MyViewHolder(itemView)
            }
        }
    }

    class HomeDiffUtillCallBack: DiffUtil.ItemCallback<Home>(){
        override fun areItemsTheSame(oldItem: Home, newItem: Home): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: Home, newItem: Home): Boolean {
            return oldItem == newItem
        }

    }



}