package com.example.unsplashapiwithcleanarchitecture.data.wallpaper.repository

import com.example.unsplashapiwithcleanarchitecture.data.common.utils.AppConstants
import com.example.unsplashapiwithcleanarchitecture.data.common.utils.WrappedResponse
import com.example.unsplashapiwithcleanarchitecture.data.wallpaper.remote.api.WallpaperApi
import com.example.unsplashapiwithcleanarchitecture.data.wallpaper.remote.dto.ResultsResponse
import com.example.unsplashapiwithcleanarchitecture.data.wallpaper.remote.dto.WallpaperResponse
import com.example.unsplashapiwithcleanarchitecture.domain.common.base.BaseResult
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.WallpaperRepository
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.entity.ResultsEntity
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.entity.UrlsEntity
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.entity.WallpaperEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WallpaperRepositoryImpl @Inject constructor(private val wallpaperApi: WallpaperApi) :
    WallpaperRepository {

    override suspend fun allWallpapers(query: String): Flow<BaseResult<WallpaperEntity, WrappedResponse<WallpaperResponse>>> {
        return flow {
            val response1 = wallpaperApi.getAllMyProducts(AppConstants.API_ACCESS_KEY, query)
            if (response1.isSuccessful) {
                val wallpaperResponse = response1.body()
                val wallpaperEntity = mapToWallpaperEntity(wallpaperResponse)
                emit(BaseResult.Success(wallpaperEntity))
            } else {
                emit(
                    BaseResult.Error(
                        WrappedResponse(
                            code = response1.code(),
                            message = response1.message(),
                            status = true,
                            errors = listOf(),
                            data = listOf()
                        )
                    )
                )
            }
        }
    }

    private fun mapToWallpaperEntity(response: WallpaperResponse?): WallpaperEntity {
        val resultsEntities = response?.results?.map { mapToResultsEntity(it) }
        return WallpaperEntity(resultsEntities)
    }

    private fun mapToResultsEntity(response: ResultsResponse): ResultsEntity {
        return ResultsEntity(
            urls = UrlsEntity(
                raw = response.urls.raw,
                full = response.urls.full,
                regular = response.urls.regular,
                small = response.urls.small,
                thumb = response.urls.thumb,
                smallS3 = response.urls.smallS3
            )
        )
    }

}

