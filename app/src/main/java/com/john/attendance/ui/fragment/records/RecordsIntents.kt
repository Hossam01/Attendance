package com.john.attendance.ui.fragment.records

import com.john.attendance.data.local.models.Matching
import com.john.attendance.data.local.models.Student
import com.john.attendance.ui.fragment.matching.MatchingIntents

sealed class RecordsIntents {
    object GoIdle : RecordsIntents()
    object GetDataRequest : RecordsIntents()
    object ShowAddStudent : RecordsIntents()
    data class AddStudent(val student: Student) : RecordsIntents()
    data class RemoveStudent(val student: Student) : RecordsIntents()
    data class UpdateStudent(val student: Student) : RecordsIntents()

}