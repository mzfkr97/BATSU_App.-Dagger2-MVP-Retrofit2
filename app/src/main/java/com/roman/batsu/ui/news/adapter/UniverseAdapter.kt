package com.roman.batsu.ui.news.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roman.batsu.R
import com.roman.batsu.ui.model.KurjerInfo
import com.roman.batsu.ui.model.News
import com.roman.batsu.ui.news.adapter.UniverseAdapter.ViewHolder
import com.roman.batsu.utils.ToolbarDataSetter
import com.squareup.picasso.Picasso
import java.util.*

class UniverseAdapter(
        var universeNewsList: List<KurjerInfo>
) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
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


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val img = universeNewsList[position].image
        setImageFromPicasso(holder, img)
        holder.title.setTextColor(Color.YELLOW)
        holder.title.text = universeNewsList[position].title
        holder.description.text = universeNewsList[position].description
        val author = universeNewsList[position].author
        val data = ToolbarDataSetter().dateFormatter(universeNewsList[position].data)
        holder.data.text = """
            $author
            $data
            """.trimIndent()
        holder.webLink.text = universeNewsList[position].web_link
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

    override fun getItemCount(): Int = universeNewsList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        private val textShare : TextView = itemView.findViewById(R.id.textShare)
        val description: TextView = itemView.findViewById(R.id.description)
        val webLink: TextView = itemView.findViewById(R.id.web_link)
        val data: TextView = itemView.findViewById(R.id.time)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            textShare.visibility = GONE

        }
    }



}