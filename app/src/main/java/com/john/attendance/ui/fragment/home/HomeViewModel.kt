package com.john.attendance.ui.fragment.home

import com.john.attendance.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel(
    intents: Channel<HomeIntents> = Channel(Channel.CONFLATED),
    state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Idle)
) : BaseViewModel<HomeIntents, HomeState>(intents) {
    override suspend fun handleIntents() {

    }

    override fun viewStateReducer(previousState: HomeState, newState: HomeState): HomeState {
        return newState
    }
}