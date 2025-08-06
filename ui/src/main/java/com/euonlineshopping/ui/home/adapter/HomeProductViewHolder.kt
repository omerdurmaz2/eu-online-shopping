package com.euonlineshopping.ui.home.adapter

import android.graphics.Paint
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
        Glide.with(itemView.context).load(product.thumbnail).into(binding.ivProductImage)
        binding.tvProductTitle.text = product.title
        binding.tvProductDescription.text = product.description
        binding.tvProductDiscountedPrice.text =
            calculateDiscountedPrice(product.price, product.discountPercentage).toString()
        binding.tvProductOriginalPrice.text = product.price.toString()
        binding.tvProductOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG


        binding.root.setOnClickListener {
            productsCallback.navigateToDetail(product)
        }
    }
}

private fun calculateDiscountedPrice(originalPrice: Double, discountPercentage: Double): Double {
    val discountAmount = originalPrice * discountPercentage / 100.0
    val discountedPrice = originalPrice - discountAmount
    return String.format("%.2f", discountedPrice).toDouble()
}