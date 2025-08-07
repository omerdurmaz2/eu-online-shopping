package com.euonlineshopping.ui.bottomsheet.sort.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.euonlineshopping.domain.model.sort.SortOptionUiModel
import com.euonlineshopping.ui.databinding.LayoutFilterItemBinding

class SortItemViewHolder(
    private val binding: LayoutFilterItemBinding,
    private val filtersCallBack: SortOptionsCallBack
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(option: SortOptionUiModel) {
        binding.tvFilterItem.text = itemView.context.getString(option.titleRes)
        if (option.isSelected) binding.ivSelectedCheck.visibility = View.VISIBLE

        binding.root.setOnClickListener {
            filtersCallBack.onSelect(option)
        }
    }
}