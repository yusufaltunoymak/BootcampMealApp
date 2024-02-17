package com.example.bootcampmealapp.util

object FirebasePaths {
    const val USERS = "Users"

    fun getUsersReference(): String {
        return USERS
    }
}