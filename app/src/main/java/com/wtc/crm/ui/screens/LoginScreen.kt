package com.wtc.crm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wtc.crm.ui.navigation.Screen
import com.wtc.crm.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isOperator by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is com.wtc.crm.ui.viewmodel.LoginUiState.Success -> {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo e título
        Text(
            text = "WTC CRM",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Integrador de Comunicação",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Toggle para tipo de usuário
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SegmentedButton(
                    selected = !isOperator,
                    onClick = { isOperator = false },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cliente")
                }
                SegmentedButton(
                    selected = isOperator,
                    onClick = { isOperator = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Operador")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Email input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Email")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Password input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Senha")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
            // Login button
            Button(
                onClick = {
                    scope.launch {
                        viewModel.login(email, password, isOperator)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = email.isNotBlank() && password.isNotBlank() && 
                         uiState !is com.wtc.crm.ui.viewmodel.LoginUiState.Loading
            ) {
                Text(
                    text = if (uiState is com.wtc.crm.ui.viewmodel.LoginUiState.Loading) "Entrando..." else "Entrar",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            // Error message
            if (uiState is com.wtc.crm.ui.viewmodel.LoginUiState.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (uiState as com.wtc.crm.ui.viewmodel.LoginUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Demos rápidos
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(onClick = { email = "cliente@wtc.com"; password = "123456" }) {
                Text("Demo Cliente")
            }
            TextButton(onClick = { email = "operador@wtc.com"; password = "123456" }) {
                Text("Demo Operador")
            }
        }
    }
}

@Composable
fun SegmentedButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.small
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

