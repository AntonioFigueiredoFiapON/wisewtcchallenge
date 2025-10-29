package com.wtc.crm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class SegmentType {
    ALL,
    GROUP,
    INDIVIDUAL,
    TAGS
}

/**
 * Modelo de dados para campanhas
 */
@Entity(tableName = "campaigns")
data class Campaign(
    @PrimaryKey
    val id: String,
    val name: String,
    val title: String,
    val body: String,
    val url: String? = null,
    val actions: List<MessageAction> = emptyList(),
    val actionUrls: Map<String, String> = emptyMap(),
    val segmentType: SegmentType,
    val segmentValue: String, // ID do grupo ou lista de IDs
    val scheduledAt: Date? = null,
    val sentAt: Date? = null,
    val createdAt: Date = Date()
)

