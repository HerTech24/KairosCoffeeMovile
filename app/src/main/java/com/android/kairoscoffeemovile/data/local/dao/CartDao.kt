package com.android.kairoscoffeemovile.data.local.dao

import androidx.room.*
import com.android.kairoscoffeemovile.data.local.entities.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Update
    suspend fun update(item: CartItem)

    @Delete
    suspend fun delete(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}