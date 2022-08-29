package com.john.attendance.ui.fragment.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.john.attendance.R
import com.john.attendance.base.BaseFragment
import com.john.attendance.base.createViewModelFactory
import com.john.attendance.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel, LoginState>() {
    override val layoutId: Int
        get() = R.layout.fragment_login

    override val viewModelClass: Class<LoginViewModel>
        get() = LoginViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
        createViewModelFactory { LoginViewModel() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.onEach { observeState(it) }.launchIn(lifecycleScope)


        binding.btnLogin.setOnClickListener {
            this.loginRequest(
                binding.usernameEdt.text.toString(),
                binding.passwordEdt.text.toString()
            )
        }
    }

    private fun loginRequest(usernameEdt: String, passwordEdt: String) {
        this.lifecycleScope.launch {
            this@LoginFragment.viewModel.intents.send(
                LoginIntents.LoginRequest(
                    usernameEdt,
                    passwordEdt
                )
            )
        }
    }

    override fun observeState(state: LoginState) {
        if (isBindingInitialized()) {
            binding.loadingIndecetor.apply {
                this.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            }
            when (state) {
                is LoginState.Idle -> {

                }
                is LoginState.Success<*> -> {
                    findNavController().navigate(R.id.homeFragment)
                }
                is LoginState.Error -> {
                    Toast.makeText(this@LoginFragment.context, state.error, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}