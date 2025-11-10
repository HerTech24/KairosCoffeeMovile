package com.android.kairoscoffeemovile.data.local.dao

import androidx.room.*
import com.android.kairoscoffeemovile.data.local.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)
}