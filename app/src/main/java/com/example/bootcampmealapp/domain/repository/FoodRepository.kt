package com.example.bootcampmealapp.domain.repository

import com.example.bootcampmealapp.data.response.Response
import com.example.bootcampmealapp.domain.model.remote.FoodResponse
import com.example.bootcampmealapp.domain.model.remote.basket.BasketFoods
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun getAllFoods() : Flow<Response<List<FoodResponse>>>
    suspend fun addToBasket(
        username: String,
        foodPiece: Int,
        foodName: String,
        foodImageName: String,
        foodPrice: Int
    ): Flow<Response<Int>>

    suspend fun getFoodBasket(username : String) : Flow<Response<List<BasketFoods>>>

}