package com.wtc.crm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Room Database para o WTC CRM
 */
@Database(
    entities = [
        User::class,
        Message::class,
        Client::class,
        Campaign::class,
        QuickNote::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun clientDao(): ClientDao
    abstract fun campaignDao(): CampaignDao
    abstract fun quickNoteDao(): QuickNoteDao
}

