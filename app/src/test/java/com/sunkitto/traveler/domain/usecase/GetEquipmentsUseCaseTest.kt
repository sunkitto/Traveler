package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.repository.EquipmentsRepositoryImpl
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.model.SortType
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
            equipmentsRepository = equipmentsRepository,
        )
    }

    @Test
    fun returns_sorted_by_highest_price_equipments() = runTest {
        val testEquipments = listOf(
            testEquipment(10),
            testEquipment(30),
            testEquipment(20),
        )

        `when`(equipmentsRepository.getEquipments())
            .thenReturn(
                flow {
                    emit(TravelerResult.Success(data = testEquipments))
                },
            )

        val getEquipmentsUseCase = GetEquipmentsUseCase(
            equipmentsRepository = equipmentsRepository,
        )
        val sortedEquipments = getEquipmentsUseCase(sortType = SortType.HIGHEST_PRICE)
            .first() as TravelerResult.Success

        assertEquals(
            testEquipments.sortedByDescending { equipment ->
                equipment.cost
            },
            sortedEquipments.data,
        )
    }
}

private fun testEquipment(cost: Int) =
    Equipment(
        id = "",
        name = "",
        image = "",
        description = "",
        cost = cost,
        categoryId = "",
    )