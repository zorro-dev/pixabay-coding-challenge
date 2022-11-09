package com.ttf.pixabayviewer.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.ttf.pixabayviewer.data.models.SearchImagesResponse
import com.ttf.pixabayviewer.data.models.SearchImagesSendData
import com.ttf.pixabayviewer.utils.readObject
import com.ttf.pixabayviewer.utils.writeObject
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ImagesCacheDataSource {

    fun cache(req: SearchImagesSendData, response: SearchImagesResponse)
    fun getCacheResponse(req: SearchImagesSendData): SearchImagesResponse?

}

class ImagesCacheDataSourceImpl @Inject constructor(@ApplicationContext val context: Context) :
    ImagesCacheDataSource {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("image_cache_shared_prefs", MODE_PRIVATE)
    }

    override fun cache(req: SearchImagesSendData, response: SearchImagesResponse) {
        prefs.writeObject(req.toJsonString(), response)
    }

    override fun getCacheResponse(req: SearchImagesSendData): SearchImagesResponse? {
        return prefs.readObject(req.toJsonString(), SearchImagesResponse::class.java)
    }

}