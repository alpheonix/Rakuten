package com.example.rakutentest.Model

data class SearchProduct (
    val id: Long,
    val newBestPrice:Float,
    val usedBestPrice:Float,
    val headline: String,
    val reviewsAverageNote: Float,
    val nbReviews: Float,
    val imagesUrls: List<String>
)