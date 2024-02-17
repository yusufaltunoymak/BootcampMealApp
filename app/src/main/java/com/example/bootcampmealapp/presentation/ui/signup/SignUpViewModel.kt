package com.example.bootcampmealapp.presentation.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bootcampmealapp.data.response.ResponseStatus
import com.example.bootcampmealapp.domain.usecases.CreateUserWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase

) : ViewModel() {
    private var _signUpViewState = MutableStateFlow(SignUpViewState())
    val signUpViewState: StateFlow<SignUpViewState> = _signUpViewState

    fun createUserWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            createUserWithEmailAndPasswordUseCase.invoke(email, password).collect() { response ->
                when (response.status) {
                    ResponseStatus.LOADING -> {
                        _signUpViewState.update { viewState ->
                            viewState.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    ResponseStatus.SUCCESS -> {
                        _signUpViewState.update { viewState ->
                            viewState.copy(
                                isSignUp = response.data,
                                isLoading = false,
                                errorMessage = null
                            )
                        }

                    }

                    else -> {
                        _signUpViewState.update { viewState ->
                            viewState.copy(
                                isLoading = false,
                                errorMessage = response.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun resetError() {
        _signUpViewState.update { viewState ->
            viewState.copy(
                errorMessage = null
            )
        }
    }
}