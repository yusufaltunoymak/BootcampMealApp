package com.example.bootcampmealapp.data.remote

import com.example.bootcampmealapp.domain.model.remote.GetAllFoodsResponse
import retrofit2.http.GET

interface FoodApi {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getAllFoods() : GetAllFoodsResponse
}