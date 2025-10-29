package com.wtc.crm.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.wtc.crm.data.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {
    
    suspend fun logout() {
        sessionManager.clearSession()
    }
}

