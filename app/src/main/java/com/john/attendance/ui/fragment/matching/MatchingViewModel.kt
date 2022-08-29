package com.john.attendance.ui.fragment.matching
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.john.attendance.base.BaseViewModel
import com.john.attendance.data.local.SchoolDatabase
import com.john.attendance.data.local.models.Matching
import com.john.attendance.data.local.models.Student
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MatchingViewModel(
    intents: Channel<MatchingIntents> = Channel(Channel.CONFLATED),
    state: MutableStateFlow<MatchingState> = MutableStateFlow(MatchingState.Idle),
    val database: SchoolDatabase? = null,
    ):BaseViewModel<MatchingIntents,MatchingState>(intents) {


    private val _viewState: MutableStateFlow<MatchingState> = state
    val viewState: StateFlow<MatchingState>
        get() = this._viewState




    override suspend fun handleIntents() {
        this.intents.consumeAsFlow().collect{
            when(it)
            {
                is MatchingIntents.GetDataRequest->
                {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, MatchingState.Loading)
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, this.getStudentsData())
                }

                is MatchingIntents.GetMatchesDataRequest->
                {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, MatchingState.Loading)
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, this.getMatchesData())
                }

                is MatchingIntents.AddMatch->
                {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, MatchingState.Loading)
                    this._viewState.value =
                        this.viewStateReducer(
                            this._viewState.value,
                            this.insertMatches(student = it.match)
                        )
                }
            }
        }
    }

    private fun getStudentsData(): MatchingState {

        return with(database?.studentDao) {
            MatchingState.Success(data = this?.getStudentList())
        }
    }

    private fun getMatchesData(): MatchingState {

        return with(database?.matchDao) {
            viewModelScope.launch {
                database?.matchDao?.getMatches()?.collect{
                    Log.d("TAG", "getMatchesData: ${it.toString()}")
                }
            }
            MatchingState.SuccessMatch(data = this?.getMatches())
        }
    }




    private suspend fun insertMatches(student: Matching): MatchingState{
        return with(database?.matchDao)
        {
            this?.insertMatch(student)
            MatchingState.SuccessMatch(data = getMatchesData())
        }
    }

    override fun viewStateReducer(
        previousState: MatchingState,
        newState: MatchingState
    ): MatchingState {
        return newState
    }
}