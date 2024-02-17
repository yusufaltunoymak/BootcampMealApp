package com.example.bootcampmealapp.util

import com.example.bootcampmealapp.data.entities.UserEntity
import com.example.bootcampmealapp.domain.model.remote.User
import com.google.firebase.auth.FirebaseUser

object FirebaseMapper {
    fun userToUserEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            email = user.email
        )
    }

    fun userFromUserEntity(userEntity: UserEntity): User {
        return User(
            id = userEntity.id,
            email = userEntity.email
        )
    }


    fun firebaseUserToUser(firebaseUser: FirebaseUser): User {
        return User(
            id = firebaseUser.uid,
            email = firebaseUser.email
        )
    }
}