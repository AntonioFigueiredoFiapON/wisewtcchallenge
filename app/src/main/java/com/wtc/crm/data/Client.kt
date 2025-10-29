package com.wtc.crm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Modelo de dados para clientes (visão do operador)
 */
@Entity(tableName = "clients")
data class Client(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val phone: String? = null,
    val company: String? = null,
    val tags: List<String> = emptyList(),
    val status: String = "active",
    val score: Int = 0,
    val notes: String? = null,
    val lastContactAt: Date? = null,
    val createdAt: Date = Date(),
    val avatarUrl: String? = null
)

