package com.example.androidpractice.txss.transaction

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpractice.databinding.ItemTxssBinding
import com.example.androidpractice.extension.toMoney


class ItemViewHolder(private val binding: ItemTxssBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: ViewItem.MoneyData) {
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