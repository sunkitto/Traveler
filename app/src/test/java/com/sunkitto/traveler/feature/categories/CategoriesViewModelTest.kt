package com.sunkitto.traveler.feature.categories

import com.sunkitto.traveler.common.TravelerResult
import com.sunkitto.traveler.data.exception.NoInternetConnectionException
import com.sunkitto.traveler.domain.model.Category
import com.sunkitto.traveler.domain.usecase.GetCategoriesUseCase
import com.sunkitto.traveler.feature.UiErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesViewModelTest {

    private val getCategoriesUseCase = mock<GetCategoriesUseCase>()
    private val uiErrorHandler = mock<UiErrorHandler>()

    private lateinit var categoriesViewModel: CategoriesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        categoriesViewModel = CategoriesViewModel(
            getCategoriesUseCase = getCategoriesUseCase,
            uiErrorHandler = uiErrorHandler,
        )
    }

    @Test
    fun categories_state_updated_and_returns_success_when_load_categories_event_submitted() {
        `when`(getCategoriesUseCase())
            .thenReturn(
                flow {
                    emit(TravelerResult.Success(data = listOf(testCategory())))
                },
            )

        categoriesViewModel.onEvent(CategoriesEvent.LoadCategories)
        assertEquals(categoriesViewModel.state.value.categories, listOf(testCategory()))
    }

    @Test
    fun categories_state_updated_and_returns_error_when_load_categories_event_submitted() {
        `when`(uiErrorHandler.handleError(ArgumentMatchers.any()))
            .thenReturn(
                "No Internet Connection",
            )

        `when`(getCategoriesUseCase())
            .thenReturn(
                flow {
                    emit(TravelerResult.Error(exception = NoInternetConnectionException()))
                },
            )

        categoriesViewModel.onEvent(CategoriesEvent.LoadCategories)
        assertEquals(
            "No Internet Connection",
            categoriesViewModel.state.value.errorMessage,
        )
    }

    @Test
    fun categories_state_updated_and_returns_load_when_load_categories_event_submitted() {
        `when`(getCategoriesUseCase())
            .thenReturn(
                flow {
                    emit(TravelerResult.Loading)
                },
            )

        categoriesViewModel.onEvent(CategoriesEvent.LoadCategories)
        assertTrue(categoriesViewModel.state.value.isLoading)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}

private fun testCategory() =
    Category(
        id = "",
        name = "",
        image = "",
    )