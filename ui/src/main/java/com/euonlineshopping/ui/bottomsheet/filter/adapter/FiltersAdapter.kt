package com.euonlineshopping.ui.bottomsheet.filter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.euonlineshopping.domain.model.filter.FilterItemUiModel
import com.euonlineshopping.ui.databinding.LayoutFilterItemBinding

class FiltersAdapter(private val filtersCallBack: FiltersCallBack) :
    ListAdapter<FilterItemUiModel, RecyclerView.ViewHolder>(FiltersAdapterItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilterItemViewHolder(
            binding = LayoutFilterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            filtersCallBack = filtersCallBack
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position)
        (holder as? FilterItemViewHolder)?.bind(product)
    }
}

internal class FiltersAdapterItemDiffCallBack : DiffUtil.ItemCallback<FilterItemUiModel>() {

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