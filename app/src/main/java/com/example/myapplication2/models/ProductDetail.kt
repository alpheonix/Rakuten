package com.example.myapplication2.models

data class ProductDetail(
    val productId: Long,
    val salePrice: Double,
    val seller: Seller,
    val quality: String,
    val type: String,
    val sellerComment: String,
    val headline: String,
    val description: String,
    val categories: List<String>,
    val globalRating: Rating,
    val images: List<Images>

)

data class Seller(
    val id: Long,
    val login: String
)
data class Rating(
    val score : Float,
    val nbReviews : Int
)
data class Images(
    val imagesUrls: ImagesUrls,
    val id: Int
)
data class ImagesUrls(
    val entry : List<URL>
)
data class URL(
    val size : String,
    val url : String
)
