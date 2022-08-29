package com.john.attendance.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Matching(
    @PrimaryKey(autoGenerate = true)
    var studentIDHome: Int,
    var studentIDWay: Int,
)
