package com.wtc.crm.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wtc.crm.ui.screens.*

@Composable
fun WTCApp() {
    val navController = rememberNavController()
    
    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route
        ) {
            composable(Screen.Login.route) {
                LoginScreen(navController = navController)
            }
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Conversations.route) {
                ConversationsScreen(navController = navController)
            }
            composable(Screen.Chat.route) { backStackEntry ->
                val chatId = backStackEntry.arguments?.getString("chatId") ?: "default"
                ChatScreen(navController = navController, chatId = chatId)
            }
            composable(Screen.Clients.route) {
                ClientsScreen(navController = navController)
            }
            composable(Screen.Campaigns.route) {
                CampaignsScreen(navController = navController)
            }
            composable(Screen.CreateCampaign.route) {
                CreateCampaignScreen(navController = navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }
        }
    }
}

