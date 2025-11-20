package com.android.kairoscoffeemovile.data.repository

import com.android.kairoscoffeemovile.data.remote.RetrofitClient
import com.android.kairoscoffeemovile.data.remote.api.AuthApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {
    private val api = RetrofitClient.authRetrofit().create(AuthApi::class.java)

    suspend fun adminLogin(email: String, password: String): String? = withContext(Dispatchers.IO) {
        val body = mapOf("email" to email, "password" to password)
        val resp = api.adminLogin(body)
        if (resp.isSuccessful) {
            resp.body()?.get("accessToken") as? String
        } else null
    }
}
