package com.ttf.pixabayviewer.data.models

import android.os.Parcelable
import android.util.Size
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageHit(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("pageURL")
    val pageUrl: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("tags")
    val tags: String? = null,

    @field:SerializedName("previewURL")
    val previewUrl: String? = null,

    @field:SerializedName("previewWidth")
    val previewWidth: Int? = null,

    @field:SerializedName("previewHeight")
    val previewHeight: Int? = null,

    @field:SerializedName("largeImageURL")
    val largeImageUrl: String? = null,

    @field:SerializedName("imageWidth")
    val imageWidth: Int? = null,

    @field:SerializedName("imageHeight")
    val imageHeight: Int? = null,

    @field:SerializedName("imageSize")
    val imageSize: Long? = null,

    @field:SerializedName("views")
    val views: Int? = null,

    @field:SerializedName("downloads")
    val downloads: Int? = null,

    @field:SerializedName("likes")
    val likes: Int? = null,

    @field:SerializedName("comments")
    val comments: Int? = null,

    @field:SerializedName("user_id")
    val user_id: Int? = null,

    @field:SerializedName("user")
    val user: String? = null,

    @field:SerializedName("userImageURL")
    val userImageURL: String? = null,
) : Parcelable {

    val previewSize: Size
        get() = Size(previewWidth ?: 0, previewHeight ?: 0)

    val largeImageSize: Size
        get() = Size(imageWidth ?: 0, imageHeight ?: 0)

}