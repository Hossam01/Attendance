package com.john.attendance.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentAttendClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var classID: Int,
    var studentID: Int
)
