package com.euonlineshopping.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutHomeProductBinding

class HomeProductViewHolder(
    private val binding: LayoutHomeProductBinding,
    private val productsCallback: ProductsCallback
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: HomeProductUiModel) {
        Glide.with(itemView.context).load(product.thumbnail).into(binding.ivThumbnail)
        binding.tvProductName.text = product.title
        binding.tvProductPrice.text = product.price.toString()

        binding.root.setOnClickListener {
            productsCallback.navigateToDetail(product)
        }
    }
}