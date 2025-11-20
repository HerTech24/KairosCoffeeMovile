package com.android.kairoscoffeemovile.data.remote.dto

data class ProductDto(
    val id: Long? = null,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val stock: Int,
    val categoriaId: Int?,
    val proveedorId: Int?,
    val urlImagen: String?,
    val ofertaActiva: Boolean? = false
)
