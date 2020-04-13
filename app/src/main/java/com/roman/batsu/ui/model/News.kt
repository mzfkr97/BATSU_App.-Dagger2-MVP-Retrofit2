package com.roman.batsu.ui.model

import com.google.gson.annotations.SerializedName

class News(title: String?, description: String?) : Rings(title, description) {
    @SerializedName("time")
    val time: String? = null

    @SerializedName("image_url")
    val imageUrl: String? = null

    @SerializedName("web_link")
    val webLink: String? = null

    @SerializedName("hex")
    val hex = 0

}