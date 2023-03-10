package com.subha.optymize.data.model

data class ProductListRes(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)