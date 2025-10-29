package com.wtc.crm.data

import com.wtc.crm.data.firebase.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CampaignRepository @Inject constructor(
    private val campaignDao: CampaignDao,
    private val firestoreRepository: FirestoreRepository
) {
    fun getAllCampaigns(): Flow<List<Campaign>> = campaignDao.getAllCampaigns()
    
    suspend fun getCampaignById(campaignId: String): Campaign? = campaignDao.getCampaignById(campaignId)
    
    fun getSentCampaigns(): Flow<List<Campaign>> = campaignDao.getSentCampaigns()
    
    fun getPendingCampaigns(): Flow<List<Campaign>> = campaignDao.getPendingCampaigns()
    
    suspend fun insertCampaign(campaign: Campaign) {
        campaignDao.insertCampaign(campaign)
        // Sincroniza com Firestore
        try {
            firestoreRepository.saveCampaign(campaign)
        } catch (e: Exception) {
            // Se Firestore não estiver configurado, continua funcionando apenas com Room
        }
    }
    
    suspend fun updateCampaign(campaign: Campaign) {
        campaignDao.updateCampaign(campaign)
        try {
            firestoreRepository.saveCampaign(campaign)
        } catch (e: Exception) {}
    }
    
    suspend fun deleteCampaign(campaign: Campaign) {
        campaignDao.deleteCampaign(campaign)
    }
}

