package com.example.bootcampmealapp.domain.model.remote.basket

import com.google.gson.annotations.SerializedName

data class GetFoodBasketResponse(
    @SerializedName("sepet_yemekler")
    val basketFoods: List<BasketFoods>,
    @SerializedName("success")
    val success: Int?
)