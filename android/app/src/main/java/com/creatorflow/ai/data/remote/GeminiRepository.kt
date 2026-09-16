package com.creatorflow.ai.data.remote

import com.creatorflow.ai.data.local.AppDatabase
import com.creatorflow.ai.data.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GeminiRepository(
    private val database: AppDatabase,
    private val apiKeyProvider: () -> String
) {
    private val gson = Gson()
    private val apiService: GeminiApiService

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(GeminiApiService::class.java)
    }

    // Exponential backoff retry execution
    private suspend fun <T> executeWithRetry(
        maxRetries: Int = 3,
        initialDelayMs: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): NetworkResult<T> {
        var currentDelay = initialDelayMs
        repeat(maxRetries - 1) { attempt ->
            try {
                return NetworkResult.Success(block())
            } catch (e: Exception) {
                delay(currentDelay)
                currentDelay = (currentDelay * factor).toLong()
            }
        }
        return try {
            NetworkResult.Success(block())
        } catch (e: Exception) {
            NetworkResult.Error("API call failed after retries: ${e.localizedMessage}", e)
        }
    }

    // 1. Generate Content Ideas & persist in Room
    suspend fun generateIdeas(
        topic: String,
        niche: String,
        platform: String,
        audience: String,
        language: String = "en"
    ): NetworkResult<List<ContentIdea>> {
        return executeWithRetry {
            val apiKey = apiKeyProvider()
            val prompt = """
                You are CreatorFlow AI, an elite viral social media strategist developed by سیدحمیدموسوی زاده.
                Generate 4 viral content ideas for:
                - Topic: $topic
                - Niche: $niche
                - Platform: $platform
                - Target Audience: $audience
                - Language: ${if (language == "fa") "Persian (Farsi)" else "English"}
                
                Respond in valid JSON array format.
            """.trimIndent()

            val request = GeminiGenerateRequest(
                contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))),
                generationConfig = GeminiGenerationConfig(responseMimeType = "application/json")
            )
            val response = apiService.generateContent(apiKey = apiKey, request = request)
            if (!response.isSuccessful) {
                throw RuntimeException("HTTP ${response.code()}: ${response.message()}")
            }
            val text = response.body()?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "[]"
            val type = object : TypeToken<List<Map<String, String>>>() {}.type
            val rawList: List<Map<String, String>> = gson.fromJson(text, type)

            val ideas = rawList.map { item ->
                ContentIdea(
                    title = item["title"] ?: "Untitled Concept",
                    angle = item["angle"] ?: "Unique perspective",
                    hookSnippet = item["hookSnippet"] ?: "",
                    whyViral = item["whyViral"] ?: "",
                    estimatedRetention = item["estimatedRetention"] ?: "High",
                    topic = topic,
                    niche = niche,
                    platform = platform
                )
            }
            database.ideaDao().insertIdeas(ideas)
            ideas
        }
    }

    // Room Database accessors
    fun getSavedIdeas(): Flow<List<ContentIdea>> = database.ideaDao().getAllIdeas()
    fun getSavedScripts(): Flow<List<ContentScript>> = database.scriptDao().getAllScripts()
    fun getCalendarPosts(): Flow<List<CalendarPost>> = database.calendarDao().getAllPosts()
    fun getAnalyticsRecords(): Flow<List<AnalyticsRecord>> = database.analyticsDao().getAllRecords()
    fun getChatMessages(): Flow<List<ChatMessage>> = database.chatDao().getAllMessages()

    suspend fun saveScript(script: ContentScript) = database.scriptDao().insertScript(script)
    suspend fun saveCalendarPost(post: CalendarPost) = database.calendarDao().insertPost(post)
    suspend fun saveAnalyticsRecord(record: AnalyticsRecord) = database.analyticsDao().insertRecord(record)
    suspend fun saveChatMessage(message: ChatMessage) = database.chatDao().insertMessage(message)
    suspend fun clearChat() = database.chatDao().clearHistory()
}
