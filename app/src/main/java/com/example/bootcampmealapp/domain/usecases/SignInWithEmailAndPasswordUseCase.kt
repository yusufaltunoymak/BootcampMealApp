package com.example.bootcampmealapp.domain.usecases

import com.example.bootcampmealapp.data.repository.FirebaseAuthRepositoryImpl
import com.example.bootcampmealapp.data.response.Response
import com.example.bootcampmealapp.domain.model.remote.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl) {
    suspend operator fun invoke(email: String, password: String) : Flow<Response<User>> =
        firebaseAuthRepositoryImpl.signInEmailAndPassword(email = email, password = password)
}