package com.example.unsplashapiwithcleanarchitecture.data.common.module

import com.example.unsplashapiwithcleanarchitecture.data.common.utils.RequestInterceptor
import com.ydhnwb.cleanarchitectureexercise.infra.utils.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl("https://api.unsplash.com/search/")
        }.build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(requestInterceptor: RequestInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor)
        }.build()
    }

    @Provides
    fun provideRequestInterceptor(prefs: SharedPrefs): RequestInterceptor {
        return RequestInterceptor(prefs)
    }

}