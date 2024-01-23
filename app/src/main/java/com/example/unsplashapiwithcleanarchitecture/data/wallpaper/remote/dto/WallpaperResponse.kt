package com.example.unsplashapiwithcleanarchitecture.data.wallpaper.remote.dto

import com.google.gson.annotations.SerializedName

data class WallpaperResponse(
    @SerializedName("results") val results: List<ResultsResponse>
)