package com.example.androidpractice.extension

import java.text.DecimalFormat

fun Long.toMoney() =  DecimalFormat("#,##0").format(this)