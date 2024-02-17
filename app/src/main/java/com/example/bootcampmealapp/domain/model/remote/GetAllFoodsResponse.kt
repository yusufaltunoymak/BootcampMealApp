package com.example.bootcampmealapp.domain.model.remote

import com.google.gson.annotations.SerializedName

data class GetAllFoodsResponse(
    @SerializedName("success")
    val success: Int?,
    @SerializedName("yemekler")
    val foods: List<FoodResponse>
)