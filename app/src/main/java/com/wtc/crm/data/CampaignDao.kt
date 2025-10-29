package com.wtc.crm.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CampaignDao {
    @Query("SELECT * FROM campaigns ORDER BY createdAt DESC")
    fun getAllCampaigns(): Flow<List<Campaign>>
    
    @Query("SELECT * FROM campaigns WHERE id = :campaignId")
    suspend fun getCampaignById(campaignId: String): Campaign?
    
    @Query("SELECT * FROM campaigns WHERE sentAt IS NOT NULL")
    fun getSentCampaigns(): Flow<List<Campaign>>
    
    @Query("SELECT * FROM campaigns WHERE sentAt IS NULL")
    fun getPendingCampaigns(): Flow<List<Campaign>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampaign(campaign: Campaign)
    
    @Update
    suspend fun updateCampaign(campaign: Campaign)
    
    @Delete
    suspend fun deleteCampaign(campaign: Campaign)
}

