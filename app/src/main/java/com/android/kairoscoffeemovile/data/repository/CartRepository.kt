package com.android.kairoscoffeemovile.data.repository

import com.android.kairoscoffeemovile.data.local.dao.CartDao
import com.android.kairoscoffeemovile.data.local.entities.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val allCartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun insert(item: CartItem) {
        cartDao.insert(item)
    }

    suspend fun update(item: CartItem) {
        cartDao.update(item)
    }

    suspend fun delete(item: CartItem) {
        cartDao.delete(item)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}