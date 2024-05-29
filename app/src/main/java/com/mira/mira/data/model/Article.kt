package com.mira.mira.data.model

data class ApiResponse(
    val success: Boolean,
    val message: String?,
    val data: Data
)

data class Data(
    val link: String,
    val image: String,
    val description: String,
    val title: String,
    val posts: List<Article>
)

data class Article(
    val link: String,
    val title: String,
    val pubDate: String,
    val description: String,
    val thumbnail: String
)
