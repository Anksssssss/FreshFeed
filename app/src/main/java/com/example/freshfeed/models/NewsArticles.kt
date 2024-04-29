package com.example.freshfeed.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NewsArticle")
data class NewsArticles(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val category: String
)