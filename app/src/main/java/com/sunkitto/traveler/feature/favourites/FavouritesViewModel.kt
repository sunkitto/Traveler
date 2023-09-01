package com.sunkitto.traveler.feature.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.domain.usecase.GetFavouritesUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val uiErrorHandler: UiErrorHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(FavouritesState())
    val state = _state.asStateFlow()

    fun onEvent(event: FavouritesEvent) {
        when(event) {
            is FavouritesEvent.LoadFavourites -> {
                getFavourites()
            }
        }
    }

    private fun getFavourites() {
        getFavouritesUseCase()
            .onEach { result ->
                when(result) {
                    is Result.Success -> {
                        _state.update { favouritesState ->
                            favouritesState.copy(
                                equipments = result.data,
                                errorMessage = null,
                                isLoading = false,
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update { favouritesState ->
                            val errorMessage = uiErrorHandler.handleError(result.exception)
                            favouritesState.copy(
                                equipments = null,
                                errorMessage = errorMessage,
                                isLoading = false,
                            )
                        }
                    }
                    is Result.Loading -> {
                        _state.update { favouritesState ->
                            favouritesState.copy(
                                equipments = null,
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