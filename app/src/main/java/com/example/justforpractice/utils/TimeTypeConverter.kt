package com.example.justforpractice.utils

import androidx.room.TypeConverter
import org.threeten.bp.OffsetTime
import org.threeten.bp.format.DateTimeFormatter

object TimeTypeConverter {
    private val formatter = DateTimeFormatter.ISO_OFFSET_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetTime(value: String?): OffsetTime? {
        return value?.let {
            return formatter.parse(value, OffsetTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(time: OffsetTime?): String? {
        return time?.format(formatter)
    }
}