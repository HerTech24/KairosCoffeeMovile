package com.android.kairoscoffeemovile.data.local.entities

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val category: String,
    val stock: Int,
    val imageUrl: String = ""
)