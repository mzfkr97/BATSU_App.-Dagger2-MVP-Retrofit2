package com.roman.batsu.ui.rings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roman.batsu.R
import com.roman.batsu.ui.model.Rings
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

    interface RingsItemClick {
        fun onClick(item: Rings?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rings, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notificationList[position])
    }

    override fun getItemCount(): Int = notificationList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val cardView: CardView = itemView.findViewById(R.id.cardNotification)
        val title: TextView = itemView.findViewById(R.id.title)
        private val firstLesson: TextView = itemView.findViewById(R.id.first_lesson)
        private val textDrop: View = itemView.findViewById(R.id.textDrop)

        fun bind(rings: Rings){
            title.text = rings.title
            firstLesson.text = rings.description
            val color: Int = ColorMaker(mContext).getRandomMaterialColor("400")
            textDrop.setBackgroundColor(color)
        }

        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onClickPopup?.onClick(notificationList[adapterPosition])
        }
    }
}