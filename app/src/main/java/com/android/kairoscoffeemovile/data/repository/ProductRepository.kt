package com.android.kairoscoffeemovile.data.repository

import com.android.kairoscoffeemovile.data.local.dao.ProductDao
import com.android.kairoscoffeemovile.data.local.entities.Product
import com.android.kairoscoffeemovile.data.remote.api.ProductApi
import com.android.kairoscoffeemovile.data.remote.dto.toEntity
import com.android.kairoscoffeemovile.data.remote.dto.ProductDto
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val api: ProductApi,
    private val productDao: ProductDao?
) {

    fun observeLocal(): Flow<List<Product>>? = productDao?.getAllProducts()

    suspend fun refresh(): List<Product> {
        val resp = api.getProducts()
        if (!resp.isSuccessful) throw IllegalStateException("Error al obtener productos: ${resp.code()}")
        val list = resp.body().orEmpty().map { it.toEntity() }
        productDao?.let { dao ->
            // Cache sencilla: reinsertar (puedes mejorar con transacciones o clear)
            list.forEach { dao.insertProduct(it) }
        }
        return list
    }

    suspend fun create(dto: ProductDto): Product {
        val resp = api.createProduct(dto)
        if (!resp.isSuccessful) throw IllegalStateException("Error al crear producto: ${resp.code()}")
        val entity = resp.body()!!.toEntity()
        productDao?.insertProduct(entity)
        return entity
    }

    suspend fun update(id: Long, dto: ProductDto): Product {
        val resp = api.updateProduct(id, dto)
        if (!resp.isSuccessful) throw IllegalStateException("Error al actualizar producto: ${resp.code()}")
        val entity = resp.body()!!.toEntity()
        productDao?.insertProduct(entity)
        return entity
    }

    suspend fun delete(id: Long) {
        val resp = api.deleteProduct(id)
        if (!resp.isSuccessful) throw IllegalStateException("Error al eliminar producto: ${resp.code()}")
        productDao?.deleteById(id)
    }
}
