package com.john.attendance.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.john.attendance.data.converter.ClassAttendanceConverter
import com.john.attendance.data.local.dao.GameDao
import com.john.attendance.data.local.dao.MatchingDao
import com.john.attendance.data.local.dao.StudentDao
import com.john.attendance.data.local.models.*

private const val DATABASE_NAME = "Database.db"


@Database(
    entities = [
        Class::class,
        Game::class,
        Student::class,
        StudentAttendClass::class,
        Matching::class
    ],
    version = 2
)
@TypeConverters(ClassAttendanceConverter::class)
abstract class SchoolDatabase : RoomDatabase() {

    abstract val gameDao: GameDao
    abstract val studentDao: StudentDao
    abstract val matchDao: MatchingDao

    companion object {
        @Volatile
        private var INSTANCE: SchoolDatabase? = null

        fun getInstance(context: Context): SchoolDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    SchoolDatabase::class.java,
                    DATABASE_NAME
                ).build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}