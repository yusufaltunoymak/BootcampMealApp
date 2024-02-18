package com.example.bootcampmealapp.data.repository

import com.example.bootcampmealapp.data.local.FoodDatabase
import com.example.bootcampmealapp.data.local.FoodEntity
import com.example.bootcampmealapp.data.remote.FoodApi
import com.example.bootcampmealapp.data.response.Response
import com.example.bootcampmealapp.domain.model.remote.FoodResponse
import com.example.bootcampmealapp.domain.model.remote.basket.BasketFoods
import com.example.bootcampmealapp.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(private val api : FoodApi,private val foodDatabase : FoodDatabase) : FoodRepository {
    override suspend fun getAllFoods(): Flow<Response<List<FoodResponse>>> {
        return flow {
            emit(Response.Loading)
            val response = try {
                api.getAllFoods()
            }
            catch (e: IOException) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            catch (e : HttpException) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            catch (e : Exception) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            emit(Response.Success(response.foods))
        }
    }

    override suspend fun addToBasket(
        username: String,
        foodPiece: Int,
        foodName: String,
        foodImageName: String,
        foodPrice: Int
    ): Flow<Response<Int>> {
        return flow {
            emit(Response.Loading)
            val response = try {
                api.addToBasket(username,foodPiece, foodName, foodImageName, foodPrice)
            }
            catch (e:IOException) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            catch (e : HttpException) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            catch (e : Exception) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            response.success?.let {
                emit(Response.Success(response.success.toInt()))
            }
        }
    }

    override suspend fun getFoodBasket(username: String): Flow<Response<List<BasketFoods>>> {
        return flow {
            emit(Response.Loading)
            val response = try {
                api.getBasketFoods(username).basketFoods
            }
            catch (e:IOException) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            catch (e : HttpException) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            catch (e : Exception) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            emit(Response.Success(response))
        }
    }

    override suspend fun deleteFoodFromBasket(
        basketFoodId: Int,
        username: String
    ): Flow<Response<Int>> {
        return flow {
            emit(Response.Loading)
            val response = try {
                api.deleteBasketFoods(basketFoodId, username)
            }
            catch (e:IOException) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            catch (e : HttpException) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            catch (e : Exception) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading foods"))
                return@flow
            }
            response.success?.let {
                emit(Response.Success(it))
            }
        }
    }

    override suspend fun getAllItemsStream(): Flow<List<FoodEntity>> = foodDatabase.foodDao().getAllFood()


    override suspend fun insertFoodToDatabase(foodEntity: FoodEntity) = foodDatabase.foodDao().insertFood(foodEntity)


    override suspend fun deleteFoodFromDatabase(foodId: Int) {
        val deleteFood = FoodEntity(foodId,"","","")
        foodDatabase.foodDao().delete(deleteFood)
    }

}