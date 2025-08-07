package com.euonlineshopping.ui.home.adapter

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.euonlineshopping.domain.model.home.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutHomeProductBinding
import com.euonlineshopping.ui.util.calculateDiscountedPrice

class HomeProductViewHolder(
    private val binding: LayoutHomeProductBinding,
    private val productsCallback: ProductsCallback
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: HomeProductUiModel) {
        Glide.with(itemView.context).load(product.thumbnail).into(binding.ivProductImage)
        binding.tvProductTitle.text = product.title
        binding.tvProductDescription.text = product.description
        binding.tvProductDiscountedPrice.text =
            product.price.calculateDiscountedPrice(product.discountPercentage).toString()
        binding.tvProductOriginalPrice.text = product.price.toString()
        binding.tvProductOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG


        binding.root.setOnClickListener {
            productsCallback.navigateToDetail(product)
        }
    }
}

