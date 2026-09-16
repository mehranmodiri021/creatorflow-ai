package com.creatorflow.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.creatorflow.ai.ui.navigation.Screen
import com.creatorflow.ai.ui.navigation.bottomNavItems
import com.creatorflow.ai.ui.screens.*
import com.creatorflow.ai.ui.theme.CreatorFlowTheme
import com.creatorflow.ai.ui.theme.DarkBackground
import com.creatorflow.ai.ui.theme.DarkSurface
import com.creatorflow.ai.ui.theme.ElectricPurple
import com.creatorflow.ai.ui.theme.TextSecondary
import com.creatorflow.ai.viewmodel.CreatorStudioViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CreatorFlowAI)
        super.onCreate(savedInstanceState)

        val app = application as CreatorFlowApplication
        val viewModel = CreatorStudioViewModel(app.repository)

        setContent {
            CreatorFlowTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    containerColor = DarkBackground,
                    bottomBar = {
                        NavigationBar(
                            containerColor = DarkSurface
                        ) {
                            bottomNavItems.forEach { screen ->
                                val selected = currentRoute == screen.route
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = screen.icon,
                                            contentDescription = screen.title
                                        )
                                    },
                                    label = { Text(screen.title) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = ElectricPurple,
                                        selectedTextColor = ElectricPurple,
                                        unselectedIconColor = TextSecondary,
                                        unselectedTextColor = TextSecondary,
                                        indicatorColor = ElectricPurple.copy(alpha = 0.15f)
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(
                                viewModel = viewModel,
                                onNavigateTo = { route -> navController.navigate(route) }
                            )
                        }
                        composable(Screen.Studio.route) {
                            StudioScreen(
                                viewModel = viewModel,
                                onNavigateTo = { route -> navController.navigate(route) }
                            )
                        }
                        composable(Screen.Planner.route) {
                            PlannerScreen(viewModel = viewModel)
                        }
                        composable(Screen.Analytics.route) {
                            AnalyticsScreen(viewModel = viewModel)
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
