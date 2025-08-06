package com.euonlineshopping.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutFavoritesProductBinding
import com.euonlineshopping.ui.home.adapter.HomeAdapterItemDiffCallBack
import com.euonlineshopping.ui.home.adapter.ProductsCallback

class FavoriteProductsAdapter(private val productsCallback: ProductsCallback) :
    ListAdapter<HomeProductUiModel, RecyclerView.ViewHolder>(HomeAdapterItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteProductItemViewHolder(
            binding = LayoutFavoritesProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            productsCallback = productsCallback
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position)
        (holder as? FavoriteProductItemViewHolder)?.bind(product)
    }
}

