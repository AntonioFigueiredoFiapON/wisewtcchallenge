package com.wtc.crm.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuickNoteRepository @Inject constructor(
    private val quickNoteDao: QuickNoteDao
) {
    fun getNotesByClient(clientId: String): Flow<List<QuickNote>> = quickNoteDao.getNotesByClient(clientId)
    
    fun getNotesByAuthor(authorId: String): Flow<List<QuickNote>> = quickNoteDao.getNotesByAuthor(authorId)
    
    suspend fun insertNote(note: QuickNote): Long = quickNoteDao.insertNote(note)
    
    suspend fun updateNote(note: QuickNote) = quickNoteDao.updateNote(note)
    
    suspend fun deleteNote(note: QuickNote) = quickNoteDao.deleteNote(note)
}

