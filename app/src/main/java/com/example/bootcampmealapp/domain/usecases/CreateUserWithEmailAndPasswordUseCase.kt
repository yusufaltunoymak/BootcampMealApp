package com.example.bootcampmealapp.domain.usecases

import com.example.bootcampmealapp.data.repository.FirebaseAuthRepositoryImpl
import com.example.bootcampmealapp.data.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(private val firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl) {
    suspend operator fun invoke(email: String, password: String): Flow<Response<Boolean>> =
        firebaseAuthRepositoryImpl.createUserWithEmailAndPassword(email, password)
}