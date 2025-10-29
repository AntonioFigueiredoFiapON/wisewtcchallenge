package com.wtc.crm.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients")
    fun getAllClients(): Flow<List<Client>>
    
    @Query("SELECT * FROM clients WHERE id = :clientId")
    suspend fun getClientById(clientId: String): Client?
    
    @Query("SELECT * FROM clients WHERE name LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%'")
    fun searchClients(query: String): Flow<List<Client>>
    
    @Query("SELECT * FROM clients WHERE status = :status")
    fun filterByStatus(status: String): Flow<List<Client>>
    
    @Query("SELECT * FROM clients WHERE score >= :minScore")
    fun filterByScore(minScore: Int): Flow<List<Client>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: Client)
    
    @Update
    suspend fun updateClient(client: Client)
    
    @Delete
    suspend fun deleteClient(client: Client)
}

