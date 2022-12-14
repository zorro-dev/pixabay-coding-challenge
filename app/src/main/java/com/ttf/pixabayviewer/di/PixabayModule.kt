package com.ttf.pixabayviewer.di

import android.content.Context
import com.ttf.pixabayviewer.data.*
import com.ttf.pixabayviewer.data.api.PixabayApi
import com.ttf.pixabayviewer.data.datasource.ImagesCacheDataSource
import com.ttf.pixabayviewer.data.datasource.ImagesCacheDataSourceImpl
import com.ttf.pixabayviewer.data.datasource.PixabayDataSource
import com.ttf.pixabayviewer.data.datasource.PixabayRemoteDataSource
import com.ttf.pixabayviewer.data.network.NetworkCheck
import com.ttf.pixabayviewer.data.repository.PixabayRepository
import com.ttf.pixabayviewer.data.repository.PixabayRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PixabayModule {

    @Provides
    @Singleton
    fun providePixabayDataSource(pixabayApi: PixabayApi): PixabayDataSource {
        return PixabayRemoteDataSource(pixabayApi)
    }

    @Provides
    @Singleton
    fun provideImagesCacheDataSource(@ApplicationContext context: Context): ImagesCacheDataSource {
        return ImagesCacheDataSourceImpl(context)
    }

    @Provides
    @Singleton
    fun providePixabayRepository(
        pixabayDataSource: PixabayDataSource,
        cacheDataSource: ImagesCacheDataSource,
        networkCheck: NetworkCheck,
    ): PixabayRepository {
        return PixabayRepositoryImpl(pixabayDataSource, cacheDataSource, networkCheck)
    }

}
