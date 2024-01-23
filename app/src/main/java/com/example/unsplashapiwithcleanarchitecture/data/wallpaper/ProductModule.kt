package com.example.unsplashapiwithcleanarchitecture.data.wallpaper

import com.example.unsplashapiwithcleanarchitecture.data.common.module.NetworkModule
import com.example.unsplashapiwithcleanarchitecture.data.wallpaper.remote.api.WallpaperApi
import com.example.unsplashapiwithcleanarchitecture.data.wallpaper.repository.WallpaperRepositoryImpl
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.WallpaperRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ProductModule {
    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): WallpaperApi {
        return retrofit.create(WallpaperApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRepository(wallpaperApi: WallpaperApi): WallpaperRepository {
        return WallpaperRepositoryImpl(wallpaperApi)
    }
}