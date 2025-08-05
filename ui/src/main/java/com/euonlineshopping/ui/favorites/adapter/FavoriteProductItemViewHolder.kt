package com.euonlineshopping.ui.favorites.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutFavoritesProductBinding
import com.euonlineshopping.ui.home.adapter.ProductsCallback

class FavoriteProductItemViewHolder(
    private val binding: LayoutFavoritesProductBinding,
    private val productsCallback: ProductsCallback
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: HomeProductUiModel) {
        Glide.with(itemView.context).load(product.thumbnail).into(binding.ivThumbnail)
        binding.tvProductName.text = product.title

        binding.root.setOnClickListener {
            productsCallback.navigateToDetail(product)
        }

        binding.ivFavorite.setOnClickListener {
            productsCallback.toggleFavorite(product)
        }

        binding.ivAddToCart.setOnClickListener {
            productsCallback.addToCart(product)
        }
    }
}