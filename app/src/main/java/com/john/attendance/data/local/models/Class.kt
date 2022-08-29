package com.john.attendance.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Class(
    @PrimaryKey(autoGenerate = true)
    var ClassID: Int,
    var date: String,
)
