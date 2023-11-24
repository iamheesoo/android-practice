package com.example.androidpractice.txss.transaction

import androidx.annotation.DrawableRes

sealed class ViewItem(
    val type: Int
) {
    object Filter: ViewItem(ViewType.FILTER.ordinal)

    data class MoneyData(
        @DrawableRes val image: Int,
        val title: String,
        val money: Long,
        val restMoney: Long,
        val time: String,
        val memo: String? = null,
        val isDeposit: Boolean // 입금인지 출금인지
    ): ViewItem(ViewType.MONEY_DATA.ordinal)
}

enum class ViewType {
    FILTER,
    MONEY_DATA
}