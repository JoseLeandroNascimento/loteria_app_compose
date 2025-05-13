package com.example.loteria_app.data

import androidx.room.TypeConverter
import java.util.Date


class Converters {

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun timestampToDate(time: Long): Date {
        return Date(time)
    }

}