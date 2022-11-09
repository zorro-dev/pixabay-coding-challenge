package com.ttf.pixabayviewer.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import com.ttf.pixabayviewer.BuildConfig
import com.ttf.pixabayviewer.data.NetworkInfo
import com.ttf.pixabayviewer.data.api.PixabayApi
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): PixabayApi {
        return retrofit.create(PixabayApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return builder
            .baseUrl(BuildConfig.PIXABAY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(converterFactory: Converter.Factory): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
    }

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(logging)
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideNetworkInfo(@ApplicationContext applicationContext: Context): NetworkInfo {
        return NetworkInfo(applicationContext)
    }
}
