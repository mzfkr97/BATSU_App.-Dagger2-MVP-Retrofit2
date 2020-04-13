package com.roman.batsu.ui.rings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roman.batsu.R
import com.roman.batsu.ui.model.News
import com.roman.batsu.ui.model.Rings
import com.roman.batsu.ui.news.adapter.NewsAdapter
import com.roman.batsu.utils.ColorMaker

class RingsAdapter internal constructor(
        private val mContext: Context,
        private val notificationList: List<Rings>

) : RecyclerView.Adapter<RingsAdapter.ViewHolder>() {

    companion object {
        private var onClickPopup: RingsItemClick? = null
    }

    fun setAutoOnItemClickListener(popupItemClick: RingsItemClick?) {
        onClickPopup = popupItemClick
    }
    public interface RingsItemClick {
        fun onClick(item: Rings?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = notificationList[position].title
        holder.firstLesson.text = notificationList[position].description
        val color = ColorMaker(mContext).getRandomMaterialColor("400")
        holder.textDrop.setBackgroundColor(color)
    }

    override fun getItemCount(): Int = notificationList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val cardView: CardView = itemView.findViewById(R.id.cardNotification)
        val title: TextView = itemView.findViewById(R.id.title)
        val firstLesson: TextView = itemView.findViewById(R.id.first_lesson)
        val textDrop: View = itemView.findViewById(R.id.textDrop)

        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onClickPopup?.onClick(notificationList[adapterPosition])
        }
    }
}