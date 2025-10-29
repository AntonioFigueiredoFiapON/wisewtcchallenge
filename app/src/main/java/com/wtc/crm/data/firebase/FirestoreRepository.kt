package com.wtc.crm.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.wtc.crm.data.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository para integração com Firebase Firestore
 * Sincroniza dados com banco de dados em tempo real na nuvem
 */
@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    
    // ========== USERS ==========
    
    suspend fun saveUser(user: User) {
        try {
            val userMap = mapOf(
                "id" to user.id,
                "name" to user.name,
                "email" to user.email,
                "role" to user.role.name,
                "avatarUrl" to (user.avatarUrl ?: ""),
                "phone" to (user.phone ?: ""),
                "company" to (user.company ?: ""),
                "tags" to user.tags,
                "status" to user.status,
                "score" to user.score,
                "createdAt" to user.createdAt.time,
                "lastAccessAt" to (user.lastAccessAt?.time ?: System.currentTimeMillis())
            )
            firestore.collection("users").document(user.id).set(userMap).await()
        } catch (e: Exception) {
            // Se Firebase não estiver configurado, continua funcionando apenas com Room
        }
    }
    
    // ========== MESSAGES ==========
    
    suspend fun saveMessage(message: Message) {
        try {
            val messageMap = mapOf(
                "id" to message.id,
                "fromUserId" to message.fromUserId,
                "toUserId" to message.toUserId,
                "groupId" to message.groupId,
                "type" to message.type.name,
                "title" to message.title,
                "body" to message.body,
                "url" to (message.url ?: ""),
                "actions" to message.actions.map { mapOf("action" to it.action, "title" to it.title) },
                "actionUrls" to message.actionUrls,
                "isImportant" to message.isImportant,
                "timestamp" to message.timestamp.time,
                "isRead" to message.isRead
            )
            firestore.collection("messages").document(message.id).set(messageMap).await()
        } catch (e: Exception) {
            // Se Firebase não estiver configurado, continua funcionando apenas com Room
        }
    }
    
    // ========== CLIENTS ==========
    
    suspend fun saveClient(client: Client) {
        try {
            val clientMap = mapOf(
                "id" to client.id,
                "name" to client.name,
                "email" to client.email,
                "phone" to (client.phone ?: ""),
                "company" to (client.company ?: ""),
                "tags" to client.tags,
                "status" to client.status,
                "score" to client.score,
                "notes" to (client.notes ?: ""),
                "lastContactAt" to (client.lastContactAt?.time ?: System.currentTimeMillis()),
                "createdAt" to client.createdAt.time,
                "avatarUrl" to (client.avatarUrl ?: "")
            )
            firestore.collection("clients").document(client.id).set(clientMap).await()
        } catch (e: Exception) {
            // Se Firebase não estiver configurado, continua funcionando apenas com Room
        }
    }
    
    // ========== CAMPAIGNS ==========
    
    suspend fun saveCampaign(campaign: Campaign) {
        try {
            val campaignMap = mapOf(
                "id" to campaign.id,
                "name" to campaign.name,
                "title" to campaign.title,
                "body" to campaign.body,
                "url" to (campaign.url ?: ""),
                "actions" to campaign.actions.map { mapOf("action" to it.action, "title" to it.title) },
                "actionUrls" to campaign.actionUrls,
                "segmentType" to campaign.segmentType.name,
                "segmentValue" to campaign.segmentValue,
                "scheduledAt" to (campaign.scheduledAt?.time),
                "sentAt" to (campaign.sentAt?.time),
                "createdAt" to campaign.createdAt.time
            )
            firestore.collection("campaigns").document(campaign.id).set(campaignMap).await()
        } catch (e: Exception) {
            // Se Firebase não estiver configurado, continua funcionando apenas com Room
        }
    }
}
