package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.data.repository.EquipmentsRepositoryImpl
import com.sunkitto.traveler.model.Equipment
import com.sunkitto.traveler.model.SortType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetEquipmentsUseCaseTest {

    private val equipmentsRepository = mock<EquipmentsRepositoryImpl>()

    private lateinit var getEquipmentsUseCase: GetEquipmentsUseCase

    @Before
    fun setup() {
        getEquipmentsUseCase = GetEquipmentsUseCase(
            equipmentsRepository = equipmentsRepository
        )
    }

    @Test
    fun returns_sorted_by_highest_price_equipments() =
        runTest {
            val equipments = listOf(
                Equipment(id = 1, cost = 10),
                Equipment(id = 2, cost = 30),
                Equipment(id = 3, cost = 20),
            )

            `when`(equipmentsRepository.getEquipments())
                .thenReturn(
                    flow {
                        emit(Result.Success(data = equipments))
                    }
                )

            val getEquipmentsUseCase = GetEquipmentsUseCase(equipmentsRepository = equipmentsRepository)
            val sortedEquipments = getEquipmentsUseCase(sortType = SortType.HIGHEST_PRICE)
                .first() as Result.Success

            assertEquals(
                equipments.sortedByDescending { it.cost },
                sortedEquipments.data
            )
        }
}