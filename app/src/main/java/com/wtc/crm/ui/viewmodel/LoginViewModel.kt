package com.wtc.crm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wtc.crm.data.SessionManager
import com.wtc.crm.data.User
import com.wtc.crm.data.UserRepository
import com.wtc.crm.data.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    suspend fun login(email: String, password: String, isOperator: Boolean): Result<User> {
        return try {
            _uiState.value = LoginUiState.Loading
            
            // Mock authentication - substituir por Firebase Auth depois
            delay(1000) // Simula chamada de rede
            
            val role = if (isOperator) UserRole.OPERATOR else UserRole.CLIENT
            
            // Verifica se usuário existe, se não cria um mock
            var user = userRepository.getUserByEmail(email)
            if (user == null) {
                user = User(
                    id = "user_${System.currentTimeMillis()}",
                    name = if (isOperator) "Operador" else "Cliente",
                    email = email,
                    role = role,
                    status = "active"
                )
                userRepository.insertUser(user)
            }
            
            // Salva sessão
            sessionManager.saveSession(user)
            
            _uiState.value = LoginUiState.Success(user)
            Result.success(user)
        } catch (e: Exception) {
            _uiState.value = LoginUiState.Error(e.message ?: "Erro ao fazer login")
            Result.failure(e)
        }
    }
    
    suspend fun checkSession(): User? {
        return try {
            val userId = sessionManager.getCurrentUserId()
            if (userId != null) {
                userRepository.getUserById(userId)
            } else null
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun logout() {
        sessionManager.clearSession()
        _uiState.value = LoginUiState.Idle
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: User) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

