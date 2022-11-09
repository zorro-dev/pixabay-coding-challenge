package com.ttf.pixabayviewer.data.api

import com.ttf.pixabayviewer.BuildConfig
import com.ttf.pixabayviewer.data.models.SearchImagesResponse
import retrofit2.Response
import retrofit2.http.*

interface PixabayApi {

    @GET("/api")
    suspend fun search(
        @Query("q") searchQuery: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 50,
        @Query("key") key: String = BuildConfig.PIXABAY_KEY,
    ): Response<SearchImagesResponse>

}