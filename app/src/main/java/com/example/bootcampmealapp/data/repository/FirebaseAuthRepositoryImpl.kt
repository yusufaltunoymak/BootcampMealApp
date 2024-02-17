package com.example.bootcampmealapp.data.repository

import com.example.bootcampmealapp.data.response.Response
import com.example.bootcampmealapp.domain.model.remote.User
import com.example.bootcampmealapp.domain.repository.FirebaseAuthRepository
import com.example.bootcampmealapp.util.FirebaseMapper
import com.example.bootcampmealapp.util.FirebasePaths
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth : FirebaseAuth,
    private val firestore : FirebaseFirestore
): FirebaseAuthRepository {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<Boolean>> {
        val usersCollectionReference = FirebasePaths.getUsersReference()
        return callbackFlow {
            trySend(Response.Loading)
            try {
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    val createdUser = User(
                        email = email,
                        id =it.user!!.uid
                    )
                    firestore.collection(usersCollectionReference).document(createdUser.id!!)
                        .set(FirebaseMapper.userToUserEntity(user = createdUser))
                        .addOnSuccessListener {
                            trySend(Response.Success(_data = true))
                        }
                        .addOnFailureListener{
                            trySend(Response.Error(it.message.toString()))
                        }
                }
                    .addOnFailureListener {
                        trySend(Response.Error(it.message.toString()))
                    }
            }
            catch (e : FirebaseException) {
                trySend(Response.Error(e.message.toString()))
            }
            awaitClose()
        }
    }

    override suspend fun signInEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<User>> {
        return callbackFlow {
            trySend(Response.Loading)
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {authResult ->
                authResult.user?.let { firebaseUser ->
                    trySend(Response.Success(FirebaseMapper.firebaseUserToUser(firebaseUser)))
                }
            }
                .addOnFailureListener {
                    trySend(Response.Error(_message = it.message.toString()))
                }
            awaitClose()
        }
    }
}