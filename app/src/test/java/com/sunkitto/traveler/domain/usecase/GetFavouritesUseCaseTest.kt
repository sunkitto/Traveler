package com.sunkitto.traveler.domain.usecase

import com.sunkitto.traveler.common.Result
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.data.repository.EquipmentsRepositoryImpl
import com.sunkitto.traveler.data.repository.FavouritesRepositoryImpl
import com.sunkitto.traveler.model.Equipment
import com.sunkitto.traveler.model.Favourite
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
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
            equipmentsRepository = equipmentsRepository
        )
    }

    @Test
    fun should_return_success_and_list_with_one_favourite_equipment() =
        runTest {
            `when`(favouritesRepository.getFavouriteEquipment())
                .thenReturn(
                    flow {
                        emit(Result.Success(data = listOf(Favourite(equipmentId = 1))))
                    }
                )

            `when`(equipmentsRepository.getEquipments(anyInt()))
                .thenReturn(
                    flow {
                        emit(Result.Success(data = listOf(Equipment(id = 1))))
                    }
                )

            val result = getFavouritesUseCase.invoke().first()
            assertTrue(result is Result.Success)

            val successResult = (result as Result.Success).data
            assertTrue(successResult.size == 1)
        }

    @Test
    fun should_return_success_and_empty_list_of_favourite_equipment() =
        runTest {
            `when`(favouritesRepository.getFavouriteEquipment())
                .thenReturn(
                    flow {
                        emit(Result.Success(data = listOf(Favourite(equipmentId = 2))))
                    }
                )

            `when`(equipmentsRepository.getEquipments(anyInt()))
                .thenReturn(
                    flow {
                        emit(Result.Success(data = listOf(Equipment(id = 1))))
                    }
                )

            val result = getFavouritesUseCase.invoke().first()
            assertTrue(result is Result.Success)

            val successResult = (result as Result.Success).data
            assertTrue(successResult.isEmpty())
        }

    @Test
    fun should_return_result_error_when_exception_in_favorites_repository() =
        runTest {
            `when`(favouritesRepository.getFavouriteEquipment())
                .thenReturn(
                    flow {
                        emit(Result.Error(exception = NoInternetConnectionException()))
                    }
                )

            `when`(equipmentsRepository.getEquipments(anyInt()))
                .thenReturn(
                    flow {
                        emit(Result.Success(data = listOf(Equipment())))
                    }
                )

            val result = getFavouritesUseCase.invoke().first()
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is NoInternetConnectionException)
        }

    @Test
    fun should_return_result_error_when_exception_in_equipments_repository() =
        runTest {
            `when`(favouritesRepository.getFavouriteEquipment())
                .thenReturn(
                    flow {
                        emit(Result.Success(data = listOf(Favourite())))
                    }
                )

            `when`(equipmentsRepository.getEquipments(anyInt()))
                .thenReturn(
                    flow {
                        emit(Result.Error(exception = NoInternetConnectionException()))
                    }
                )

            val result = getFavouritesUseCase.invoke().first()
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is NoInternetConnectionException)
        }
}