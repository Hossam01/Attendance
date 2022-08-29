package com.john.attendance.ui.fragment.home

sealed class HomeIntents {
    object GoIdle : HomeIntents()
    data class GetDataRequest(val username: String, val password: String) : HomeIntents()
}