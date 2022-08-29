package com.john.attendance.ui.fragment.home

import com.john.attendance.base.ViewState

sealed class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class Success<T>(val data: T? = null) : HomeState()
    object Error : HomeState()
}