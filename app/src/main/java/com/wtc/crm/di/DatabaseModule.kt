package com.wtc.crm.di

import android.content.Context
import androidx.room.Room
import com.wtc.crm.data.AppDatabase
import com.wtc.crm.data.CampaignDao
import com.wtc.crm.data.ClientDao
import com.wtc.crm.data.MessageDao
import com.wtc.crm.data.QuickNoteDao
import com.wtc.crm.data.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "wtc_crm_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }
    
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
    
    @Provides
    fun provideMessageDao(database: AppDatabase): MessageDao = database.messageDao()
    
    @Provides
    fun provideClientDao(database: AppDatabase): ClientDao = database.clientDao()
    
    @Provides
    fun provideCampaignDao(database: AppDatabase): CampaignDao = database.campaignDao()
    
    @Provides
    fun provideQuickNoteDao(database: AppDatabase): QuickNoteDao = database.quickNoteDao()
}

