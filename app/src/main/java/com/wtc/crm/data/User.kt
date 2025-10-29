package com.wtc.crm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class UserRole {
    OPERATOR,
    CLIENT
}

/**
 * Modelo de dados para usuários (Operadores e Clientes)
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val avatarUrl: String? = null,
    val phone: String? = null,
    val company: String? = null,
    val tags: List<String> = emptyList(),
    val status: String = "active",
    val score: Int = 0,
    val createdAt: Date = Date(),
    val lastAccessAt: Date? = null
)

