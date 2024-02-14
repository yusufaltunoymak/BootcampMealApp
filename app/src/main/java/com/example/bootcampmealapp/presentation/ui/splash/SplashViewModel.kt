package com.example.bootcampmealapp.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SplashViewModel : ViewModel() {
    private val _navigateToSignIn = MutableLiveData<Unit>()
    val navigateToSignIn : LiveData<Unit> get() = _navigateToSignIn

    init {
        startSplash()
    }

    private fun startSplash() {
        viewModelScope.launch {
            delay(2.seconds)
            _navigateToSignIn.value = Unit
        }
    }
}