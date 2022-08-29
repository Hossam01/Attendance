package com.john.attendance.ui.fragment.records

import com.john.attendance.base.ViewState

sealed class RecordsState(
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState {
    object Idle : RecordsState()
    object Loading : RecordsState()
    data class Success<T>(val data: T? = null) : RecordsState()
    object Error : RecordsState()
}