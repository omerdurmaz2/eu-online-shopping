package com.euonlineshopping.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutHomeProductBinding

class HomeProductsAdapter :
    ListAdapter<HomeProductUiModel, RecyclerView.ViewHolder>(HomeAdapterItemDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeProductViewHolder(LayoutHomeProductBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position)
        (holder as? HomeProductViewHolder)?.bind(product)
    }
}

private class HomeAdapterItemDiffCallBack : DiffUtil.ItemCallback<HomeProductUiModel>() {

    override fun areItemsTheSame(
        oldItem: HomeProductUiModel,
        newItem: HomeProductUiModel
    ): Boolean = oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: HomeProductUiModel,
        newItem: HomeProductUiModel
    ): Boolean = oldItem == newItem
}