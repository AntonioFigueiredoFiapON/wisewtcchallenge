package com.wtc.crm.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

/**
 * Type converters para Room Database
 */
class Converters {
    private val gson = Gson()
    
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return list?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun fromMessageActionList(value: String?): List<MessageAction>? {
        return value?.let {
            val listType = object : TypeToken<List<MessageAction>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun toMessageActionList(list: List<MessageAction>?): String? {
        return list?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun fromStringMap(value: String?): Map<String, String>? {
        return value?.let {
            val mapType = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson(it, mapType)
        }
    }
    
    @TypeConverter
    fun toStringMap(map: Map<String, String>?): String? {
        return map?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun fromSegmentType(value: String?): SegmentType? {
        return value?.let { SegmentType.valueOf(it) }
    }
    
    @TypeConverter
    fun toSegmentType(segmentType: SegmentType?): String? {
        return segmentType?.name
    }
    
    @TypeConverter
    fun fromUserRole(value: String?): UserRole? {
        return value?.let { UserRole.valueOf(it) }
    }
    
    @TypeConverter
    fun toUserRole(role: UserRole?): String? {
        return role?.name
    }
    
    @TypeConverter
    fun fromMessageType(value: String?): MessageType? {
        return value?.let { MessageType.valueOf(it) }
    }
    
    @TypeConverter
    fun toMessageType(messageType: MessageType?): String? {
        return messageType?.name
    }
}

