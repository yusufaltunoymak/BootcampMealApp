package com.example.bootcampmealapp.presentation.ui.home

import com.example.bootcampmealapp.domain.model.remote.FoodResponse

data class HomeViewState(
    val isLoading: Boolean? = null,
    val foods: List<FoodResponse>? = null,
    val errorMessage: String? = null
)