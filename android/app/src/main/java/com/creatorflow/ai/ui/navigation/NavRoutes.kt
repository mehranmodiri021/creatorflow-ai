package com.creatorflow.ai.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Studio : Screen("studio", "AI Studio", Icons.Filled.AutoAwesome)
    object Planner : Screen("planner", "Planner", Icons.Filled.CalendarMonth)
    object Analytics : Screen("analytics", "Analytics", Icons.Filled.BarChart)
    object Profile : Screen("profile", "Profile", Icons.Filled.Person)

    // Studio sub-destinations
    object IdeaGen : Screen("studio/ideas", "Idea Generator", Icons.Filled.Lightbulb)
    object HookGen : Screen("studio/hooks", "Hook Generator", Icons.Filled.Bolt)
    object ScriptWriter : Screen("studio/scripts", "Script Writer", Icons.Filled.Description)
    object CaptionGen : Screen("studio/captions", "Caption Studio", Icons.Filled.Chat)
    object ThumbnailStudio : Screen("studio/thumbnails", "Thumbnail Studio", Icons.Filled.Image)
    object AIChat : Screen("studio/chat", "AI Strategist", Icons.Filled.SmartToy)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Studio,
    Screen.Planner,
    Screen.Analytics,
    Screen.Profile
)
