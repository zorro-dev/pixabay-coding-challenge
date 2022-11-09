package com.ttf.pixabayviewer.data.models

import com.google.gson.annotations.SerializedName

class SearchImagesResponse(
    @SerializedName("total")
    val total: Int = -1,
    @SerializedName("totalHits")
    val totalHits: Int = -1,
    @SerializedName("hits")
    val hits: List<ImageHit>,
)