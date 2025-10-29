package com.wtc.crm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wtc.crm.data.SegmentType
import com.wtc.crm.ui.navigation.Screen
import com.wtc.crm.ui.viewmodel.CampaignViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCampaignScreen(
    navController: NavController,
    viewModel: CampaignViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var segmentType by remember { mutableStateOf<SegmentType>(SegmentType.ALL) }
    var sendImmediately by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    val scope = rememberCoroutineScope()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Campanha") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Nome da Campanha
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome da Campanha *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Label, contentDescription = null) },
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Título
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Title, contentDescription = null) },
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Corpo da Mensagem
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                label = { Text("Mensagem *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                leadingIcon = { Icon(Icons.Default.Message, contentDescription = null) },
                minLines = 5,
                maxLines = 10
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // URL (opcional)
            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("URL (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Link, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Tipo de Segmento
            Text(
                text = "Segmento",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            SegmentedButton(
                segmentType = segmentType,
                onSegmentSelected = { segmentType = it }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Envio Imediato
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = sendImmediately,
                    onCheckedChange = { sendImmediately = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Enviar imediatamente",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Botão Criar
            Button(
                onClick = {
                    if (name.isBlank() || title.isBlank() || body.isBlank()) {
                        errorMessage = "Preencha todos os campos obrigatórios"
                        showErrorDialog = true
                    } else {
                        isLoading = true
                        scope.launch {
                            try {
                                viewModel.createCampaign(
                                    name = name,
                                    title = title,
                                    body = body,
                                    url = if (url.isNotBlank()) url else null,
                                    segmentType = segmentType,
                                    segmentValue = "",
                                    sendImmediately = sendImmediately
                                )
                                isLoading = false
                                showSuccessDialog = true
                            } catch (e: Exception) {
                                isLoading = false
                                errorMessage = e.message ?: "Erro ao criar campanha"
                                showErrorDialog = true
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Criando...")
                } else {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Criar Campanha")
                }
            }
        }
    }
    
    // Dialog de Sucesso
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                navController.popBackStack()
            },
            title = { Text("Campanha Criada!") },
            text = { Text("A campanha foi criada e ${if (sendImmediately) "enviada" else "salva"} com sucesso.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
    
    // Dialog de Erro
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Erro") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButton(
    segmentType: SegmentType,
    onSegmentSelected: (SegmentType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = segmentType == SegmentType.ALL,
            onClick = { onSegmentSelected(SegmentType.ALL) },
            label = { Text("Todos") },
            modifier = Modifier.weight(1f)
        )
        FilterChip(
            selected = segmentType == SegmentType.GROUP,
            onClick = { onSegmentSelected(SegmentType.GROUP) },
            label = { Text("Grupo") },
            modifier = Modifier.weight(1f)
        )
        FilterChip(
            selected = segmentType == SegmentType.INDIVIDUAL,
            onClick = { onSegmentSelected(SegmentType.INDIVIDUAL) },
            label = { Text("Individual") },
            modifier = Modifier.weight(1f)
        )
        FilterChip(
            selected = segmentType == SegmentType.TAGS,
            onClick = { onSegmentSelected(SegmentType.TAGS) },
            label = { Text("Tags") },
            modifier = Modifier.weight(1f)
        )
    }
}

