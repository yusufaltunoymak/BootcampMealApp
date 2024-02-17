package com.example.bootcampmealapp.domain.usecases

import com.example.bootcampmealapp.data.repository.FoodRepositoryImpl
import com.example.bootcampmealapp.data.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToBasketUseCase @Inject constructor(private val repositoryImpl: FoodRepositoryImpl) {
    suspend operator fun invoke(username : String,foodPiece : Int,foodName : String,foodImageName : String,foodPrice : Int): Flow<Response<Int>> =
        repositoryImpl.addToBasket(username,foodPiece, foodName, foodImageName, foodPrice)
}