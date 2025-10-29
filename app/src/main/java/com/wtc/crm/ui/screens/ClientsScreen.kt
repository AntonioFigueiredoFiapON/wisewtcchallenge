package com.wtc.crm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wtc.crm.ui.navigation.Screen
import com.wtc.crm.ui.viewmodel.ClientsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(
    navController: NavController,
    viewModel: ClientsViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf("") }
    val clients by viewModel.clients.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    
    LaunchedEffect(searchText) {
        viewModel.updateSearchQuery(searchText)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clientes") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* Create new client */ },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Novo Cliente") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Buscar clientes") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Filters
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == com.wtc.crm.ui.viewmodel.FilterType.ALL,
                    onClick = { viewModel.selectFilter(com.wtc.crm.ui.viewmodel.FilterType.ALL) },
                    label = { Text("Todos") }
                )
                FilterChip(
                    selected = selectedFilter == com.wtc.crm.ui.viewmodel.FilterType.VIP,
                    onClick = { viewModel.selectFilter(com.wtc.crm.ui.viewmodel.FilterType.VIP) },
                    label = { Text("VIP") }
                )
                FilterChip(
                    selected = selectedFilter == com.wtc.crm.ui.viewmodel.FilterType.PREMIUM,
                    onClick = { viewModel.selectFilter(com.wtc.crm.ui.viewmodel.FilterType.PREMIUM) },
                    label = { Text("Premium") }
                )
                FilterChip(
                    selected = selectedFilter == com.wtc.crm.ui.viewmodel.FilterType.ACTIVE,
                    onClick = { viewModel.selectFilter(com.wtc.crm.ui.viewmodel.FilterType.ACTIVE) },
                    label = { Text("Ativos") }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (clients.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Nenhum cliente encontrado")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(clients, key = { it.id }) { client ->
                        ClientCard(
                            client = client,
                            onClick = {
                                navController.navigate("client/${client.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientCard(
    client: com.wtc.crm.data.Client,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = client.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = client.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row {
                    client.tags.take(2).forEach { tag ->
                        AssistChip(onClick = { }, label = { Text(tag) })
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Score: ${client.score}")
                }
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }
        }
    }
}

