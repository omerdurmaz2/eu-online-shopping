package com.euonlineshopping.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutHomeProductBinding

class HomeProductViewHolder(private val binding: LayoutHomeProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: HomeProductUiModel) {
        binding.tvProductName.text = product.name
    }

}