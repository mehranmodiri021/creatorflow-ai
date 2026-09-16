package com.creatorflow.ai.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.8-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Header("User-Agent") userAgent: String = "aistudio-build-android",
        @Body request: GeminiGenerateRequest
    ): Response<GeminiGenerateResponse>
}
