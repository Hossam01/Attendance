package com.john.attendance.ui.fragment.matching

import com.john.attendance.base.ViewState

sealed class MatchingState(
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState {
    object Idle : MatchingState()
    object Loading : MatchingState()
    object refresh : MatchingState()
    data class Success<T>(val data: T? = null) : MatchingState()
    data class SuccessMatch<T>(val data: T? = null) : MatchingState()
    object Error : MatchingState()}
