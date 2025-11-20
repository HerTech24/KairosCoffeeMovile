package com.android.kairoscoffeemovile.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun adminLogin(@Body body: Map<String, String>): Response<Map<String, String>>
}
