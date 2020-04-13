package com.roman.batsu.ui.model

import com.google.gson.annotations.SerializedName

class News(title: String?, description: String?) : Rings(title, description) {
    @SerializedName("time")
    val time: String? = null

    @SerializedName("image_url")
    val image_url: String? = null

    @SerializedName("web_link")
    val web_link: String? = null

    @SerializedName("hex")
    val hex = 0

}