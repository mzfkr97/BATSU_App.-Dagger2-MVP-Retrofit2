package com.roman.batsu.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roman.batsu.databinding.ItemHomeBinding
import com.roman.batsu.ui.main.adapter.HomeAdapter.MyViewHolder
import com.roman.batsu.ui.model.Home

class HomeAdapter : ListAdapter<Home, MyViewHolder>(HomeDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class MyViewHolder private constructor(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemHome: Home) {
            binding.itemHome = itemHome
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeBinding.inflate(inflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    class HomeDiffUtilCallBack: DiffUtil.ItemCallback<Home>(){
        override fun areItemsTheSame(oldItem: Home, newItem: Home): Boolean {
            return oldItem.day == newItem.day
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Home, newItem: Home): Boolean {
            return oldItem == newItem
        }

    }



}