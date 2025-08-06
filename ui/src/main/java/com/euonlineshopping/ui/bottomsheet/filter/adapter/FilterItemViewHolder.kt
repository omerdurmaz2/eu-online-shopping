package com.euonlineshopping.ui.bottomsheet.filter.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.euonlineshopping.domain.model.FilterItemUiModel
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.databinding.LayoutFilterItemBinding
import com.euonlineshopping.ui.databinding.LayoutHomeProductBinding
import com.euonlineshopping.ui.home.adapter.ProductsCallback

class FilterItemViewHolder(
    private val binding: LayoutFilterItemBinding,
    private val filtersCallBack: FiltersCallBack
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(filter: FilterItemUiModel) {
        binding.tvFilterItem.text = filter.name

        binding.root.setOnClickListener {
            filtersCallBack.onSelect(filter)
        }
    }
}