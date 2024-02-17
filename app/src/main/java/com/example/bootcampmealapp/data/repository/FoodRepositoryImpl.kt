package com.example.bootcampmealapp.data.repository

import com.example.bootcampmealapp.data.remote.FoodApi
import com.example.bootcampmealapp.data.response.Response
import com.example.bootcampmealapp.domain.model.remote.FoodResponse
import com.example.bootcampmealapp.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(private val api : FoodApi) : FoodRepository {
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
}