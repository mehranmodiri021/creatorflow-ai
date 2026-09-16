package com.creatorflow.ai.data.local

import androidx.room.*
import com.creatorflow.ai.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IdeaDao {
    @Query("SELECT * FROM content_ideas ORDER BY createdAt DESC")
    fun getAllIdeas(): Flow<List<ContentIdea>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIdeas(ideas: List<ContentIdea>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIdea(idea: ContentIdea): Long

    @Delete
    suspend fun deleteIdea(idea: ContentIdea)
}

@Dao
interface ScriptDao {
    @Query("SELECT * FROM content_scripts ORDER BY createdAt DESC")
    fun getAllScripts(): Flow<List<ContentScript>>

    @Query("SELECT * FROM content_scripts WHERE id = :id")
    suspend fun getScriptById(id: Long): ContentScript?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScript(script: ContentScript): Long

    @Delete
    suspend fun deleteScript(script: ContentScript)
}

@Dao
interface CaptionDao {
    @Query("SELECT * FROM content_captions ORDER BY createdAt DESC")
    fun getAllCaptions(): Flow<List<ContentCaption>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCaptions(captions: List<ContentCaption>)
}

@Dao
interface ThumbnailDao {
    @Query("SELECT * FROM thumbnail_concepts ORDER BY createdAt DESC")
    fun getAllThumbnails(): Flow<List<ThumbnailConcept>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThumbnails(concepts: List<ThumbnailConcept>)
}

@Dao
interface CalendarDao {
    @Query("SELECT * FROM calendar_posts ORDER BY scheduledDate ASC")
    fun getAllPosts(): Flow<List<CalendarPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: CalendarPost): Long

    @Update
    suspend fun updatePost(post: CalendarPost)

    @Delete
    suspend fun deletePost(post: CalendarPost)
}

@Dao
interface AnalyticsDao {
    @Query("SELECT * FROM analytics_records ORDER BY recordedDate DESC")
    fun getAllRecords(): Flow<List<AnalyticsRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: AnalyticsRecord): Long
}

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<ChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessage): Long

    @Query("DELETE FROM chat_messages")
    suspend fun clearHistory()
}
