package com.wtc.crm.data.api

import com.wtc.crm.data.*
import kotlinx.coroutines.delay
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Serviço Mock de API REST
 * Simula chamadas de API para desenvolvimento e testes
 * Pode ser substituído por retrofit + API real facilmente
 */
@Singleton
class MockApiService @Inject constructor() {
    
    // Simula delay de rede
    private suspend fun simulateNetworkDelay() {
        delay(500)
    }
    
    // ========== USERS ==========
    
    suspend fun loginUser(email: String, password: String, isOperator: Boolean): Result<User> {
        simulateNetworkDelay()
        
        return try {
            // Mock de autenticação
            if ((email == "operador@wtc.com" && password == "123456" && isOperator) ||
                (email == "cliente@wtc.com" && password == "123456" && !isOperator)) {
                
                val user = User(
                    id = if (isOperator) "operator_123" else "client_456",
                    name = if (isOperator) "Operador WTC" else "Cliente WTC",
                    email = email,
                    role = if (isOperator) UserRole.OPERATOR else UserRole.CLIENT,
                    tags = if (isOperator) listOf("operator", "admin") else listOf("client", "vip"),
                    status = "active",
                    score = if (isOperator) 100 else 85,
                    createdAt = Date()
                )
                Result.success(user)
            } else {
                Result.failure(Exception("Credenciais inválidas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // ========== MESSAGES ==========
    
    suspend fun sendMessage(message: Message): Result<Message> {
        simulateNetworkDelay()
        
        return try {
            // Mock de envio de mensagem via API
            Result.success(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getMessages(userId: String): Result<List<Message>> {
        simulateNetworkDelay()
        
        return try {
            // Mock de lista de mensagens
            val messages = listOf(
                Message(
                    id = UUID.randomUUID().toString(),
                    fromUserId = "system",
                    toUserId = userId,
                    type = MessageType.TEXT,
                    title = "Bem-vindo!",
                    body = "Olá! Estamos aqui para ajudar.",
                    timestamp = Date()
                )
            )
            Result.success(messages)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // ========== CLIENTS ==========
    
    suspend fun getClients(): Result<List<Client>> {
        simulateNetworkDelay()
        
        return try {
            // Mock de lista de clientes
            val clients = listOf(
                Client(
                    id = "client_1",
                    name = "João Silva",
                    email = "joao@email.com",
                    tags = listOf("VIP", "Premium"),
                    status = "active",
                    score = 85,
                    createdAt = Date()
                ),
                Client(
                    id = "client_2",
                    name = "Maria Santos",
                    email = "maria@email.com",
                    tags = listOf("Premium"),
                    status = "active",
                    score = 72,
                    createdAt = Date()
                ),
                Client(
                    id = "client_3",
                    name = "Pedro Costa",
                    email = "pedro@email.com",
                    tags = listOf("Básico"),
                    status = "active",
                    score = 65,
                    createdAt = Date()
                )
            )
            Result.success(clients)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createClient(client: Client): Result<Client> {
        simulateNetworkDelay()
        return Result.success(client)
    }
    
    // ========== CAMPAIGNS ==========
    
    suspend fun createCampaign(campaign: Campaign): Result<Campaign> {
        simulateNetworkDelay()
        return Result.success(campaign)
    }
    
    suspend fun getCampaigns(): Result<List<Campaign>> {
        simulateNetworkDelay()
        return Result.success(emptyList())
    }
}

