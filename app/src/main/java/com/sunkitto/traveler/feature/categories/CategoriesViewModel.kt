package com.sunkitto.traveler.feature.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.domain.usecase.GetCategoriesUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val uiErrorHandler: UiErrorHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    fun onEvent(event: CategoriesEvent) {
        when(event) {
            is CategoriesEvent.LoadCategories -> {
                getCategories()
            }
        }
    }

    private fun getCategories() {
        getCategoriesUseCase()
            .onEach { result ->
                when(result) {
                    is Result.Success -> {
                        _state.update { categoriesState ->
                            categoriesState.copy(
                                categories = result.data,
                                errorMessage = null,
                                isLoading = false,
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update { categoriesState ->
                            categoriesState.copy(
                                categories = null,
                                errorMessage = uiErrorHandler.handleError(result.exception),
                                isLoading = false,
                            )
                        }
                    }
                    is Result.Loading -> {
                        _state.update { categoriesState ->
                            categoriesState.copy(
                                categories = null,
                                errorMessage = null,
                                isLoading = true,
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}