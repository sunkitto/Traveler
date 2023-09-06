package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.data.repository.EquipmentsRepositoryImpl
import com.sunkitto.traveler.data.repository.FavouritesRepositoryImpl
import com.sunkitto.traveler.domain.model.Equipment
import com.sunkitto.traveler.domain.model.Favourite
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetFavouritesUseCaseTest {

    private val favouritesRepository = mock<FavouritesRepositoryImpl>()
    private val equipmentsRepository = mock<EquipmentsRepositoryImpl>()

    private lateinit var getFavouritesUseCase: GetFavouritesUseCase

    @Before
    fun setup() {
        getFavouritesUseCase = GetFavouritesUseCase(
            favouritesRepository = favouritesRepository,
            equipmentsRepository = equipmentsRepository,
        )
    }

    @Test
    fun should_return_success_and_list_with_one_favourite_equipment() =
        runTest {
            `when`(favouritesRepository.getFavourites())
                .thenReturn(
                    flow {
                        emit(
                            TravelerResult.Success(data = listOf(testFavourite(equipmentId = "1"))),
                        )
                    },
                )

            `when`(equipmentsRepository.getEquipments())
                .thenReturn(
                    flow {
                        emit(
                            TravelerResult.Success(data = listOf(testEquipment(id = "1"))),
                        )
                    },
                )

            val result = getFavouritesUseCase.invoke().first()
            assertTrue(result is TravelerResult.Success)

            val successTravelerResult = (result as TravelerResult.Success).data
            assertTrue(successTravelerResult.size == 1)
        }

    @Test
    fun should_return_success_and_empty_list_of_favourite_equipment() =
        runTest {
            `when`(favouritesRepository.getFavourites())
                .thenReturn(
                    flow {
                        emit(
                            TravelerResult.Success(data = listOf(testFavourite(equipmentId = "2"))),
                        )
                    },
                )

            `when`(equipmentsRepository.getEquipments())
                .thenReturn(
                    flow {
                        emit(TravelerResult.Success(data = listOf(testEquipment(id = "1"))))
                    },
                )

            val result = getFavouritesUseCase.invoke().first()
            assertTrue(result is TravelerResult.Success)

            val successTravelerResult = (result as TravelerResult.Success).data
            assertTrue(successTravelerResult.isEmpty())
        }

    @Test
    fun should_return_result_error_when_exception_in_favorites_repository() =
        runTest {
            `when`(favouritesRepository.getFavourites())
                .thenReturn(
                    flow {
                        emit(TravelerResult.Error(exception = NoInternetConnectionException()))
                    },
                )

            `when`(equipmentsRepository.getEquipments())
                .thenReturn(
                    flow {
                        emit(TravelerResult.Success(data = listOf(testEquipment(""))))
                    },
                )

            val result = getFavouritesUseCase.invoke().first()
            assertTrue(result is TravelerResult.Error)
            assertTrue((result as TravelerResult.Error).exception is NoInternetConnectionException)
        }

    @Test
    fun should_return_result_error_when_exception_in_equipments_repository() =
        runTest {
            `when`(favouritesRepository.getFavourites())
                .thenReturn(
                    flow {
                        emit(TravelerResult.Success(data = listOf(testFavourite(""))))
                    },
                )

            `when`(equipmentsRepository.getEquipments())
                .thenReturn(
                    flow {
                        emit(TravelerResult.Error(exception = NoInternetConnectionException()))
                    },
                )

            val result = getFavouritesUseCase.invoke().first()
            assertTrue(result is TravelerResult.Error)
            assertTrue((result as TravelerResult.Error).exception is NoInternetConnectionException)
        }
}

private fun testEquipment(id: String) =
    Equipment(
        id = id,
        name = "",
        image = "",
        description = "",
        cost = 0,
        categoryId = "",
    )

private fun testFavourite(equipmentId: String) =
    Favourite(
        id = "",
        equipmentId = equipmentId,
    )