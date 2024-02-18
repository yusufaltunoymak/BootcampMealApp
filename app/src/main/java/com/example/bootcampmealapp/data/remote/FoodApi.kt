package com.example.bootcampmealapp.data.remote

import com.example.bootcampmealapp.domain.model.remote.CRUDResponse
import com.example.bootcampmealapp.domain.model.remote.GetAllFoodsResponse
import com.example.bootcampmealapp.domain.model.remote.basket.GetFoodBasketResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodApi {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getAllFoods() : GetAllFoodsResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToBasket(
        @Field("kullanici_adi") username : String,
        @Field("yemek_siparis_adet") foodPiece : Int,
        @Field("yemek_adi") foodName : String,
        @Field("yemek_resim_adi") foodImageName: String,
        @Field("yemek_fiyat") foodPrice : Int
    ) : CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun getBasketFoods(@Field("kullanici_adi") username : String) : GetFoodBasketResponse

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteBasketFoods(
        @Field("sepet_yemek_id") basketFoodId : Int,
        @Field("kullanici_adi") username : String
    ) : CRUDResponse
}