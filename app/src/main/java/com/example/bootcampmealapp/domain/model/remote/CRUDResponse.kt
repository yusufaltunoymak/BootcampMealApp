package com.example.bootcampmealapp.domain.model.remote

import com.google.gson.annotations.SerializedName

data class CRUDResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Int?
)