package com.creatorflow.ai.data.local

import androidx.room.TypeConverter
import com.creatorflow.ai.data.model.PostStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value ?: emptyList<String>())
    }

    @TypeConverter
    fun toStringList(value: String?): List<String> {
        if (value.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromPostStatus(status: PostStatus): String {
        return status.name
    }

    @TypeConverter
    fun toPostStatus(value: String): PostStatus {
        return try {
            PostStatus.valueOf(value)
        } catch (e: Exception) {
            PostStatus.PLANNED
        }
    }
}
