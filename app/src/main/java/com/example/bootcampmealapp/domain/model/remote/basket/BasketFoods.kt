package com.example.bootcampmealapp.domain.model.remote.basket

import com.google.gson.annotations.SerializedName

data class BasketFoods(
    @SerializedName("kullanici_adi")
    val username: String?,
    @SerializedName("sepet_yemek_id")
    val id: String?,
    @SerializedName("yemek_adi")
    val foodName: String?,
    @SerializedName("yemek_fiyat")
    val foodPrice: String?,
    @SerializedName("yemek_resim_adi")
    val foodImageUrl: String?,
    @SerializedName("yemek_siparis_adet")
    val foodPiece: String?
)