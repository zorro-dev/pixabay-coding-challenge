package com.ttf.pixabayviewer.data.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.ttf.pixabayviewer.data.repository.PixabayRepository
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class SearchImagesSendData(
    @SerializedName("q")
    val searchQuery: String,
    @SerializedName("p")
    val page: Int = 1,
    @SerializedName("pp")
    val perPage: Int = PixabayRepository.pixabayPageSize,
) {

    val queryUrlEncoded: String =
        URLEncoder.encode(searchQuery, StandardCharsets.UTF_8.toString())

    fun toJsonString(): String {
        return Gson().toJson(this)
    }

}