package com.example.bootcampmealapp.domain.repository

import com.example.bootcampmealapp.data.response.Response
import com.example.bootcampmealapp.domain.model.remote.FoodResponse
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun getAllFoods() : Flow<Response<List<FoodResponse>>>

}