package com.example.bootcampmealapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val foodName : String?,
    val foodPrice: String?,
    val foodImageUrl: String?
)