package com.example.bootcampmealapp.presentation.ui.signin

import com.example.bootcampmealapp.domain.model.remote.User

data class SignInViewState(
    val isLoading : Boolean? = null,
    val isSignedIn : Boolean? = null,
    val currentUser : User?= null,
    val errorMessage : String? = null
)
