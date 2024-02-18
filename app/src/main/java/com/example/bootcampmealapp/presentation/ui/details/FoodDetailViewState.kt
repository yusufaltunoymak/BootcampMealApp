package com.example.bootcampmealapp.presentation.ui.details

import com.example.bootcampmealapp.domain.model.remote.FoodResponse
import com.example.bootcampmealapp.domain.model.remote.basket.BasketFoods

data class FoodDetailViewState(
    val isLoading: Boolean? = null,
    var piece : Int = 1,
    var price : Int = 0,
    val isAddedBasket : Int? = null,
    val basketFoods : List<BasketFoods>? = null,
    val isCompleted : Boolean = false,
    val foods : FoodResponse? = null,
    val errorMessage: String? = null

)