package com.john.attendance.ui.fragment.login

import com.john.attendance.base.ViewState

sealed class LoginState(
    open val isLoading: Boolean = false,
    open val error: String? = null
) : ViewState {
    object Idle : LoginState()
    data class Loading(override val isLoading: Boolean = true) : LoginState()
    data class Success<T>(val data: T? = null) : LoginState()
    data class Error(override val error: String? = null) : LoginState()
}