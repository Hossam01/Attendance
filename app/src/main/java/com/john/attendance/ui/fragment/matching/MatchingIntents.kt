package com.john.attendance.ui.fragment.matching

import com.john.attendance.data.local.models.Matching
import com.john.attendance.data.local.models.Student
import com.john.attendance.ui.fragment.records.RecordsIntents


sealed class MatchingIntents{
    object GetDataRequest : MatchingIntents()
    object GetMatchesDataRequest : MatchingIntents()
    data class AddMatch(val match: Matching) : MatchingIntents()
    object RemoveStudent : MatchingIntents()

}
