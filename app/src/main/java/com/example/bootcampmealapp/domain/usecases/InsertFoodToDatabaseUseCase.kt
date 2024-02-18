package com.example.bootcampmealapp.domain.usecases

import com.example.bootcampmealapp.data.local.FoodEntity
import com.example.bootcampmealapp.data.repository.FoodRepositoryImpl
import javax.inject.Inject

class InsertFoodToDatabaseUseCase @Inject constructor(private val foodRepositoryImpl: FoodRepositoryImpl) {
    suspend operator fun invoke(foodEntity: FoodEntity) = foodRepositoryImpl.insertFoodToDatabase(foodEntity)
}