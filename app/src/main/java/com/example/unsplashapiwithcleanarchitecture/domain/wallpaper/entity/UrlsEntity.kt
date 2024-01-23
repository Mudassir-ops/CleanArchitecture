package com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.entity

data class UrlsEntity(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
    val smallS3: String
)