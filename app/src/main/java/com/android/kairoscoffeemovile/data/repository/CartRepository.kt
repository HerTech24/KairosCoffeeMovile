package com.android.kairoscoffeemovile.data.repository

class CartRepository(private val cartDao: CartDao) {
    val allCartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun addToCart(cartItem: CartItem) {
        cartDao.insert(cartItem)
    }

    suspend fun updateQuantity(cartItem: CartItem) {
        cartDao.update(cartItem)
    }

    suspend fun removeFromCart(cartItem: CartItem) {
        cartDao.delete(cartItem)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}