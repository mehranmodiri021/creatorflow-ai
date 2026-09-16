package com.creatorflow.ai

import android.app.Application
import com.creatorflow.ai.data.local.AppDatabase
import com.creatorflow.ai.data.remote.GeminiRepository

class CreatorFlowApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val repository: GeminiRepository by lazy {
        GeminiRepository(
            database = database,
            apiKeyProvider = {
                // In production, retrieved from secure EncryptedSharedPreferences or BuildConfig
                System.getenv("GEMINI_API_KEY") ?: ""
            }
        )
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: CreatorFlowApplication
            private set
    }
}
