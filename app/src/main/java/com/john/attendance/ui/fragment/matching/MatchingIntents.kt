package com.john.attendance.ui.fragment.matching

import com.john.attendance.data.local.models.Matching


sealed class MatchingIntents{
    object GetDataRequest : MatchingIntents()
    object GetMatchesDataRequest : MatchingIntents()
    data class AddMatch(val match: Matching) : MatchingIntents()
}
