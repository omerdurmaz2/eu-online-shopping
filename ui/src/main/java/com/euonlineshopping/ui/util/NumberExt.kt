package com.euonlineshopping.ui.util

fun Double.calculateDiscountedPrice(discountPercentage: Double): Double {
    val discountAmount = this * discountPercentage / 100.0
    val discountedPrice = this - discountAmount
    return String.format("%.2f", discountedPrice).toDouble()
}