package com.android.kairoscoffeemovile.data.remote.dto

import com.android.kairoscoffeemovile.data.local.entities.Product

fun ProductDto.toEntity(): Product = Product(
    id = (id ?: 0L),
    nombre = nombre,
    precio = precio,
    descripcion = descripcion,
    urlImagen = urlImagen,
    stock = stock,
    idCategoria = idCategoria,
    idProveedor = idProveedor
)

fun Product.toDto(): ProductDto = ProductDto(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    precio = precio,
    stock = stock,
    idCategoria = idCategoria,
    idProveedor = idProveedor,
    urlImagen = urlImagen,
    ofertaActiva = false
)
