package com.euonlineshopping.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutCartProductBinding
import com.euonlineshopping.ui.databinding.LayoutHomeProductBinding
import com.euonlineshopping.ui.util.calculateDiscountedPrice

class CartProductViewHolder(
    private val binding: LayoutCartProductBinding,
    private val productsCallback: CartProductsCallback
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: HomeProductUiModel) {
        Glide.with(itemView.context).load(product.thumbnail).into(binding.ivThumbnail)
        binding.tvProductName.text = product.title
        binding.tvOriginalPrice.text = "${product.price} ₺"
        binding.tvDiscountedPrice.text =
            "${product.price.calculateDiscountedPrice(product.discountPercentage)} ₺"
        binding.tvProductCount.text = product.count.toString()

        binding.btnDescreaseCount.setOnClickListener {
            if (product.count == 1) {
                productsCallback.removeProduct(product)
            } else {
                productsCallback.decreaseCount(product)
            }
        }

        binding.btnIncreaseCount.setOnClickListener {
            productsCallback.increaseCount(product)
        }

        binding.btnRemoveProduct.setOnClickListener {
            productsCallback.removeProduct(product)
        }

        binding.root.setOnClickListener {
            productsCallback.navigateToDetail(product)
        }
    }
}