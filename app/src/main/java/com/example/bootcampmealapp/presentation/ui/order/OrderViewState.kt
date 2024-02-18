package com.example.bootcampmealapp.presentation.ui.order

import com.example.bootcampmealapp.domain.model.remote.basket.BasketFoods

data class OrderViewState(
    val isLoading: Boolean? = null,
    val basketFoods : List<BasketFoods>? = null,
    val errorMessage: String? = null
)