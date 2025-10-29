package com.wtc.crm.data

import com.wtc.crm.data.firebase.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepository @Inject constructor(
    private val clientDao: ClientDao,
    private val firestoreRepository: FirestoreRepository
) {
    fun getAllClients(): Flow<List<Client>> {
        // Por enquanto usa apenas Room - Firestore pode ser habilitado depois
        return clientDao.getAllClients()
        // TODO: Habilitar sincronização com Firestore quando necessário
    }
    
    suspend fun getClientById(clientId: String): Client? = clientDao.getClientById(clientId)
    
    fun searchClients(query: String): Flow<List<Client>> = clientDao.searchClients(query)
    
    fun filterByStatus(status: String): Flow<List<Client>> = clientDao.filterByStatus(status)
    
    fun filterByScore(minScore: Int): Flow<List<Client>> = clientDao.filterByScore(minScore)
    
    suspend fun insertClient(client: Client) {
        clientDao.insertClient(client)
        // Sincroniza com Firestore (opcional)
        try {
            firestoreRepository.saveClient(client)
        } catch (e: Exception) {}
    }
    
    suspend fun updateClient(client: Client) {
        clientDao.updateClient(client)
        // Sincroniza com Firestore (opcional)
        try {
            firestoreRepository.saveClient(client)
        } catch (e: Exception) {}
    }
    
    suspend fun deleteClient(client: Client) {
        clientDao.deleteClient(client)
    }
}

