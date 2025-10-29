package com.wtc.crm.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "session_prefs")

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore
    
    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_ROLE_KEY = stringPreferencesKey("user_role")
        private val IS_LOGGED_IN_KEY = stringPreferencesKey("is_logged_in")
    }
    
    suspend fun saveSession(user: User) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = user.id
            preferences[USER_EMAIL_KEY] = user.email
            preferences[USER_ROLE_KEY] = user.role.name
            preferences[IS_LOGGED_IN_KEY] = "true"
        }
    }
    
    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    
    val currentUserId: Flow<String?> = dataStore.data.map { it[USER_ID_KEY] }
    
    val currentUserRole: Flow<UserRole?> = dataStore.data.map { 
        it[USER_ROLE_KEY]?.let { role -> UserRole.valueOf(role) }
    }
    
    val isLoggedIn: Flow<Boolean> = dataStore.data.map { 
        it[IS_LOGGED_IN_KEY] == "true"
    }
    
    suspend fun getCurrentUserId(): String? {
        return try {
            dataStore.data.first()[USER_ID_KEY]
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun getCurrentUserRole(): UserRole? {
        return try {
            val roleStr = dataStore.data.first()[USER_ROLE_KEY]
            roleStr?.let { UserRole.valueOf(it) }
        } catch (e: Exception) {
            null
        }
    }
}

