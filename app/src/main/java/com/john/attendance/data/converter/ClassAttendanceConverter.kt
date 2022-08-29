package com.john.attendance.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ClassAttendanceConverter {
    @TypeConverter
    fun StringToList(string: String?): List<Int>? {
        return Gson().fromJson(string, object : TypeToken<List<Int?>?>() {}.type)
    }

    @TypeConverter
    fun ListToString(value: List<Int>?): String? {
        val listType: Type = object : TypeToken<ArrayList<Int?>?>() {}.type
        return Gson().fromJson("${value}", listType)
    }
}