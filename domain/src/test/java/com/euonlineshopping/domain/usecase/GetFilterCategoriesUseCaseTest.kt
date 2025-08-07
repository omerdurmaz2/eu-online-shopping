package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.model.Category
import com.euonlineshopping.data.repository.ProductRepository
import com.euonlineshopping.domain.model.filter.FiltersUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetFilterCategoriesUseCaseTest {

    private lateinit var useCase: GetFilterCategoriesUseCase
    private val repository: ProductRepository = mockk()

    @BeforeEach
    fun setUp() {
        useCase = GetFilterCategoriesUseCase(repository)
    }

    @Test
    fun `invoke emits loading then content when categories are found and one is selected`() =
        runTest {
            val selectedFilter = "electronics"
            val mockCategories = listOf(
                Category(slug = "electronics", name = "Electronics"),
                Category(slug = "jewelery", name = "Jewelery")
            )
            coEvery { repository.getFilterCategories() } returns Result.success(mockCategories)

            val flow = useCase.invoke(selectedFilter)

            assertEquals(FiltersUiState.Loading, flow.first())
            val lastState = flow.last()
            assert(lastState is FiltersUiState.Content)

            val contentState = lastState as FiltersUiState.Content
            assertEquals(2, contentState.filters.size)
            assertEquals(true, contentState.filters[0].isSelected)
            assertEquals(false, contentState.filters[1].isSelected)
        }

    @Test
    fun `invoke emits loading then content when categories are found and no filter is selected`() =
        runTest {
            val mockCategories = listOf(
                Category(slug = "electronics", name = "Electronics"),
                Category(slug = "jewelery", name = "Jewelery")
            )
            coEvery { repository.getFilterCategories() } returns Result.success(mockCategories)

            val flow = useCase.invoke(null)

            assertEquals(FiltersUiState.Loading, flow.first())
            val lastState = flow.last()
            assert(lastState is FiltersUiState.Content)

            val contentState = lastState as FiltersUiState.Content
            assertEquals(2, contentState.filters.size)
            assertEquals(false, contentState.filters[0].isSelected)
            assertEquals(false, contentState.filters[1].isSelected)
        }

    @Test
    fun `invoke emits loading then empty when no categories are found`() = runTest {
        val mockCategories = emptyList<Category>()
        coEvery { repository.getFilterCategories() } returns Result.success(mockCategories)

        val flow = useCase.invoke(null)

        assertEquals(FiltersUiState.Loading, flow.first())
        assertEquals(FiltersUiState.Empty, flow.last())
    }

    @Test
    fun `invoke emits loading then error when repository call fails`() = runTest {
        coEvery { repository.getFilterCategories() } returns Result.failure(Throwable("Network error"))

        val flow = useCase.invoke(null)

        assertEquals(FiltersUiState.Loading, flow.first())
        val lastState = flow.last()
        assert(lastState is FiltersUiState.Error)
    }
}