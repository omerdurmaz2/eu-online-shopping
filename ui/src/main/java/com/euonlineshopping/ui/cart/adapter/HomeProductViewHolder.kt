package com.euonlineshopping.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutHomeProductBinding

class CartProductViewHolder(
    private val binding: LayoutHomeProductBinding,
    private val productsCallback: CartProductsCallback
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: HomeProductUiModel) {
        Glide.with(itemView.context).load(product.thumbnail).into(binding.ivThumbnail)
        binding.tvProductName.text = product.title

        binding.root.setOnClickListener {
            productsCallback.navigateToDetail(product)
        }
    }
}