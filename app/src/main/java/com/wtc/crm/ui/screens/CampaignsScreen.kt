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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wtc.crm.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignsScreen(navController: NavController) {
    val campaigns = remember {
        listOf(
            MockCampaign("1", "Promoção Black Friday", "Venha conferir nossas ofertas", "Enviada"),
            MockCampaign("2", "Evento Cliente VIP", "Convidamos para nosso evento exclusivo", "Pendente"),
            MockCampaign("3", "Renovação Contrato", "Seu contrato vence em breve", "Enviada"),
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campanhas") },
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
                onClick = { navController.navigate(Screen.CreateCampaign.route) },
                icon = { Icon(Icons.Default.Create, contentDescription = null) },
                text = { Text("Nova Campanha") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Campanhas Ativas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(campaigns) { campaign ->
                    CampaignCard(campaign = campaign)
                }
            }
        }
    }
}

data class MockCampaign(val id: String, val title: String, val body: String, val status: String)

@Composable
fun CampaignCard(campaign: MockCampaign) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = campaign.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                AssistChip(
                    onClick = { },
                    label = { Text(campaign.status) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (campaign.status == "Enviada")
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.errorContainer
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = campaign.body,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { }, modifier = Modifier.weight(1f)) {
                    Text("Ver Detalhes")
                }
                OutlinedButton(onClick = { }, modifier = Modifier.weight(1f)) {
                    Text("Editar")
                }
            }
        }
    }
}

