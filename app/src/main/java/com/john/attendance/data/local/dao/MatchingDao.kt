package com.john.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.john.attendance.data.local.models.Matching
import com.john.attendance.data.local.models.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: Matching)

    @Query("Select * from student where student.StudentID = :id")
    fun getStudent(id: Int): Student

    @Query("Select * from matching")
    fun getMatches(): Flow<List<Matching>>

    @Query("delete from matching where 1")
    suspend fun deleteAllStudents()
}