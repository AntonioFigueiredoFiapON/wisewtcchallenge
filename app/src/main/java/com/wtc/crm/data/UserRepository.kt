package com.wtc.crm.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun getUserById(userId: String): User? = userDao.getUserById(userId)
    
    suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)
    
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
    
    fun getUsersByRole(role: UserRole): Flow<List<User>> = userDao.getUsersByRole(role)
    
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
}

