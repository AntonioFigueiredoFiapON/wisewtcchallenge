package com.wtc.crm.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wtc.crm.ui.navigation.Screen
import com.wtc.crm.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var showMenu by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WTC CRM") },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Perfil") },
                            onClick = {
                                showMenu = false
                                navController.navigate(Screen.Profile.route)
                            },
                            leadingIcon = {
                                Icon(Icons.Default.AccountCircle, contentDescription = null)
                            }
                        )
                        Divider()
                        DropdownMenuItem(
                            text = { Text("Sair / Trocar Usuário") },
                            onClick = {
                                showMenu = false
                                scope.launch {
                                    viewModel.logout()
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            },
                            leadingIcon = {
                                Icon(Icons.Default.ExitToApp, contentDescription = null)
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Conversations.route) },
                    icon = { Icon(Icons.Default.Chat, contentDescription = "Conversas") },
                    label = { Text("Chat") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Clients.route) },
                    icon = { Icon(Icons.Default.People, contentDescription = "Clientes") },
                    label = { Text("Clientes") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Campaigns.route) },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = "Campanhas") },
                    label = { Text("Campanhas") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Ações rápidas
            Text(
                text = "Ações Rápidas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(quickActions) { action ->
                    QuickActionCard(
                        icon = action.icon,
                        title = action.title,
                        onClick = { /* Handle click */ }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Mensagens recentes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mensagens Recentes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { navController.navigate(Screen.Conversations.route) }) {
                    Text("Ver todas")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Lista de mensagens mock
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(Screen.Chat.createRoute("123")) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Circle,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Cliente XYZ",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Preciso de ajuda com minha conta...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Há 5 minutos",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

data class QuickAction(val icon: String, val title: String)

val quickActions = listOf(
    QuickAction("/promo", "Nova Promoção"),
    QuickAction("/agradecer", "Agradecimento"),
    QuickAction("/boleto", "Enviar Boleto"),
    QuickAction("/evento", "Criar Evento")
)

@Composable
fun QuickActionCard(icon: String, title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(100.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

