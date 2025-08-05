package com.euonlineshopping.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.euonlineshopping.data.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cart_products")
private val CART_PRODUCTS_KEY = stringPreferencesKey("cart_products_key")

@Singleton
class CartManager @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {
    private val productListType = object : TypeToken<MutableList<Product>>() {}.type

    private fun getCartListFromJson(json: String?): MutableList<Product> {
        return if (json.isNullOrEmpty()) {
            mutableListOf()
        } else {
            gson.fromJson(json, productListType)
        }
    }

    private fun saveCartListAsJson(productList: List<Product>): String {
        return gson.toJson(productList, productListType)
    }

    suspend fun addProductToCart(product: Product) {
        context.dataStore.edit { preferences ->
            val cartJson = preferences[CART_PRODUCTS_KEY]
            val currentCart = getCartListFromJson(cartJson)
            currentCart.add(product)
            preferences[CART_PRODUCTS_KEY] = saveCartListAsJson(currentCart)
        }
    }

    suspend fun clearProductFromCart(productId: Int) {
        context.dataStore.edit { preferences ->
            val cartJson = preferences[CART_PRODUCTS_KEY]
            val currentCart = getCartListFromJson(cartJson)
            val updatedCart = currentCart.filter { it.id != productId }
            preferences[CART_PRODUCTS_KEY] = saveCartListAsJson(updatedCart)
        }
    }

    suspend fun removeOneProductFromCart(productId: Int) {
        context.dataStore.edit { preferences ->
            val cartJson = preferences[CART_PRODUCTS_KEY]
            val currentCart = getCartListFromJson(cartJson)
            val productToRemove = currentCart.find { it.id == productId }

            if (productToRemove != null) {
                currentCart.remove(productToRemove)
            }
            preferences[CART_PRODUCTS_KEY] = saveCartListAsJson(currentCart)
        }
    }

    fun getCartProducts(): Flow<List<Product>> {
        return context.dataStore.data.map { preferences ->
            val cartJson = preferences[CART_PRODUCTS_KEY]
            getCartListFromJson(cartJson)
        }
    }
}