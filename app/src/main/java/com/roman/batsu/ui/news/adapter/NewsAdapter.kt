package com.roman.batsu.ui.news.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roman.batsu.R
import com.roman.batsu.ui.model.News
import com.squareup.picasso.Picasso
import java.util.*

class NewsAdapter(
        private val dashboardList: List<News>
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    companion object {
        private var onClickPopup: DashboardItemClick? = null

        private fun IntArray.getRandomImageInArray(): Int {
            val rnd = Random().nextInt(size)
            return this[rnd]
        }

        private val myDraw = intArrayOf(
                R.drawable.a_arrow,
                R.drawable.a_work,
                R.drawable.a_boo,
                R.drawable.a_books,
                R.drawable.a_calculator,
                R.drawable.a_hands,
                R.drawable.a_hourglass,
                R.drawable.a_stock
        )
    }

    fun setAutoOnItemClickListener(popupItemClick: DashboardItemClick?) {
        onClickPopup = popupItemClick
    }

    interface DashboardItemClick {
        fun onClickPopup(item: News?)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: News = dashboardList[position]
        val imageUrl = dashboardList[position].imageUrl
        setImageFromPicasso(holder, imageUrl)
        holder.bind(movie)
    }

    private fun setImageFromPicasso(holder: ViewHolder, image_url: String?) {
        if (image_url != null && !image_url.isEmpty()) {
            Picasso.get()
                    .load(image_url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.a_arrow)
                    .into(holder.imageView)
        } else {
            Picasso.get()
                    .load(myDraw.getRandomImageInArray())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageView)
        }
    }

    private fun Int.myColor(): Int {
        return when (this) {
            1 -> Color.WHITE
            2 -> Color.YELLOW
            3 -> Color.RED
            else -> Color.BLACK
        }
    }

    override fun getItemCount(): Int = dashboardList.count()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var title: TextView = itemView.findViewById(R.id.title)
        var textShare: TextView = itemView.findViewById(R.id.textShare)
        var description: TextView = itemView.findViewById(R.id.description)
        var webLink: TextView = itemView.findViewById(R.id.web_link)
        var time: TextView = itemView.findViewById(R.id.time)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            textShare.setOnClickListener(this)
        }

        override fun onClick(view: View) {
                onClickPopup?.onClickPopup(dashboardList[adapterPosition])
        }

        fun bind(taxiItem: News) {

            val web = taxiItem.webLink
            val colorHex = taxiItem.hex
            title.text = taxiItem.title
            title.setTextColor(colorHex.myColor())
            description.text = taxiItem.description
            if (web != null) {
                if (web.isEmpty()) {
                    webLink.visibility = View.GONE
                }
            }
            webLink.text = taxiItem.webLink
            time.text = taxiItem.time
        }

    }



}