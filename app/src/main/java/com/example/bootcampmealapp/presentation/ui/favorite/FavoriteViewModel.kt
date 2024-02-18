package com.example.bootcampmealapp.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bootcampmealapp.data.local.FoodEntity
import com.example.bootcampmealapp.domain.usecases.DeleteFoodFromDatabaseUseCase
import com.example.bootcampmealapp.domain.usecases.GetAllFoodsFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFoodsFromDatabaseUseCase: GetAllFoodsFromDatabaseUseCase,
    private val deleteFoodFromDatabaseUseCase: DeleteFoodFromDatabaseUseCase
) : ViewModel() {
    private val _favoriteFoods : MutableStateFlow<List<FoodEntity>> = MutableStateFlow(emptyList())
    val favoriteFoods : StateFlow<List<FoodEntity>> = _favoriteFoods

    init {
        getAllFoods()
    }

    private fun getAllFoods() {
        viewModelScope.launch {
            getAllFoodsFromDatabaseUseCase.invoke().collect {
                _favoriteFoods.value = it
            }
        }
    }

    fun deleteFromFavorite(foodId : Int) {
        viewModelScope.launch {
            deleteFoodFromDatabaseUseCase.invoke(foodId)
        }
    }
}