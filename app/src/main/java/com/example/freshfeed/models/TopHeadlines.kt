package com.example.freshfeed.models

data class TopHeadlines(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)