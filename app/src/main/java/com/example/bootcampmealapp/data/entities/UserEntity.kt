package com.example.bootcampmealapp.data.entities

import com.google.firebase.firestore.PropertyName

data class UserEntity(
    @PropertyName("id")
    val id : String? = null,
    @PropertyName("email")
    val email : String? = null
)
