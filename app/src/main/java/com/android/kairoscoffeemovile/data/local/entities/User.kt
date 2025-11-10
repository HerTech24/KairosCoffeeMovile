package com.android.kairoscoffeemovile.data.local.entities

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val username: String,
    val password: String
)