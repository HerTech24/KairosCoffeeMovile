package com.android.kairoscoffeemovile.data.repository

import com.android.kairoscoffeemovile.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {
    private val api = RetrofitClient.apiService

    // Admin login
    suspend fun adminLogin(email: String, password: String): String? = withContext(Dispatchers.IO) {
        val body = mapOf("email" to email, "password" to password)
        val resp = api.adminLogin(body)
        if (resp.isSuccessful) {
            val map = resp.body()
            map?.get("accessToken") as? String
        } else null
    }
}
