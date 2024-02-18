package com.example.bootcampmealapp.domain.usecases

import com.example.bootcampmealapp.data.repository.FoodRepositoryImpl
import javax.inject.Inject

class DeleteFoodFromDatabaseUseCase @Inject constructor(private val foodRepositoryImpl: FoodRepositoryImpl) {
    suspend operator fun invoke(foodId : Int) = foodRepositoryImpl.deleteFoodFromDatabase(foodId)
}