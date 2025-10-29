package com.wtc.crm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wtc.crm.data.Message
import com.wtc.crm.data.MessageRepository
import com.wtc.crm.data.MessageType
import com.wtc.crm.data.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    suspend fun loadConversation(chatId: String, otherUserId: String? = null) {
        val currentUserId = sessionManager.getCurrentUserId() ?: return
        
        viewModelScope.launch {
            val flow = if (otherUserId != null) {
                messageRepository.getConversation(currentUserId, otherUserId)
            } else {
                messageRepository.getMessagesByGroup(chatId)
            }
            
            flow.collect { messageList ->
                _messages.value = messageList.sortedBy { it.timestamp }
            }
        }
    }
    
    fun sendMessage(text: String, chatId: String, toUserId: String? = null) {
        viewModelScope.launch {
            val currentUserId = sessionManager.getCurrentUserId() ?: return@launch
            
            val message = Message(
                id = UUID.randomUUID().toString(),
                fromUserId = currentUserId,
                toUserId = toUserId,
                groupId = if (toUserId == null) chatId else null,
                type = MessageType.TEXT,
                title = "",
                body = text,
                timestamp = Date()
            )
            
            messageRepository.insertMessage(message)
        }
    }
    
    fun sendRichMessage(
        title: String,
        body: String,
        url: String? = null,
        actions: List<com.wtc.crm.data.MessageAction> = emptyList(),
        actionUrls: Map<String, String> = emptyMap(),
        chatId: String,
        toUserId: String? = null,
        type: MessageType = MessageType.CAMPAIGN
    ) {
        viewModelScope.launch {
            val currentUserId = sessionManager.getCurrentUserId() ?: return@launch
            
            val message = Message(
                id = UUID.randomUUID().toString(),
                fromUserId = currentUserId,
                toUserId = toUserId,
                groupId = if (toUserId == null) chatId else null,
                type = type,
                title = title,
                body = body,
                url = url,
                actions = actions,
                actionUrls = actionUrls,
                timestamp = Date()
            )
            
            messageRepository.insertMessage(message)
        }
    }
    
    fun markAsRead(messageId: String) {
        viewModelScope.launch {
            messageRepository.markAsRead(messageId)
        }
    }
    
    fun markAsImportant(messageId: String) {
        viewModelScope.launch {
            val message = _messages.value.find { it.id == messageId }
            message?.let {
                val updated = it.copy(isImportant = !it.isImportant)
                messageRepository.updateMessage(updated)
            }
        }
    }
}

