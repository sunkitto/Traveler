package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.repository.EquipmentsRepositoryImpl
import com.sunkitto.traveler.domain.model.Equipment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class GetEquipmentsBySearchQueryUseCaseTest {

    private val equipmentsRepository = Mockito.mock<EquipmentsRepositoryImpl>()

    private lateinit var getEquipmentsUseCase: GetEquipmentsUseCase

    @Before
    fun setup() {
        getEquipmentsUseCase = GetEquipmentsUseCase(
            equipmentsRepository = equipmentsRepository,
        )
    }

    @Test
    fun returns_equipments_filtered_by_search_query() = runTest {
        val testEquipments = listOf(
            testEquipment("Test query"),
            testEquipment(" test qUerY#"),
            testEquipment("abc"),
        )

        `when`(equipmentsRepository.getEquipments())
            .thenReturn(
                flow {
                    emit(TravelerResult.Success(data = testEquipments))
                },
            )

        val getEquipmentsBySearchQueryUseCase = GetEquipmentsBySearchQueryUseCase(
            equipmentsRepository = equipmentsRepository,
        )
        val filteredEquipments = getEquipmentsBySearchQueryUseCase(searchQuery = "Test query")
            .first() as TravelerResult.Success

        assertEquals(
            2,
            filteredEquipments.data.size,
        )
    }
}

private fun testEquipment(name: String) =
    Equipment(
        id = "",
        name = name,
        image = "",
        description = "",
        cost = 0,
        categoryId = "",
    )