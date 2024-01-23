package com.example.unsplashapiwithcleanarchitecture.data.wallpaper.remote.dto

import com.google.gson.annotations.SerializedName

data class ResultsResponse(
    @SerializedName("urls") val urls: UrlsResponse,
)