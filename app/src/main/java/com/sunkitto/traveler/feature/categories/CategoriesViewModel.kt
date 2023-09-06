package com.sunkitto.traveler.feature.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.usecase.GetCategoriesUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val uiErrorHandler: UiErrorHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    fun onEvent(event: CategoriesEvent) {
        when (event) {
            is CategoriesEvent.LoadCategories -> {
                getCategories()
            }
        }
    }

    private fun getCategories() {
        getCategoriesUseCase()
            .onEach { result ->
                when (result) {
                    is TravelerResult.Success -> {
                        _state.update { categoriesState ->
                            categoriesState.copy(
                                categories = result.data,
                                errorMessage = null,
                                isLoading = false,
                            )
                        }
                    }
                    is TravelerResult.Error -> {
                        _state.update { categoriesState ->
                            categoriesState.copy(
                                categories = emptyList(),
                                errorMessage = uiErrorHandler.handleError(result.exception),
                                isLoading = false,
                            )
                        }
                    }
                    is TravelerResult.Loading -> {
                        _state.update { categoriesState ->
                            categoriesState.copy(
                                categories = emptyList(),
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