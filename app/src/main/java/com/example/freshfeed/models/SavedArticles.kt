package com.example.freshfeed.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedArticles")
data class SavedArticles(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val description: String?,
    val image: String?,
    val source: String?

)