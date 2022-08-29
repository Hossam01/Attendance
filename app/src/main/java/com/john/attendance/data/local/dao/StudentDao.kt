package com.john.attendance.data.local.dao

import androidx.room.*
import com.john.attendance.data.local.models.Class
import com.john.attendance.data.local.models.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Query("Select * from student Order by attendanceTimes DESC")
    fun getStudentList(): Flow<List<Student>>

    @Query("delete from student where 1")
    suspend fun deleteAllStudents()

    @Query("delete from student where student.StudentID = :id")
    suspend fun deleteStudent(id: Int)
}