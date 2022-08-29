package com.john.attendance.data.local.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    var StudentID: Int? = null,
    var name: String = "",
    var attendanceTimes: Int = 0,
    var absenceTimes: Int = 0
) : Parcelable
