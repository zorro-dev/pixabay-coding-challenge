package com.ttf.pixabayviewer.data

import com.ttf.pixabayviewer.data.api.IResult
import com.ttf.pixabayviewer.data.api.PixabayApi
import com.ttf.pixabayviewer.data.base.BaseDataSource
import com.ttf.pixabayviewer.data.models.SearchImagesResponse
import com.ttf.pixabayviewer.data.models.SearchImagesSendData
import javax.inject.Inject

interface PixabayDataSource {
    suspend fun search(searchImagesSendData: SearchImagesSendData): IResult<SearchImagesResponse>
}

class PixabayDataSourceImpl @Inject constructor(private val pixabayApi: PixabayApi) :
    BaseDataSource(), PixabayDataSource {

    override suspend fun search(searchImagesSendData: SearchImagesSendData): IResult<SearchImagesResponse> {
        val searchQuery = searchImagesSendData.searchQuery
        val page = searchImagesSendData.page
        val perPage = searchImagesSendData.perPage

        return getResponse(request = { pixabayApi.search(searchQuery, page, perPage) },
            defaultErrorMessage = "Error signUp")
    }

}