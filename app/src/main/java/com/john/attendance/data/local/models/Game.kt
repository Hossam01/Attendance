package com.john.attendance.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey(autoGenerate = true)
    var GameID: Int,
    var firstStudentID: Int,
    var secondStudentID: Int,
    var matchResultState: Int? = null
)
