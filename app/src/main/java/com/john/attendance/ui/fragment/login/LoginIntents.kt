package com.john.attendance.ui.fragment.login

sealed class LoginIntents {
    object GoIdle : LoginIntents()
    data class LoginRequest(val username: String, val password: String) : LoginIntents()
}