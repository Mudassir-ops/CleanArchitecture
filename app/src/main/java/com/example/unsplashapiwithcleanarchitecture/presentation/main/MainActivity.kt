package com.example.unsplashapiwithcleanarchitecture.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.unsplashapiwithcleanarchitecture.databinding.ActivityMainBinding
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.entity.WallpaperEntity
import com.example.unsplashapiwithcleanarchitecture.presentation.common.extension.gone
import com.example.unsplashapiwithcleanarchitecture.presentation.common.extension.showToast
import com.example.unsplashapiwithcleanarchitecture.presentation.common.extension.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: HomeMainViewModel by viewModels()
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupRecyclerView()
        observe()
    }

    private fun observe() {
        observeState()
        observeWallpapers()
    }

    private fun setupRecyclerView() {
        val mAdapter = MainWallpapersAdapter(mutableListOf(), context = this@MainActivity)
        binding?.productsRecyclerView?.apply {
            adapter = mAdapter
            hasFixedSize()
        }
    }

    private fun observeState() {
        viewModel.mState.flowWithLifecycle(this.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }.launchIn(this.lifecycleScope)
    }

    private fun observeWallpapers() {
        viewModel.mProducts.flowWithLifecycle(this.lifecycle, Lifecycle.State.STARTED)
            .onEach { wallpaperEntity ->
                if (wallpaperEntity != null) {
                    handleWallpapers(wallpaperEntity)
                }
            }.launchIn(this.lifecycleScope)
    }

    private fun handleWallpapers(products: WallpaperEntity) {
        binding?.productsRecyclerView?.adapter?.let {
            if (it is MainWallpapersAdapter) {
                products.results?.let { it1 -> it.updateList(it1) }
            }
        }
    }

    private fun handleState(state: MainActivityState) {
        when (state) {
            is MainActivityState.IsLoading -> handleLoading(state.isLoading)
            is MainActivityState.ShowToast -> this@MainActivity.showToast(state.message)
            is MainActivityState.Init -> Unit
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.loadingProgressBar?.visible()
        } else {
            binding?.loadingProgressBar?.gone()
        }
    }


}