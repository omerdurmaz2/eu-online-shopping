package com.euonlineshopping.ui.bottomsheet.sort.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.euonlineshopping.domain.model.SortOptionUiModel
import com.euonlineshopping.ui.databinding.LayoutFilterItemBinding

class SortOptionsAdapter(private val filtersCallBack: SortOptionsCallBack) :
    ListAdapter<SortOptionUiModel, RecyclerView.ViewHolder>(SortOptionAdapterItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SortItemViewHolder(
            binding = LayoutFilterItemBinding.inflate(LayoutInflater.from(parent.context)),
            filtersCallBack = filtersCallBack
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position)
        (holder as? SortItemViewHolder)?.bind(product)
    }
}

internal class SortOptionAdapterItemDiffCallBack : DiffUtil.ItemCallback<SortOptionUiModel>() {

    override fun areItemsTheSame(
        oldItem: SortOptionUiModel,
        newItem: SortOptionUiModel
    ): Boolean = oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: SortOptionUiModel,
        newItem: SortOptionUiModel
    ): Boolean = oldItem == newItem
}