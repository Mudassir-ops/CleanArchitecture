package com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.usecase

import com.example.unsplashapiwithcleanarchitecture.data.common.utils.WrappedResponse
import com.example.unsplashapiwithcleanarchitecture.data.wallpaper.remote.dto.WallpaperResponse
import com.example.unsplashapiwithcleanarchitecture.domain.common.base.BaseResult
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.WallpaperRepository
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.entity.WallpaperEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMyWallpaperUseCase @Inject constructor(private val wallpaperRepository: WallpaperRepository) {

    suspend fun invokeAllWallpaperUseCase(query: String): Flow<BaseResult<WallpaperEntity, WrappedResponse<WallpaperResponse>>> {
        return wallpaperRepository.allWallpapers(query)
    }

}