package com.euonlineshopping.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.euonlineshopping.data.model.Product
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cart_products")
private val CART_PRODUCTS_KEY = stringSetPreferencesKey("cart_products_key")

@Singleton
class CartManager @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {
    suspend fun addProductToCart(product: Product) {
        context.dataStore.edit { preferences ->
            val currentCartJsonSet = preferences[CART_PRODUCTS_KEY] ?: emptySet()
            val newCartList = currentCartJsonSet.toMutableList()
            
            val productJson = gson.toJson(product)
            newCartList.add(productJson)
            
            preferences[CART_PRODUCTS_KEY] = newCartList.toSet()
        }
    }
    
    suspend fun clearProductFromCart(productId: Int) {
        context.dataStore.edit { preferences ->
            val currentCartJsonSet = preferences[CART_PRODUCTS_KEY] ?: emptySet()

            val updatedCartList = currentCartJsonSet.map { json ->
                gson.fromJson(json, Product::class.java)
            }.filter { it.id != productId }.map { gson.toJson(it) }

            preferences[CART_PRODUCTS_KEY] = updatedCartList.toSet()
        }
    }
    
    fun getCartProducts(): Flow<List<Product>> {
        return context.dataStore.data.map { preferences ->
            val cartJsonSet = preferences[CART_PRODUCTS_KEY] ?: emptySet()
            cartJsonSet.map { json ->
                gson.fromJson(json, Product::class.java)
            }.toList()
        }
    }
    
    suspend fun removeOneProductFromCart(productId: Int) {
         context.dataStore.edit { preferences ->
            val currentCartJsonSet = preferences[CART_PRODUCTS_KEY] ?: emptySet()

            val currentCartList = currentCartJsonSet.map { json ->
                 gson.fromJson(json, Product::class.java)
            }.toMutableList()
            
            val productToRemove = currentCartList.find { it.id == productId }

            if (productToRemove != null) {
                 currentCartList.remove(productToRemove)
            }
            
            preferences[CART_PRODUCTS_KEY] = currentCartList.map { gson.toJson(it) }.toSet()
         }
    }
}