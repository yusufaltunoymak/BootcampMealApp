package com.example.bootcampmealapp.domain.repository

import com.example.bootcampmealapp.data.response.Response
import com.example.bootcampmealapp.domain.model.remote.User
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthRepository {
    suspend fun createUserWithEmailAndPassword(email : String, password : String) : Flow<Response<Boolean>>
    suspend fun signInEmailAndPassword(email: String,password: String) : Flow<Response<User>>

}