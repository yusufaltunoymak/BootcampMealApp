package com.example.bootcampmealapp.domain.usecases

import com.example.bootcampmealapp.data.repository.FoodRepositoryImpl
import javax.inject.Inject

class CheckFavoriteUseCase @Inject constructor(private val repositoryImpl: FoodRepositoryImpl) {
    suspend operator fun invoke(foodId : Int) : Int = repositoryImpl.checkIsFavoriteFood(foodId)
}