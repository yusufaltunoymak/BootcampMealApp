package com.example.bootcampmealapp.domain.model.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FoodResponse(
    @SerializedName("yemek_adi")
    val foodName: String,
    @SerializedName("yemek_fiyat")
    val foodPrice: String,
    @SerializedName("yemek_resim_adi")
    val foodImageUrl: String,
    @SerializedName("yemek_id")
    val foodId: String,
    var isFavorited : Boolean = false
) : Serializable