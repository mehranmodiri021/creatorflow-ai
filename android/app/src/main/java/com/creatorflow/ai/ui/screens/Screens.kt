package com.creatorflow.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creatorflow.ai.data.model.MonetizationTier
import com.creatorflow.ai.data.model.PostStatus
import com.creatorflow.ai.ui.components.GlassCard
import com.creatorflow.ai.ui.components.GradientButton
import com.creatorflow.ai.ui.navigation.Screen
import com.creatorflow.ai.ui.theme.*
import com.creatorflow.ai.viewmodel.CreatorStudioViewModel

@Composable
fun HomeScreen(
    viewModel: CreatorStudioViewModel,
    onNavigateTo: (String) -> Unit
) {
    val tier by viewModel.monetizationTier.collectAsState()
    val remainingRequests by viewModel.remainingDailyRequests.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CreatorFlow AI",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "سیدحمیدموسوی زاده",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricPurple
                    )
                }
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (tier == MonetizationTier.VIP) GoldAccent.copy(alpha = 0.2f) else ElectricPurple.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (tier == MonetizationTier.VIP) GoldAccent else ElectricPurple)
                ) {
                    Text(
                        text = if (tier == MonetizationTier.VIP) "VIP UNLIMITED" else "$remainingRequests Free Credits",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (tier == MonetizationTier.VIP) GoldAccent else ElectricPurple
                    )
                }
            }
        }

        item {
            // Daily Trending Hook Spotlight
            GlassCard(modifier = Modifier.fillMaxWidth(), borderColor = GoldAccent.copy(alpha = 0.4f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Bolt, contentDescription = null, tint = GoldAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Today's Viral Hook Formula", color = GoldAccent, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "\"99% of creators do this backwards... here's the 3-second fix.\"",
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text("Category: Curiosity Gap • Estimated Retention: 84%", color = TextSecondary, style = MaterialTheme.typography.bodySmall)
            }
        }

        item {
            Text("AI Studio Tools", style = MaterialTheme.typography.titleLarge, color = TextPrimary)
        }

        item {
            // Quick Studio Tools Grid
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StudioToolCard(
                        title = "Idea Generator",
                        desc = "Viral concepts & angles",
                        icon = Icons.Filled.Lightbulb,
                        color = ElectricPurple,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTo(Screen.Studio.route) }
                    )
                    StudioToolCard(
                        title = "Hook Generator",
                        desc = "3-sec attention grabbers",
                        icon = Icons.Filled.Bolt,
                        color = GoldAccent,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTo(Screen.Studio.route) }
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StudioToolCard(
                        title = "Script Writer",
                        desc = "Full Reels & Shorts scripts",
                        icon = Icons.Filled.Description,
                        color = CreatorBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTo(Screen.Studio.route) }
                    )
                    StudioToolCard(
                        title = "AI Strategist",
                        desc = "Real-time creator chat",
                        icon = Icons.Filled.SmartToy,
                        color = ElectricPurple,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTo(Screen.Studio.route) }
                    )
                }
            }
        }
    }
}

@Composable
fun StudioToolCard(
    title: String,
    desc: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(desc, color = TextSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
fun StudioScreen(
    viewModel: CreatorStudioViewModel,
    onNavigateTo: (String) -> Unit
) {
    var selectedTool by remember { mutableStateOf("Ideas") }
    var inputTopic by remember { mutableStateOf("") }
    val ideasState by viewModel.ideasState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Text("AI Content Studio", style = MaterialTheme.typography.headlineMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        // Tool Selector Tabs
        val tools = listOf("Ideas", "Hooks", "Scripts", "Captions", "Thumbnails")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tools.forEach { tool ->
                val isSelected = selectedTool == tool
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedTool = tool },
                    label = { Text(tool) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ElectricPurple,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tool Input Form
        OutlinedTextField(
            value = inputTopic,
            onValueChange = { inputTopic = it },
            label = { Text("What is your content topic?") },
            placeholder = { Text("e.g. AI Productivity Tools for Creators") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ElectricPurple,
                unfocusedBorderColor = DarkCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        GradientButton(
            text = "Generate with Gemini AI",
            onClick = {
                if (inputTopic.isNotBlank()) {
                    viewModel.generateViralIdeas(inputTopic, "Technology", "Shorts & Reels", "Creators")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Results Display
        Text("Generated Concepts", style = MaterialTheme.typography.titleLarge, color = TextPrimary)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text("Concept 1: The 3 Hidden AI Apps No One Tells You", color = GoldAccent, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Hook: \"Stop wasting 4 hours on video editing... this workflow does it in 4 minutes.\"", color = TextPrimary)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Retention angle: Speed comparison side-by-side demonstration.", color = TextSecondary, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun PlannerScreen(viewModel: CreatorStudioViewModel) {
    val posts by viewModel.calendarPosts.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Content Calendar", style = MaterialTheme.typography.headlineMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                Text("Plan and schedule your viral pipeline", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(
                onClick = { showAddDialog = true },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ElectricPurple)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Post", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (posts.isEmpty()) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("No scheduled posts yet.", color = TextSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.addCalendarPost("Top 5 Creator Tools for 2026", "YouTube Shorts", System.currentTimeMillis())
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricPurple)
                ) {
                    Text("Schedule Quick Post")
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(posts) { post ->
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(post.title, color = TextPrimary, fontWeight = FontWeight.Bold)
                            SuggestionChip(
                                onClick = {},
                                label = { Text(post.platform) }
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Status: ${post.status.name}", color = GoldAccent, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

@Composable
fun AnalyticsScreen(viewModel: CreatorStudioViewModel) {
    val records by viewModel.analyticsRecords.collectAsState()
    var viewsInput by remember { mutableStateOf("125000") }
    var likesInput by remember { mutableStateOf("8400") }
    var followersInput by remember { mutableStateOf("45200") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Creator Analytics", style = MaterialTheme.typography.headlineMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
            Text("Track engagement & receive AI strategic audits", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
        }

        item {
            // Metric Cards
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                GlassCard(modifier = Modifier.weight(1f)) {
                    Text("Views", color = TextSecondary, style = MaterialTheme.typography.labelSmall)
                    Text(viewsInput, color = TextPrimary, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                }
                GlassCard(modifier = Modifier.weight(1f)) {
                    Text("Engagement", color = TextSecondary, style = MaterialTheme.typography.labelSmall)
                    Text("6.72%", color = GoldAccent, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            GlassCard(modifier = Modifier.fillMaxWidth(), borderColor = CreatorBlue) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = CreatorBlue)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("AI Strategic Growth Audit", color = CreatorBlue, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("• Retention Dropoff: Strongest between 0:02 and 0:05.", color = TextPrimary)
                Text("• Action Plan: Replace verbal intro with direct visual proof cut.", color = TextPrimary)
                Text("• Hashtag Scaling: Shift to 3 broad tags and 4 micro-niche tags.", color = TextPrimary)
            }
        }
    }
}

@Composable
fun ProfileScreen(viewModel: CreatorStudioViewModel) {
    val tier by viewModel.monetizationTier.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Creator Profile", style = MaterialTheme.typography.headlineMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
        }

        item {
            GlassCard(modifier = Modifier.fillMaxWidth(), borderColor = if (tier == MonetizationTier.VIP) GoldAccent else ElectricPurple) {
                Text("Plan: ${tier.name}", color = if (tier == MonetizationTier.VIP) GoldAccent else ElectricPurple, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    if (tier == MonetizationTier.VIP) "Unlimited AI generation, no advertisements, and premium viral templates enabled."
                    else "Free Plan: Daily limited credits. Watch a rewarded ad or upgrade to VIP.",
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))
                if (tier == MonetizationTier.FREE) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { viewModel.upgradeToVip() },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)
                        ) {
                            Text("Upgrade to VIP", color = Color.Black)
                        }
                        OutlinedButton(
                            onClick = { viewModel.rewardAdWatched() },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricPurple)
                        ) {
                            Text("Watch Ad (+3 Credits)")
                        }
                    }
                }
            }
        }

        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("App & Developer Information", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Application: CreatorFlow AI (هوش مصنوعی کریتورفلو)", color = TextSecondary)
                Text("Developer / Publisher: سیدحمیدموسوی زاده", color = TextSecondary)
                Text("Category: AI Productivity / Creator Tools", color = TextSecondary)
                Text("Version: 1.0.0 (API 26 - 35)", color = TextSecondary)
                Text("Architecture: Jetpack Compose + MVVM + Room + Retrofit", color = TextSecondary)
                Text("AI Engine: Gemini 3.8 Flash", color = TextSecondary)
            }
        }
    }
}

@Composable
fun ChatAssistantScreen(viewModel: CreatorStudioViewModel) {
    val messages by viewModel.chatMessages.collectAsState()
    var textInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Text("AI Creator Strategist", style = MaterialTheme.typography.headlineMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
        Text("Personal content growth consultant developed by سیدحمیدموسوی زاده", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { msg ->
                val isUser = msg.sender == "user"
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isUser) ElectricPurple else DarkSurface,
                        border = if (!isUser) androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder) else null,
                        modifier = Modifier.widthIn(max = 280.dp)
                    ) {
                        Text(
                            text = msg.text,
                            modifier = Modifier.padding(12.dp),
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = { Text("Ask your content strategist...") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricPurple,
                    unfocusedBorderColor = DarkCardBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (textInput.isNotBlank()) {
                        viewModel.sendChatMessage(textInput)
                        textInput = ""
                    }
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ElectricPurple)
            ) {
                Icon(Icons.Filled.Send, contentDescription = "Send", tint = Color.White)
            }
        }
    }
}
