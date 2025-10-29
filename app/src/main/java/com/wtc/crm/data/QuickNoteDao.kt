package com.wtc.crm.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuickNoteDao {
    @Query("SELECT * FROM quick_notes WHERE clientId = :clientId ORDER BY timestamp DESC")
    fun getNotesByClient(clientId: String): Flow<List<QuickNote>>
    
    @Query("SELECT * FROM quick_notes WHERE authorId = :authorId ORDER BY timestamp DESC")
    fun getNotesByAuthor(authorId: String): Flow<List<QuickNote>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: QuickNote): Long
    
    @Update
    suspend fun updateNote(note: QuickNote)
    
    @Delete
    suspend fun deleteNote(note: QuickNote)
}

