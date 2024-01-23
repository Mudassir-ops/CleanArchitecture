package com.example.unsplashapiwithcleanarchitecture.data.wallpaper.remote.api

import com.example.unsplashapiwithcleanarchitecture.data.wallpaper.remote.dto.WallpaperResponse
import retrofit2.Response
import retrofit2.http.*

interface WallpaperApi {

    @GET("photos")
    suspend fun getAllMyProducts(
        @Query("client_id") clientId: String,
        @Query("query") query: String
    ): Response<WallpaperResponse>

}