package com.john.attendance.ui.fragment.login

import com.john.attendance.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow

class LoginViewModel(
    intents: Channel<LoginIntents> = Channel(Channel.CONFLATED),
    state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
) : BaseViewModel<LoginIntents, LoginState>(intents) {
    private val _viewState: MutableStateFlow<LoginState> = state
    val viewState: StateFlow<LoginState>
        get() = this._viewState

    override suspend fun handleIntents() {
        this.intents.consumeAsFlow().collect {
            when (it) {
                is LoginIntents.GoIdle -> {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, LoginState.Idle)
                }

                is LoginIntents.LoginRequest -> {
                    this._viewState.value =
                        this.viewStateReducer(this._viewState.value, LoginState.Loading())
                    delay(1000)
                    this._viewState.value =
                        this.viewStateReducer(
                            this._viewState.value,
                            validateLoginData(it.username, it.password)
                        )
                }
            }
        }
    }

    private fun validateLoginData(username: String, password: String): LoginState {
        if (username.isBlank())
            return LoginState.Error("Username is Empty")
        else if (password.isBlank())
            return LoginState.Error("Password is Empty")
        else if (username.equals("admin", true).not() ||
            password.equals("123456", true).not()
        )
            return LoginState.Error("Username or password is wrong, please contact admin")
        else
            return LoginState.Success<Boolean>()
    }

    override fun viewStateReducer(previousState: LoginState, newState: LoginState): LoginState {
        return when (newState) {
            is LoginState.Idle,
            is LoginState.Error,
            is LoginState.Loading,
            LoginState.Success<Boolean>() -> {
                newState
            }
            else -> {
                newState
            }
        }

    }
}