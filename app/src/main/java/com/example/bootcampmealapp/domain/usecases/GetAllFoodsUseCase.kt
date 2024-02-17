package com.example.bootcampmealapp.domain.usecases

import com.example.bootcampmealapp.data.repository.FoodRepositoryImpl
import com.example.bootcampmealapp.data.response.Response
import com.example.bootcampmealapp.domain.model.remote.FoodResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFoodsUseCase @Inject constructor(private val repo : FoodRepositoryImpl){
    suspend operator fun invoke(): Flow<Response<List<FoodResponse>>> {
        return repo.getAllFoods()
    }
}