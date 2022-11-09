package com.ttf.pixabayviewer.data.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class SearchImagesSendData(
    @SerializedName("q")
    val searchQuery: String,
    @SerializedName("p")
    val page: Int = 1,
    @SerializedName("pp")
    val perPage: Int = 50,
) {

    fun toJsonString(): String {
        return Gson().toJson(this)
    }

}