package com.wtc.crm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Modelo de dados para anotações rápidas por cliente
 */
@Entity(tableName = "quick_notes")
data class QuickNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val clientId: String,
    val content: String,
    val authorId: String,
    val timestamp: Date = Date()
)

