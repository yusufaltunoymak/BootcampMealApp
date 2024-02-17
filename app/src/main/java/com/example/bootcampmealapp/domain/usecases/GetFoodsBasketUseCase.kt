package com.example.bootcampmealapp.domain.usecases

import com.example.bootcampmealapp.data.repository.FoodRepositoryImpl
import com.example.bootcampmealapp.data.response.Response
import com.example.bootcampmealapp.domain.model.remote.basket.BasketFoods
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFoodBasketUseCase @Inject constructor(private val repositoryImpl: FoodRepositoryImpl) {
    suspend operator fun invoke(userName : String) : Flow<Response<List<BasketFoods>>> = repositoryImpl.getFoodBasket(userName)
}