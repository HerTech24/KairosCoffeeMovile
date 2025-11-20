package com.android.kairoscoffeemovile.data.remote.dto

data class ProductDto(
    val id: Long? = null,
    val nombre: String,
    val descripcion: String? = null,
    val precio: Double,
    val stock: Int = 0,
    val categoriaId: Long,          // ⚠️ obligatorio, tipo Long
    val proveedorId: Long? = null,  // opcional, tipo Long
    val urlImagen: String? = null,
    val ofertaActiva: Boolean? = false
)