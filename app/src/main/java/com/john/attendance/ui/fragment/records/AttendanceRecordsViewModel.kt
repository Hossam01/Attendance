package com.john.attendance.ui.fragment.records

import android.util.Log
import com.john.attendance.base.BaseViewModel
import com.john.attendance.data.local.SchoolDatabase
import com.john.attendance.data.local.models.Student
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow

class AttendanceRecordsViewModel(
    intents: Channel<RecordsIntents> = Channel(Channel.CONFLATED),
    state: MutableStateFlow<RecordsState> = MutableStateFlow(RecordsState.Idle),
    val database: SchoolDatabase? = null
) : BaseViewModel<RecordsIntents, RecordsState>(intents) {

    private val _viewState: MutableStateFlow<RecordsState> = state
    val viewState: StateFlow<RecordsState>
        get() = this._viewState

    override suspend fun handleIntents() {
        this.intents.consumeAsFlow().collect {
            when (it) {
                is RecordsIntents.GetDataRequest -> {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, RecordsState.Loading)
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, this.getStudentsData())
                }

                is RecordsIntents.AddStudent -> {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, RecordsState.Loading)
                    this._viewState.value =
                        this.viewStateReducer(
                            this._viewState.value,
                            this.insertStudents(student = it.student)
                        )
                }

                is RecordsIntents.UpdateStudent -> {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, RecordsState.Loading)
                    this._viewState.value =
                        this.viewStateReducer(
                            this._viewState.value,
                            this.updateStudents(student = it.student)
                        )
                }

                is RecordsIntents.RemoveStudent -> {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, RecordsState.Loading)
                    this._viewState.value =
                        this.viewStateReducer(
                            this._viewState.value,
                            this.deleteStudents(student = it.student)
                        )
                }
            }
        }
    }


    private fun getStudentsData(): RecordsState {
        return with(database?.studentDao) {
            RecordsState.Success(data = this?.getStudentList())
        }
    }

    private suspend fun insertStudents(student: Student): RecordsState {
        return with(database?.studentDao)
        {
            this?.insertStudent(student)
            RecordsState.Success(data = this?.getStudentList())
        }
    }

    private suspend fun deleteStudents(student: Student): RecordsState {
        return with(database?.studentDao)
        {
            student.StudentID?.let { this?.deleteStudent(it) }
            RecordsState.Success(data = this?.getStudentList())
        }
    }

    private suspend fun updateStudents(student: Student): RecordsState {
        return with(database?.studentDao)
        {
            this?.updateStudent(student)
            RecordsState.Success(data = this?.getStudentList())
        }
    }

    override fun viewStateReducer(
        previousState: RecordsState,
        newState: RecordsState
    ): RecordsState {
        return newState
    }
}