package com.euonlineshopping.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutHomeProductBinding
import com.euonlineshopping.ui.home.adapter.HomeAdapterItemDiffCallBack

class CartProductsAdapter(private val productsCallback: CartProductsCallback) :
    ListAdapter<HomeProductUiModel, RecyclerView.ViewHolder>(HomeAdapterItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CartProductViewHolder(
            binding = LayoutHomeProductBinding.inflate(LayoutInflater.from(parent.context)),
            productsCallback = productsCallback
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position)
        (holder as? CartProductViewHolder)?.bind(product)
    }
}

