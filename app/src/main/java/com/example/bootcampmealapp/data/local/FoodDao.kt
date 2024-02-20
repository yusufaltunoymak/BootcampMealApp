package com.example.bootcampmealapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(foodEntity: FoodEntity)

    @Query("SELECT * FROM food_table")
    fun getAllFood() : Flow<List<FoodEntity>>

    @Delete
    suspend fun delete(foodEntity: FoodEntity)


    @Query("SELECT COUNT(*) FROM food_table WHERE id = :foodId")
    suspend fun isFoodInFavorites(foodId: Int): Int
}