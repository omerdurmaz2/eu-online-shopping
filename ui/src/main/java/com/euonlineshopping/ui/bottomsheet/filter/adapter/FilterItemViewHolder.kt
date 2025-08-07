package com.euonlineshopping.ui.bottomsheet.filter.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.euonlineshopping.domain.model.filter.FilterItemUiModel
import com.euonlineshopping.ui.databinding.LayoutFilterItemBinding

class FilterItemViewHolder(
    private val binding: LayoutFilterItemBinding,
    private val filtersCallBack: FiltersCallBack
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(filter: FilterItemUiModel) {
        binding.tvFilterItem.text = filter.name
        if (filter.isSelected) binding.ivSelectedCheck.visibility = View.VISIBLE

        binding.root.setOnClickListener {
            filtersCallBack.onSelect(filter)
        }
    }
}