package com.example.androidpractice.txss

import androidx.annotation.DrawableRes
import com.example.androidpractice.R

val txssDummyList = listOf(
    MoneyData(
        image = R.drawable.ic_money,
        title = "희수",
        money = 6000,
        restMoney = 179805,
        time = "2023-10-02 10:22",
        memo = "계란",
        isDeposit = false
    ),
    MoneyData(
        image = R.drawable.ic_credit_card,
        title = "카드 캐시백",
        money = 100,
        restMoney = 185805,
        time = "2023-10-02 10:04",
        isDeposit = true
    ),
    MoneyData(
        image = R.drawable.ic_emart,
        title = "(주)이마트 천호점",
        money = 3680,
        restMoney = 185705,
        time = "2023-10-02 10:04",
        memo = "대파",
        isDeposit = false
    ),
    MoneyData(
        image = R.drawable.ic_money,
        title = "희수",
        money = 4290,
        restMoney = 189385,
        time = "2023-10-01 10:49",
        memo = "도시가스",
        isDeposit = false
    ),
    MoneyData(
        image = R.drawable.ic_money,
        title = "희수",
        money = 6000,
        restMoney = 179805,
        time = "2023-09-29 10:22",
        memo = "계란",
        isDeposit = false
    ),
    MoneyData(
        image = R.drawable.ic_credit_card,
        title = "카드 캐시백",
        money = 100,
        restMoney = 185805,
        time = "2023-09-29 10:04",
        isDeposit = true
    ),
    MoneyData(
        image = R.drawable.ic_emart,
        title = "(주)이마트 천호점",
        money = 3680,
        restMoney = 185705,
        time = "2023-09-29 10:04",
        memo = "대파",
        isDeposit = false
    ),
    MoneyData(
        image = R.drawable.ic_money,
        title = "희수",
        money = 4290,
        restMoney = 189385,
        time = "2023-09-28 10:49",
        memo = "도시가스",
        isDeposit = false
    ),
)

data class MoneyData(
    @DrawableRes val image: Int,
    val title: String,
    val money: Long,
    val restMoney: Long,
    val time: String,
    val memo: String? = null,
    val isDeposit: Boolean // 입금인지 출금인지
)