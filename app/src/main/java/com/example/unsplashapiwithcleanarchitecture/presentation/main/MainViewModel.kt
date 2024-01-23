package com.example.unsplashapiwithcleanarchitecture.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplashapiwithcleanarchitecture.domain.common.base.BaseResult
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.entity.WallpaperEntity
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.usecase.GetAllMyWallpaperUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(private val getAllMyWallpaperUseCase: GetAllMyWallpaperUseCase) :
    ViewModel() {
    private val state = MutableStateFlow<MainActivityState>(MainActivityState.Init)
    val mState: StateFlow<MainActivityState> get() = state
    private val products = MutableStateFlow<WallpaperEntity?>(null)
    val mProducts: StateFlow<WallpaperEntity?> get() = products
    init {
        allMyWallpapers("Cars")
    }
    private fun setLoading() {
        state.value = MainActivityState.IsLoading(true)
    }
    private fun hideLoading() {
        state.value = MainActivityState.IsLoading(false)
    }
    private fun showToast(message: String) {
        state.value = MainActivityState.ShowToast(message)
    }
    private fun allMyWallpapers(query: String) {
        viewModelScope.launch {
            getAllMyWallpaperUseCase.invokeAllWallpaperUseCase(query)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> {
                            products.value = result.data
                        }

                        is BaseResult.Error -> {
                            showToast("result.rawResponse.message")
                        }
                    }

                }
        }
    }
}
sealed class MainActivityState {
    object Init : MainActivityState()
    data class IsLoading(val isLoading: Boolean) : MainActivityState()
    data class ShowToast(val message: String) : MainActivityState()
}