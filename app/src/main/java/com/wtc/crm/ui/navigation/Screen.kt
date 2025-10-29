package com.wtc.crm.ui.navigation

import com.wtc.crm.data.User

/**
 * Definição das telas da aplicação
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Chat : Screen("chat/{chatId}") {
        fun createRoute(chatId: String) = "chat/$chatId"
    }
    object Conversations : Screen("conversations")
    object Clients : Screen("clients")
    object ClientDetail : Screen("client/{clientId}") {
        fun createRoute(clientId: String) = "client/$clientId"
    }
    object Campaigns : Screen("campaigns")
    object CreateCampaign : Screen("create_campaign")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}

