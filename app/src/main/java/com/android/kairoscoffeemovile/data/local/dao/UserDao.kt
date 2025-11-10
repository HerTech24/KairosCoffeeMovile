package com.android.kairoscoffeemovile.data.local.dao

import androidx.room.*
import com.android.kairoscoffeemovile.data.local.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun register(user: User)

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): User?
}