package com.example.freshfeed.db

import androidx.room.TypeConverter
import com.example.freshfeed.models.Source
import com.google.gson.Gson

class SourceTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromSource(source: Source): String {
        return gson.toJson(source)
    }

    @TypeConverter
    fun toSource(sourceString: String): Source {
        return gson.fromJson(sourceString, Source::class.java)
    }
}