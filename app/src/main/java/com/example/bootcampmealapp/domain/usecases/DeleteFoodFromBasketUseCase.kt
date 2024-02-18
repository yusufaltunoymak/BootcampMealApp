package com.example.bootcampmealapp.domain.usecases

import com.example.bootcampmealapp.data.repository.FoodRepositoryImpl
import com.example.bootcampmealapp.data.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFoodFromBasketUseCase @Inject constructor(private val repositoryImpl: FoodRepositoryImpl) {
    suspend operator fun invoke(foodBasketId: Int, username: String): Flow<Response<Int>> =
        repositoryImpl.deleteFoodFromBasket(foodBasketId, username)
}