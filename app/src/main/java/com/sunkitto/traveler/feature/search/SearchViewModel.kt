package com.sunkitto.traveler.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.usecase.GetEquipmentsBySearchQueryUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getEquipmentsBySearchQueryUseCase: GetEquipmentsBySearchQueryUseCase,
    private val uiErrorHandler: UiErrorHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchEquipments -> {

                _state.update { searchState ->
                    searchState.copy(
                        searchQuery = event.query
                    )
                }

                if(event.query.trim().isNotBlank()) {
                    searchEquipments(event.query)
                } else {
                    _state.update { searchState ->
                        searchState.copy(
                            equipments = emptyList()
                        )
                    }
                }
            }
            is SearchEvent.ClearSearch -> {
                _state.update { searchState ->
                    searchState.copy(
                        searchQuery = ""
                    )
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun searchEquipments(query: String) {
        getEquipmentsBySearchQueryUseCase(query.trim())
            .onStart {
                _state.update { searchState ->
                    searchState.copy(
                        isLoading = true,
                    )
                }
            }
            .debounce(1500)
            .onEach { result ->
                when (result) {
                    is TravelerResult.Success -> {
                        _state.update { searchState ->
                            searchState.copy(
                                equipments = result.data,
                                errorMessage = null,
                                isLoading = false,
                            )
                        }
                    }
                    is TravelerResult.Error -> {
                        _state.update { searchState ->
                            val errorMessage = uiErrorHandler.handleError(result.exception)
                            searchState.copy(
                                equipments = emptyList(),
                                errorMessage = errorMessage,
                                isLoading = false,
                            )
                        }
                    }
                    is TravelerResult.Loading -> {
                        _state.update { searchState ->
                            searchState.copy(
                                equipments = emptyList(),
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