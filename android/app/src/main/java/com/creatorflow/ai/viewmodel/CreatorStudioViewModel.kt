package com.creatorflow.ai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creatorflow.ai.data.model.*
import com.creatorflow.ai.data.remote.GeminiRepository
import com.creatorflow.ai.data.remote.NetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CreatorStudioViewModel(
    private val repository: GeminiRepository
) : ViewModel() {

    // UI States
    private val _ideasState = MutableStateFlow<NetworkResult<List<ContentIdea>>>(NetworkResult.Success(emptyList()))
    val ideasState: StateFlow<NetworkResult<List<ContentIdea>>> = _ideasState.asStateFlow()

    // Room Flows
    val savedIdeas: StateFlow<List<ContentIdea>> = repository.getSavedIdeas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedScripts: StateFlow<List<ContentScript>> = repository.getSavedScripts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val calendarPosts: StateFlow<List<CalendarPost>> = repository.getCalendarPosts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val analyticsRecords: StateFlow<List<AnalyticsRecord>> = repository.getAnalyticsRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val chatMessages: StateFlow<List<ChatMessage>> = repository.getChatMessages()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Monetization & Requests Tracking
    private val _monetizationTier = MutableStateFlow(MonetizationTier.FREE)
    val monetizationTier: StateFlow<MonetizationTier> = _monetizationTier.asStateFlow()

    private val _remainingDailyRequests = MutableStateFlow(5)
    val remainingDailyRequests: StateFlow<Int> = _remainingDailyRequests.asStateFlow()

    fun upgradeToVip() {
        _monetizationTier.value = MonetizationTier.VIP
        _remainingDailyRequests.value = 9999
    }

    fun consumeRequest(): Boolean {
        if (_monetizationTier.value == MonetizationTier.VIP) return true
        if (_remainingDailyRequests.value > 0) {
            _remainingDailyRequests.value -= 1
            return true
        }
        return false
    }

    fun rewardAdWatched() {
        _remainingDailyRequests.value += 3
    }

    // AI Generation Triggers
    fun generateViralIdeas(topic: String, niche: String, platform: String, audience: String, language: String = "en") {
        if (!consumeRequest()) return

        viewModelScope.launch {
            _ideasState.value = NetworkResult.Loading
            val result = repository.generateIdeas(topic, niche, platform, audience, language)
            _ideasState.value = result
        }
    }

    fun sendChatMessage(userText: String) {
        viewModelScope.launch {
            val userMsg = ChatMessage(sender = "user", text = userText)
            repository.saveChatMessage(userMsg)

            // Simulate / trigger AI strategist reply
            val replyText = "As your social media strategist: focus on creating a high-contrast opening hook within the first 2 seconds, followed by 3 fast-paced visual value cuts. This increases average view duration by up to 38%."
            val aiMsg = ChatMessage(sender = "assistant", text = replyText)
            repository.saveChatMessage(aiMsg)
        }
    }

    fun addCalendarPost(title: String, platform: String, scheduledDate: Long) {
        viewModelScope.launch {
            repository.saveCalendarPost(
                CalendarPost(
                    title = title,
                    platform = platform,
                    scheduledDate = scheduledDate
                )
            )
        }
    }

    fun saveAnalyticsRecord(views: Long, likes: Long, followers: Long, comments: Long, saves: Long, platform: String) {
        viewModelScope.launch {
            val totalEng = likes + comments + saves
            val rate = if (views > 0) (totalEng.toDouble() / views.toDouble()) * 100.0 else 0.0
            val verdict = if (rate > 5.0) "High Retention - Scale This Format" else "Optimize Hook & Retention"

            repository.saveAnalyticsRecord(
                AnalyticsRecord(
                    platform = platform,
                    views = views,
                    likes = likes,
                    followers = followers,
                    comments = comments,
                    saves = saves,
                    engagementRate = rate,
                    aiVerdict = verdict
                )
            )
        }
    }
}
