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

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favorite_products")
private val FAVORITE_PRODUCTS_KEY = stringSetPreferencesKey("favorite_products_key")

class FavoriteProductManager(
    private val context: Context,
    private val gson: Gson
) {

    suspend fun toggleFavorite(product: Product) {
        context.dataStore.edit { preferences ->
            val currentFavoritesJsonSet = preferences[FAVORITE_PRODUCTS_KEY] ?: emptySet()

            val currentFavorites = currentFavoritesJsonSet.map { json ->
                gson.fromJson(json, Product::class.java)
            }.toMutableList()

            val existingProduct = currentFavorites.find { it.id == product.id }

            if (existingProduct != null) {
                currentFavorites.remove(existingProduct)
            } else {
                val productToAdd = product.copy(isFavorite = true)
                currentFavorites.add(productToAdd)
            }

            val updatedJsonSet = currentFavorites.map { gson.toJson(it) }.toSet()
            preferences[FAVORITE_PRODUCTS_KEY] = updatedJsonSet
        }
    }

    fun getFavoriteProducts(): Flow<List<Product>> {
        return context.dataStore.data.map { preferences ->
            val favoriteJsonSet = preferences[FAVORITE_PRODUCTS_KEY] ?: emptySet()
            favoriteJsonSet.map { json ->
                gson.fromJson(json, Product::class.java)
            }.toList()
        }
    }

    fun isProductFavorite(productId: Int): Flow<Boolean> {
        return getFavoriteProducts().map { favoriteProducts ->
            favoriteProducts.any { it.id == productId }
        }
    }
}