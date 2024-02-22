package com.example.bootcampmealapp.presentation.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bootcampmealapp.data.response.ResponseStatus
import com.example.bootcampmealapp.domain.usecases.DeleteFoodFromBasketUseCase
import com.example.bootcampmealapp.domain.usecases.GetFoodBasketUseCase
import com.example.bootcampmealapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getFoodBasketUseCase: GetFoodBasketUseCase,
    private val deleteFoodFromBasketUseCase: DeleteFoodFromBasketUseCase
) : ViewModel() {
    private var _viewState = MutableStateFlow(OrderViewState())
    val viewState = _viewState.asStateFlow()

    fun getBasketFoods(username : String = Constants.USERNAME) {
        viewModelScope.launch {
            getFoodBasketUseCase.invoke(username).collect { response ->
                when(response.status) {
                    ResponseStatus.LOADING -> {}
                    ResponseStatus.SUCCESS -> {
                        response.data?.let { foodList ->
                            val groupedFoodList = foodList
                                .groupBy { it.foodName }
                                .map { (foodName, group) ->
                                    val totalQuantity =
                                        group.sumOf { it.foodPiece?.toIntOrNull() ?: 0 }
                                    val totalPrice =
                                        group.sumOf { it.foodPrice?.toIntOrNull() ?: 0 }
                                    group.first().copy(
                                        foodPiece = totalQuantity.toString(),
                                        foodPrice = totalPrice.toString()

                                    )
                                }
                            _viewState.update { viewState ->
                                viewState.copy(
                                    isLoading = false,
                                    totalPrice = response.data.sumOf { it.foodPrice?.toInt()
                                        ?.times(it.foodPiece!!.toInt()) ?: 0 },
                                    basketFoods = groupedFoodList
                                )
                            }
                        }
                    }
                    else -> {}
                }

            }
        }
    }


    fun deleteFoodFromBasket(foodBasketId: Int, userName: String = Constants.USERNAME) {
        viewModelScope.launch {
            deleteFoodFromBasketUseCase.invoke(foodBasketId, userName).collect { response ->
                when (response.status) {
                    ResponseStatus.LOADING -> {}
                    ResponseStatus.SUCCESS -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                isDeletedFromBasket = response.data!!,
                                isLoading = false
                            )
                        }
                        _viewState.update { viewState ->
                            val updatedList = viewState.basketFoods?.toMutableList()
                            updatedList?.removeAll { it.id == foodBasketId.toString() }
                            val totalPrice = updatedList?.sumOf {
                                it.foodPrice?.toInt()
                                    ?.times(it.foodPiece?.toInt()!!) ?: 0
                            }
                            viewState.copy(
                                basketFoods = updatedList,
                                isLoading = false,
                                totalPrice = totalPrice
                            )
                        }
//                        getFoodBasket(userName)

                    }

                    else -> {}
                }
            }
        }
    }

}