package com.android.kairoscoffeemovile.data.remote.dto

import com.android.kairoscoffeemovile.data.local.entities.Product

fun ProductDto.toEntity(): Product = Product(
    id = (id ?: 0L),
    nombre = nombre,
    precio = precio,
    descripcion = descripcion,
    urlImagen = urlImagen,
    stock = stock,
    categoriaId = categoriaId,
    proveedorId = proveedorId
)

fun Product.toDto(): ProductDto = ProductDto(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    precio = precio,
    stock = stock,
    categoriaId = categoriaId,
    proveedorId = proveedorId,
    urlImagen = urlImagen,
    ofertaActiva = false
)
