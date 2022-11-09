package com.ttf.pixabayviewer.data

import com.ttf.pixabayviewer.data.api.IResult
import com.ttf.pixabayviewer.data.models.SearchImagesResponse
import com.ttf.pixabayviewer.data.models.SearchImagesSendData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface PixabayRepository {

    suspend fun search(searchImagesSendData: SearchImagesSendData): Flow<IResult<SearchImagesResponse>>

}

class PixabayRepositoryImpl @Inject constructor(
    private val pixabayDataSource: PixabayDataSource,
    private val imageChaDataSource: ImagesCacheDataSource,
    private val networkInfo: NetworkInfo,
) : PixabayRepository {

    override suspend fun search(searchImagesSendData: SearchImagesSendData): Flow<IResult<SearchImagesResponse>> {
        return flow {
            emit(IResult.loading())

            val isOnline = networkInfo.isInternetAvailable()

            if (isOnline) {
                val result = pixabayDataSource.search(searchImagesSendData)
                result.data?.let {
                    imageChaDataSource.cache(searchImagesSendData, it)
                }
                emit(result)
            } else {
                val cachedResponse = imageChaDataSource.getCacheResponse(searchImagesSendData)
                emit(IResult.success(cachedResponse))
            }

            emit(IResult.finish())
        }.flowOn(Dispatchers.IO)
    }

}