package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.datastore.CartManager
import com.euonlineshopping.data.model.Product
import com.euonlineshopping.domain.model.cart.CartUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetCartProductsUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var getCartProductsUseCase: GetCartProductsUseCase
    private val cartManager: CartManager = mockk()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCartProductsUseCase = GetCartProductsUseCase(cartManager)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `invoke emits loading then content when cart is not empty`() = runTest {
        val product1 = Product(
            1,
            "Product 1",
            "Description",
            "image.jpg",
            false,
            price = 10.0,
            discountPercentage = 4.0
        )
        val product2 =  Product(
            2,
            "Product 2",
            "Description",
            "image.jpg",
            false,
            price = 15.0,
            discountPercentage = 4.0
        )

        val mockProductList = listOf(product1, product1, product2)

        coEvery { cartManager.getCartProducts() } returns flow { emit(mockProductList) }

        val resultFlow = getCartProductsUseCase.invoke()

        assertEquals(CartUiState.Loading, resultFlow.first())

        val lastState = resultFlow.last()
        assert(lastState is CartUiState.Content)

        val contentState = lastState as CartUiState.Content
        assertEquals("30,00 ₺", contentState.price)
        assertEquals("0,00 ₺", contentState.discount)
        assertEquals("30,00 ₺", contentState.total)
        assertEquals(2, contentState.products.size)
        assertEquals(2, contentState.products[0].count)
        assertEquals(1, contentState.products[1].count)
    }

    @Test
    fun `invoke emits loading then empty when cart is empty`() = runTest {
        coEvery { cartManager.getCartProducts() } returns flow { emit(emptyList()) }

        val resultFlow = getCartProductsUseCase.invoke()

        assertEquals(CartUiState.Loading, resultFlow.first())

        val lastState = resultFlow.last()
        assertEquals(CartUiState.Empty, lastState)
    }
}