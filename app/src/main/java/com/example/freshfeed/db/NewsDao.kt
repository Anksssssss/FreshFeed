package com.example.freshfeed.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.freshfeed.models.NewsArticles

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article:NewsArticles)


    @Query("DELETE FROM NewsArticle WHERE category = :category")
    fun deleteAll(category: String)

    @Query("SELECT * FROM NewsArticle")
    fun getNewsArticles(): List<NewsArticles>

}