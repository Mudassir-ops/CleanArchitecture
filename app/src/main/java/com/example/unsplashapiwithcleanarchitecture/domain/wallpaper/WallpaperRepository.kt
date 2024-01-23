package com.example.unsplashapiwithcleanarchitecture.domain.wallpaper

import com.example.unsplashapiwithcleanarchitecture.data.common.utils.WrappedResponse
import com.example.unsplashapiwithcleanarchitecture.data.wallpaper.remote.dto.WallpaperResponse
import com.example.unsplashapiwithcleanarchitecture.domain.common.base.BaseResult
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.entity.WallpaperEntity
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {
    suspend fun allWallpapers(query: String): Flow<BaseResult<WallpaperEntity, WrappedResponse<WallpaperResponse>>>
}