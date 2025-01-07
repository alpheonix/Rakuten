package com.example.rakutentest.Model

data class Search(
    val totalResultProductsCount:Int = 0,
    val resultProductsCount: Int = 0,
    val pageNumber:Int = 0,
    val title: String = "title",
    val products: List<SearchProduct> = emptyList()
)
