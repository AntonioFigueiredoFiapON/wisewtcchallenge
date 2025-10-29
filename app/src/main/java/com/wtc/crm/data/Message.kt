package com.wtc.crm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class MessageType {
    TEXT,
    CAMPAIGN,
    PROMOTION,
    BANNER,
    EVENT
}

/**
 * Modelo de dados para mensagens
 */
@Entity(tableName = "messages")
data class Message(
    @PrimaryKey
    val id: String,
    val fromUserId: String,
    val toUserId: String? = null, // null para mensagens de grupo
    val groupId: String? = null, // null para mensagens 1:1
    val type: MessageType,
    val title: String,
    val body: String,
    val url: String? = null,
    val actions: List<MessageAction> = emptyList(),
    val actionUrls: Map<String, String> = emptyMap(),
    val isImportant: Boolean = false,
    val timestamp: Date = Date(),
    val isRead: Boolean = false
)

data class MessageAction(
    val action: String,
    val title: String
)

