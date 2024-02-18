package com.example.bootcampmealapp.presentation.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bootcampmealapp.data.response.ResponseStatus
import com.example.bootcampmealapp.domain.model.remote.FoodResponse
import com.example.bootcampmealapp.domain.model.remote.basket.BasketFoods
import com.example.bootcampmealapp.domain.usecases.AddToBasketUseCase
import com.example.bootcampmealapp.domain.usecases.GetFoodBasketUseCase
import com.example.bootcampmealapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val addToBasketUseCase: AddToBasketUseCase,
    private val getFoodBasketUseCase: GetFoodBasketUseCase

) : ViewModel() {
    private var _viewState = MutableStateFlow(FoodDetailViewState())
    val viewState = _viewState.asStateFlow()
    private var basketList: MutableLiveData<List<BasketFoods>> = MutableLiveData()


    fun initMeal(food: FoodResponse) {
        _viewState.update {
            val updateFoodPrice = food.foodPrice.toInt() * viewState.value.piece
            it.copy(
                foods = food,
                price = updateFoodPrice
            )
        }
    }

    fun incrementQuantity() {
        _viewState.update { viewState ->
            val piece = viewState.piece
            val food =viewState.foods
            val newPiece = piece.inc()
            val newPrice = food!!.foodPrice.toInt() * newPiece
            viewState.copy(
                piece = newPiece,
                price = newPrice
            )
        }
    }

    fun decreaseQuantity() {
        _viewState.update { viewState ->
            val food = viewState.foods
            val currentPiece = viewState.piece
            if(currentPiece > 1) {
                val newPiece = currentPiece.dec()
                val newPrice = food!!.foodPrice.toInt() * newPiece
                return@update viewState.copy(
                    piece = newPiece,
                    price = newPrice
                )
            }
            return@update viewState
        }
    }
    private fun addToBasket(
        username: String = Constants.USERNAME,
        quantity: Int,
        foodName: String,
        foodImageName: String,
        foodPrice: Int
    ) {
        viewModelScope.launch {
            addToBasketUseCase.invoke(username, quantity, foodName, foodImageName, foodPrice)
                .collect { response ->
                    when (response.status) {
                        ResponseStatus.LOADING -> {

                        }

                        ResponseStatus.SUCCESS -> {
                            _viewState.update { viewState ->
                                viewState.copy(
                                    isAddedBasket = response.data
                                )
                            }
                            getFoodForBasket()
                        }

                        else -> {

                        }
                    }
                }
        }
    }

    private fun getFoodForBasket(username : String = Constants.USERNAME) {
        viewModelScope.launch {
            getFoodBasketUseCase.invoke(username).collect { response ->
                when(response.status) {
                    ResponseStatus.LOADING -> {}
                    ResponseStatus.SUCCESS -> {
                        response.data?.let {
                            basketList.value = it
                            _viewState.update { viewState ->
                                viewState.copy(
                                    basketFoods = basketList.value,
                                )
                            }
                        }
                    }
                    else -> {}
                }

            }
        }
    }

        private fun isAlreadyBasket(foodName: String) : Boolean {
        val basketItems = basketList.value.orEmpty()
        return basketItems.any { it.foodName == foodName }

    }

    fun addToCart() {
        val meal = viewState.value.foods ?: return
        viewModelScope.launch {
            getFoodForBasket()
            delay(1000)
            if (isAlreadyBasket(meal.foodName)) {
                Log.d("aynıürün","aynı")

                _viewState.update {viewState ->
                    viewState.copy(
                        isCompleted = true
                    )
                }
            }
            else {
                Log.d("buraya","buraya")
                addToBasket(
                    foodName = meal.foodName,
                    foodImageName = meal.foodImageUrl,
                    foodPrice = meal.foodPrice.toInt(),
                    quantity = _viewState.value.piece
                )
            }
        }
    }
}