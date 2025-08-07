import com.euonlineshopping.data.model.Product
import com.euonlineshopping.data.model.ProductsResponse
import com.euonlineshopping.data.repository.ProductRepository
import com.euonlineshopping.domain.model.home.ProductsUiState
import com.euonlineshopping.domain.usecase.GetCategoryProductsUseCase
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
class GetCategoryProductsUseCaseTest {

    private lateinit var useCase: GetCategoryProductsUseCase
    private val repository: ProductRepository = mockk()

    @BeforeEach
    fun setUp() {
        useCase = GetCategoryProductsUseCase(repository)
    }

    @Test
    fun `invoke emits loading then content when products are found`() = runTest {
        // Arrange
        val category = "electronics"
        val mockProduct = Product(
            id = 1,
            title = "Laptop",
            description = "desc",
            thumbnail = "image.jpg",
            isFavorite = false,
            price = 1000.0,
            discountPercentage = 10.0
        )
        val mockResponse = ProductsResponse(listOf(mockProduct), 1,0,0)
        coEvery { repository.getCategoryProducts(category, null, null) } returns Result.success(
            mockResponse
        )

        // Act
        val flow = useCase.invoke(category, null, null)

        // Assert
        assertEquals(ProductsUiState.Loading, flow.first())
        val lastState = flow.last()
        assert(lastState is ProductsUiState.Content)
        val contentState = lastState as ProductsUiState.Content
        assertEquals(1, contentState.products.size)
        assertEquals(1, contentState.productCount)
        assertEquals(mockProduct.title, contentState.products[0].title)
        assertEquals(mockProduct.price, contentState.products[0].price)
        assertEquals(mockProduct.discountPercentage, contentState.products[0].discountPercentage)
    }

    @Test
    fun `invoke emits loading then empty when no products are found`() = runTest {
        // Arrange
        val category = "clothing"
        val mockResponse = ProductsResponse(emptyList(), 0, 0, 0)
        coEvery { repository.getCategoryProducts(category, null, null) } returns Result.success(
            mockResponse
        )

        // Act
        val flow = useCase.invoke(category, null, null)

        // Assert
        assertEquals(ProductsUiState.Loading, flow.first())
        assertEquals(ProductsUiState.Empty, flow.last())
    }

    @Test
    fun `invoke emits loading then error when repository call fails`() = runTest {
        // Arrange
        val category = "error_category"
        coEvery { repository.getCategoryProducts(category, null, null) } returns Result.failure(
            Throwable("Network error")
        )

        // Act
        val flow = useCase.invoke(category, null, null)

        // Assert
        assertEquals(ProductsUiState.Loading, flow.first())
        val lastState = flow.last()
        assert(lastState is ProductsUiState.Error)
    }
}