package com.example.androidpractice.txss

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpractice.databinding.ItemTxssBinding
import com.example.androidpractice.extension.toMoney

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var list: List<MoneyData> = emptyList()
    inner class ViewHolder(private val binding: ItemTxssBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MoneyData) {
            with(binding) {
                Glide.with(root)
                    .load(data.image)
                    .circleCrop()
                    .into(ivLogo)
                tvTitle.text = data.title
                tvTime.text = data.time
                tvMoney.text = "${data.money.toMoney()}원"
                tvTotalMoney.text = "${data.restMoney.toMoney()}원"
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTxssBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}