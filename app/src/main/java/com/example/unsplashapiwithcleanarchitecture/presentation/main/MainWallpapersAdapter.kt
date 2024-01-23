package com.example.unsplashapiwithcleanarchitecture.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplashapiwithcleanarchitecture.R
import com.example.unsplashapiwithcleanarchitecture.databinding.ItemHomeLayoutBinding
import com.example.unsplashapiwithcleanarchitecture.domain.wallpaper.entity.ResultsEntity

class MainWallpapersAdapter(
    private val products: MutableList<ResultsEntity>, private val context: Context
) : RecyclerView.Adapter<MainWallpapersAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(mProducts: List<ResultsEntity>) {
        products.clear()
        products.addAll(mProducts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemHomeLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(product: ResultsEntity) {

            Glide.with(context).load(product.urls.regular)
                .placeholder(R.drawable.ic_launcher_background).timeout(10000)
                .error(R.drawable.ic_launcher_foreground).into(itemBinding.ivUnSplash)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHomeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(products[position])

    override fun getItemCount() = products.size
}