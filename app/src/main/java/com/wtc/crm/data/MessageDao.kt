package com.wtc.crm.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE (fromUserId = :userId OR toUserId = :userId OR groupId IS NOT NULL) ORDER BY timestamp DESC")
    fun getMessagesByUser(userId: String): Flow<List<Message>>
    
    @Query("SELECT * FROM messages WHERE groupId = :groupId ORDER BY timestamp DESC")
    fun getMessagesByGroup(groupId: String): Flow<List<Message>>
    
    @Query("SELECT * FROM messages WHERE fromUserId = :fromId AND toUserId = :toId ORDER BY timestamp DESC")
    fun getConversation(fromId: String, toId: String): Flow<List<Message>>
    
    @Query("SELECT * FROM messages WHERE isRead = 0 AND (toUserId = :userId OR groupId IS NOT NULL) ORDER BY timestamp DESC")
    fun getUnreadMessages(userId: String): Flow<List<Message>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<Message>)
    
    @Update
    suspend fun updateMessage(message: Message)
    
    @Query("UPDATE messages SET isRead = 1 WHERE id = :messageId")
    suspend fun markAsRead(messageId: String)
    
    @Delete
    suspend fun deleteMessage(message: Message)
}

