package com.example.bootcampmealapp.domain.usecases

import com.example.bootcampmealapp.data.local.FoodEntity
import com.example.bootcampmealapp.data.repository.FoodRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFoodsFromDatabaseUseCase @Inject constructor(private val foodRepositoryImpl: FoodRepositoryImpl) {
    suspend operator fun invoke() : Flow<List<FoodEntity>> = foodRepositoryImpl.getAllItemsStream()
}