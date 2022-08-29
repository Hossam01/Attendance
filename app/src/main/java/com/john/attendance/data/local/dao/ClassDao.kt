package com.john.attendance.data.local.dao

import androidx.room.*
import com.john.attendance.data.local.models.Class
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClass(classDto: Class)

    @Query("Select * from class")
    fun getClassList(): Flow<List<Class>>

    @Query("delete from class where 1")
    suspend fun deleteAllClass()

    @Query("delete from class where class.ClassID = :id")
    suspend fun deleteClass(id: Int)
}