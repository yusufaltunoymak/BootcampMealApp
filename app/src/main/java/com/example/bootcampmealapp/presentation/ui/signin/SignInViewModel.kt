package com.example.bootcampmealapp.presentation.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bootcampmealapp.data.response.ResponseStatus
import com.example.bootcampmealapp.domain.usecases.SignInWithEmailAndPasswordUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    private val firebaseAuth : FirebaseAuth
) : ViewModel() {
    private val _signInViewState : MutableStateFlow<SignInViewState> = MutableStateFlow(SignInViewState())
    val signInViewState : StateFlow<SignInViewState> = _signInViewState.asStateFlow()


    fun signInWithEmailAndPassword(email : String, password : String){
        viewModelScope.launch {
            signInWithEmailAndPasswordUseCase(email,password).collect(){ response ->
                when(response.status){
                    ResponseStatus.LOADING -> {
                        _signInViewState.update { viewState ->
                            viewState.copy(
                                isLoading = true,
                                errorMessage = null,
                                isSignedIn = null
                            )
                        }
                    }
                    ResponseStatus.SUCCESS -> {
                        _signInViewState.update { viewState ->
                            viewState.copy(
                                isLoading = false,
                                errorMessage = null,
                                isSignedIn = true,
                                currentUser = response.data,
                            )
                        }
                        Log.d("currentu", response.data.toString())

                    }
                    else -> {
                        _signInViewState.update { viewState ->
                            viewState.copy(
                                isLoading = null,
                                errorMessage = response.message
                            )
                        }
                    }
                }
            }
        }
    }
}