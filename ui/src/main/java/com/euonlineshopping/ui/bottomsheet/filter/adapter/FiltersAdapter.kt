package com.euonlineshopping.ui.bottomsheet.filter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.euonlineshopping.domain.model.FilterItemUiModel
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutFilterItemBinding
import com.euonlineshopping.ui.databinding.LayoutHomeProductBinding
import com.euonlineshopping.ui.home.adapter.HomeProductViewHolder
import com.euonlineshopping.ui.home.adapter.ProductsCallback

class FiltersAdapter(private val filtersCallBack: FiltersCallBack) :
    ListAdapter<FilterItemUiModel, RecyclerView.ViewHolder>(HomeAdapterItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilterItemViewHolder(
            binding = LayoutFilterItemBinding.inflate(LayoutInflater.from(parent.context)),
            filtersCallBack = filtersCallBack
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position)
        (holder as? FilterItemViewHolder)?.bind(product)
    }
}

internal class HomeAdapterItemDiffCallBack : DiffUtil.ItemCallback<FilterItemUiModel>() {

    override fun areItemsTheSame(
        oldItem: FilterItemUiModel,
        newItem: FilterItemUiModel
    ): Boolean = oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: FilterItemUiModel,
        newItem: FilterItemUiModel
    ): Boolean = oldItem == newItem
}