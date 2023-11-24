package com.example.androidpractice.txss.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.databinding.ItemHistoryFilterBinding
import com.example.androidpractice.databinding.ItemTxssBinding

class HistoryAdapter :
    ListAdapter<ViewItem, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<ViewItem>() {
        override fun areItemsTheSame(oldItem: ViewItem, newItem: ViewItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ViewItem, newItem: ViewItem): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.FILTER.ordinal -> {
                val binding = ItemHistoryFilterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FilterViewHolder(binding)
            }

            else -> {
                val binding =
                    ItemTxssBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (currentList[position].type) {
            ViewType.MONEY_DATA.ordinal -> {
                if (holder is ItemViewHolder) {
                    if (currentList[position] is ViewItem.MoneyData) {
                        holder.bind(currentList[position] as ViewItem.MoneyData)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type
    }
}