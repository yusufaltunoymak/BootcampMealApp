package com.example.bootcampmealapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bootcampmealapp.data.response.ResponseStatus
import com.example.bootcampmealapp.domain.model.remote.FoodResponse
import com.example.bootcampmealapp.domain.usecases.GetAllFoodsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodsUseCase
) : ViewModel() {
    private var _viewState = MutableStateFlow(HomeViewState())
    val viewState = _viewState.asStateFlow()
    private val cacheList = mutableListOf<FoodResponse>()



    init {
        getAllFoods()
    }

    private fun getAllFoods() {
        viewModelScope.launch {
            getAllFoodsUseCase.invoke().collect { response ->
                when(response.status) {
                    ResponseStatus.LOADING -> {

                    }
                    ResponseStatus.SUCCESS -> {
                        cacheList.addAll(response.data ?: emptyList())
                        _viewState.update {viewState ->
                            viewState.copy(
                                isLoading = false,
                                foods = cacheList
                            )
                        }
                    }
                    else -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                errorMessage = response.message
                            )
                        }
                    }
                }
            }
        }
    }
    fun search(searchQuery: String) {
        viewModelScope.launch {
            _viewState.update { viewState ->
                viewState.copy(
                    foods = if (searchQuery.isEmpty()) {
                        cacheList
                    } else {
                        cacheList.filter {
                            it.foodName!!.lowercase().contains(searchQuery.lowercase())
                        }
                    }
                )
            }
        }
    }
}