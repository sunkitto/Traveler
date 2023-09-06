package com.sunkitto.traveler.feature.equipments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.domain.model.SortType
import com.sunkitto.traveler.domain.usecase.GetEquipmentsByCategoryUseCase
import com.sunkitto.traveler.domain.usecase.GetEquipmentsUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val CATEGORY_ID_ARG = "categoryId"
private const val CATEGORY_NAME_ARG = "categoryName"

class EquipmentsArgs(
    val categoryId: String,
    val categoryName: String,
) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(
            checkNotNull(savedStateHandle[CATEGORY_ID_ARG]) as String,
            checkNotNull(savedStateHandle[CATEGORY_NAME_ARG]) as String,
        )
}

@HiltViewModel
class EquipmentsViewModel @Inject constructor(
    private val getEquipmentsUseCase: GetEquipmentsUseCase,
    private val getEquipmentsByCategoryUseCase: GetEquipmentsByCategoryUseCase,
    private val uiErrorHandler: UiErrorHandler,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val equipmentsArgs = EquipmentsArgs(savedStateHandle)

    private val _state = MutableStateFlow(
        EquipmentsState(categoryName = equipmentsArgs.categoryName),
    )
    val state = _state.asStateFlow()

    fun onEvent(event: EquipmentsEvent) {
        when (event) {
            is EquipmentsEvent.LoadEquipments -> {
                loadEquipments(equipmentsArgs.categoryId)
            }
            is EquipmentsEvent.SortEquipment -> {
                sortEquipments(event.sortType)
            }
        }
    }

    private fun loadEquipments(categoryId: String) {
        getEquipmentsByCategoryUseCase(categoryId)
            .onEach { result ->
                when (result) {
                    is TravelerResult.Success -> {
                        _state.update { equipmentsState ->
                            equipmentsState.copy(
                                equipments = result.data,
                                errorMessage = null,
                                isLoading = false,
                            )
                        }
                    }
                    is TravelerResult.Error -> {
                        _state.update { equipmentsState ->
                            equipmentsState.copy(
                                equipments = emptyList(),
                                errorMessage = uiErrorHandler.handleError(result.exception),
                                isLoading = false,
                            )
                        }
                    }
                    is TravelerResult.Loading -> {
                        _state.update { equipmentsState ->
                            equipmentsState.copy(
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

    private fun sortEquipments(sortType: SortType) {
        getEquipmentsUseCase(sortType)
            .onEach { result ->
                when (result) {
                    is TravelerResult.Success -> {
                        _state.update { equipmentsState ->
                            equipmentsState.copy(
                                equipments = result.data,
                                errorMessage = null,
                                isLoading = false,
                            )
                        }
                    }
                    is TravelerResult.Error -> {
                        _state.update { equipmentsState ->
                            equipmentsState.copy(
                                equipments = emptyList(),
                                errorMessage = uiErrorHandler.handleError(result.exception),
                                isLoading = false,
                            )
                        }
                    }
                    is TravelerResult.Loading -> {
                        _state.update { equipmentsState ->
                            equipmentsState.copy(
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