package com.wtc.crm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wtc.crm.data.Campaign
import com.wtc.crm.data.CampaignRepository
import com.wtc.crm.data.MessageAction
import com.wtc.crm.data.MessageRepository
import com.wtc.crm.data.MessageType
import com.wtc.crm.data.SegmentType
import com.wtc.crm.data.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CampaignViewModel @Inject constructor(
    private val campaignRepository: CampaignRepository,
    private val messageRepository: MessageRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    
    val campaigns: StateFlow<List<Campaign>> = campaignRepository.getAllCampaigns()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val sentCampaigns: StateFlow<List<Campaign>> = campaignRepository.getSentCampaigns()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun createCampaign(
        name: String,
        title: String,
        body: String,
        url: String? = null,
        actions: List<MessageAction> = emptyList(),
        actionUrls: Map<String, String> = emptyMap(),
        segmentType: SegmentType,
        segmentValue: String,
        sendImmediately: Boolean = false
    ) {
        viewModelScope.launch {
            val campaign = Campaign(
                id = UUID.randomUUID().toString(),
                name = name,
                title = title,
                body = body,
                url = url,
                actions = actions,
                actionUrls = actionUrls,
                segmentType = segmentType,
                segmentValue = segmentValue,
                scheduledAt = if (sendImmediately) null else Date(),
                sentAt = if (sendImmediately) Date() else null,
                createdAt = Date()
            )
            
            campaignRepository.insertCampaign(campaign)
            
            // Se enviar imediatamente, cria mensagens para cada destinatário
            if (sendImmediately) {
                sendCampaignMessages(campaign)
            }
        }
    }
    
    private suspend fun sendCampaignMessages(campaign: Campaign) {
        val currentUserId = sessionManager.getCurrentUserId() ?: return
        
        // Mock - em produção, buscar destinatários baseado no segmento
        when (campaign.segmentType) {
            SegmentType.ALL -> {
                // Enviar para todos os clientes
                // Por enquanto, cria uma mensagem genérica
                val message = com.wtc.crm.data.Message(
                    id = UUID.randomUUID().toString(),
                    fromUserId = currentUserId,
                    toUserId = null,
                    groupId = campaign.segmentValue.takeIf { campaign.segmentType == SegmentType.GROUP },
                    type = MessageType.CAMPAIGN,
                    title = campaign.title,
                    body = campaign.body,
                    url = campaign.url,
                    actions = campaign.actions,
                    actionUrls = campaign.actionUrls,
                    timestamp = Date()
                )
                messageRepository.insertMessage(message)
            }
            SegmentType.GROUP -> {
                val message = com.wtc.crm.data.Message(
                    id = UUID.randomUUID().toString(),
                    fromUserId = currentUserId,
                    groupId = campaign.segmentValue,
                    type = MessageType.CAMPAIGN,
                    title = campaign.title,
                    body = campaign.body,
                    url = campaign.url,
                    actions = campaign.actions,
                    actionUrls = campaign.actionUrls,
                    timestamp = Date()
                )
                messageRepository.insertMessage(message)
            }
            SegmentType.INDIVIDUAL -> {
                val message = com.wtc.crm.data.Message(
                    id = UUID.randomUUID().toString(),
                    fromUserId = currentUserId,
                    toUserId = campaign.segmentValue,
                    type = MessageType.CAMPAIGN,
                    title = campaign.title,
                    body = campaign.body,
                    url = campaign.url,
                    actions = campaign.actions,
                    actionUrls = campaign.actionUrls,
                    timestamp = Date()
                )
                messageRepository.insertMessage(message)
            }
            SegmentType.TAGS -> {
                // Por enquanto, trata como grupo
                val message = com.wtc.crm.data.Message(
                    id = UUID.randomUUID().toString(),
                    fromUserId = currentUserId,
                    groupId = campaign.segmentValue,
                    type = MessageType.CAMPAIGN,
                    title = campaign.title,
                    body = campaign.body,
                    url = campaign.url,
                    actions = campaign.actions,
                    actionUrls = campaign.actionUrls,
                    timestamp = Date()
                )
                messageRepository.insertMessage(message)
            }
        }
    }
    
    fun sendPendingCampaign(campaignId: String) {
        viewModelScope.launch {
            val campaign = campaignRepository.getCampaignById(campaignId)
            campaign?.let {
                val updated = it.copy(sentAt = Date())
                campaignRepository.updateCampaign(updated)
                sendCampaignMessages(updated)
            }
        }
    }
}

