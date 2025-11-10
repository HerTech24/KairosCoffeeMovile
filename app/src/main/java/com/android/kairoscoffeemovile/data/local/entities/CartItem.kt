package com.android.kairoscoffeemovile.data.local.entities

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val productName: String,
    val price: Double,
    var quantity: Int
)