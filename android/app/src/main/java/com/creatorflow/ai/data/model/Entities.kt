package com.creatorflow.ai.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_ideas")
data class ContentIdea(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val angle: String,
    val hookSnippet: String,
    val whyViral: String,
    val estimatedRetention: String,
    val topic: String,
    val niche: String,
    val platform: String,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "content_scripts")
data class ContentScript(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val platform: String,
    val targetLength: String,
    val hook: String,
    val intro: String,
    val mainContent: List<String>,
    val emotionalTrigger: String,
    val callToAction: String,
    val visualDirectives: List<String>,
    val wordCount: Int,
    val isSaved: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "content_captions")
data class ContentCaption(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val topic: String,
    val platform: String,
    val style: String,
    val headline: String,
    val captionBody: String,
    val callToAction: String,
    val hashtags: List<String>,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "thumbnail_concepts")
data class ThumbnailConcept(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val conceptName: String,
    val visualDescription: String,
    val textSuggestions: List<String>,
    val colorPalette: List<String>,
    val layoutGuidance: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "calendar_posts")
data class CalendarPost(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val platform: String, // YouTube, Reels, TikTok, Shorts
    val scheduledDate: Long, // timestamp
    val status: PostStatus = PostStatus.PLANNED,
    val hookSnippet: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class PostStatus {
    PLANNED,
    RECORDED,
    EDITED,
    PUBLISHED
}

@Entity(tableName = "analytics_records")
data class AnalyticsRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val platform: String,
    val views: Long,
    val likes: Long,
    val followers: Long,
    val comments: Long,
    val saves: Long,
    val engagementRate: Double,
    val aiVerdict: String,
    val recordedDate: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sender: String, // "user" or "assistant"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MonetizationTier {
    FREE,
    VIP
}
