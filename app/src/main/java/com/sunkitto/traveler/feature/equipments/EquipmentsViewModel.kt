package com.sunkitto.traveler.feature.equipments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.domain.usecase.GetEquipmentsUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EquipmentsViewModel @Inject constructor(
    private val getEquipmentsUseCase: GetEquipmentsUseCase,
    private val uiErrorHandler: UiErrorHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(EquipmentsState())
    val state = _state.asStateFlow()

    fun onEvent(event: EquipmentsEvent) {
        when(event) {
            is EquipmentsEvent.LoadEquipments -> {
                loadEquipments(event.categoryId)
            }
            is EquipmentsEvent.SortEquipment -> {

            }
        }
    }

    private fun loadEquipments(categoryId: Int) {
        getEquipmentsUseCase(categoryId)
            .onEach { result ->
                when(result) {
                    is Result.Success -> {
                        _state.update { equipmentsState ->
                            equipmentsState.copy(
                                equipments = result.data,
                                errorMessage = null,
                                isLoading = false,
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update { equipmentsState ->
                            equipmentsState.copy(
                                equipments = null,
                                errorMessage = uiErrorHandler.handleError(result.exception),
                                isLoading = false,
                            )
                        }
                    }
                    is Result.Loading -> {
                        _state.update { equipmentsState ->
                            equipmentsState.copy(
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