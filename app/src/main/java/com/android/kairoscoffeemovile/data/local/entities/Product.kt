package com.android.kairoscoffeemovile.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Long,
    val nombre: String,
    val precio: Double,
    val descripcion: String? = null,
    val urlImagen: String? = null,
    val stock: Int = 0,
    val idCategoria: Int,          // ⚠️ obligatorio
    val idProveedor: Int? = null
)
