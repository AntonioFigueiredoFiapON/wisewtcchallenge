package com.wtc.crm.data

import com.wtc.crm.data.firebase.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val firestoreRepository: FirestoreRepository
) {
    fun getMessagesByUser(userId: String): Flow<List<Message>> {
        // Por enquanto usa apenas Room - Firestore pode ser habilitado depois
        return messageDao.getMessagesByUser(userId)
        // TODO: Habilitar sincronização com Firestore quando necessário
        /*
        return firestoreRepository.observeMessagesByUser(userId)
            .onEach { messages ->
                messages.forEach { message ->
                    try {
                        messageDao.insertMessage(message)
                    } catch (e: Exception) {}
                }
            }
            .catch { messageDao.getMessagesByUser(userId) }
        */
    }
    
    fun getMessagesByGroup(groupId: String): Flow<List<Message>> {
        return messageDao.getMessagesByGroup(groupId)
    }
    
    fun getConversation(fromId: String, toId: String): Flow<List<Message>> {
        return messageDao.getConversation(fromId, toId)
    }
    
    fun getUnreadMessages(userId: String): Flow<List<Message>> = 
        messageDao.getUnreadMessages(userId)
    
    suspend fun insertMessage(message: Message) {
        // Salva no Room primeiro para resposta rápida
        messageDao.insertMessage(message)
        // Sincroniza com Firestore (opcional - funciona mesmo sem Firebase configurado)
        try {
            firestoreRepository.saveMessage(message)
        } catch (e: Exception) {
            // Se Firestore não estiver configurado, continua funcionando apenas com Room
        }
    }
    
    suspend fun insertMessages(messages: List<Message>) {
        messageDao.insertMessages(messages)
        messages.forEach { message ->
            try {
                firestoreRepository.saveMessage(message)
            } catch (e: Exception) {}
        }
    }
    
    suspend fun updateMessage(message: Message) {
        messageDao.updateMessage(message)
        try {
            firestoreRepository.saveMessage(message)
        } catch (e: Exception) {}
    }
    
    suspend fun markAsRead(messageId: String) {
        messageDao.markAsRead(messageId)
        // Atualiza no Firestore também se necessário
    }
    
    suspend fun deleteMessage(message: Message) {
        messageDao.deleteMessage(message)
    }
}

