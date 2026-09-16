package com.creatorflow.ai.data.remote

import com.google.gson.annotations.SerializedName

// Gemini 3.8 Flash API DTOs
data class GeminiGenerateRequest(
    @SerializedName("contents") val contents: List<GeminiContent>,
    @SerializedName("systemInstruction") val systemInstruction: GeminiContent? = null,
    @SerializedName("generationConfig") val generationConfig: GeminiGenerationConfig? = null
)

data class GeminiContent(
    @SerializedName("role") val role: String? = null,
    @SerializedName("parts") val parts: List<GeminiPart>
)

data class GeminiPart(
    @SerializedName("text") val text: String
)

data class GeminiGenerationConfig(
    @SerializedName("temperature") val temperature: Double? = 0.7,
    @SerializedName("topP") val topP: Double? = 0.95,
    @SerializedName("responseMimeType") val responseMimeType: String? = null
)

data class GeminiGenerateResponse(
    @SerializedName("candidates") val candidates: List<GeminiCandidate>?,
    @SerializedName("usageMetadata") val usageMetadata: GeminiUsageMetadata?
)

data class GeminiCandidate(
    @SerializedName("content") val content: GeminiContent?
)

data class GeminiUsageMetadata(
    @SerializedName("promptTokenCount") val promptTokenCount: Int,
    @SerializedName("candidatesTokenCount") val candidatesTokenCount: Int,
    @SerializedName("totalTokenCount") val totalTokenCount: Int
)

// App Result wrapper
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val cause: Throwable? = null) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}
