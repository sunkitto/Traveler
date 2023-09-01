package com.sunkitto.traveler.feature.search

import androidx.lifecycle.ViewModel
import com.sunkitto.traveler.domain.usecase.GetEquipmentsUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getEquipmentsUseCase: GetEquipmentsUseCase,
    private val uiErrorHandler: UiErrorHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.SearchEquipments -> {
                searchEquipments()
            }
        }
    }

    private fun searchEquipments() {

    }
}