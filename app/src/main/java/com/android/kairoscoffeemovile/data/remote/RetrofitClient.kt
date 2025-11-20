package com.android.kairoscoffeemovile.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Productos en tu máquina local (emulador Android -> host machine = 10.0.2.2:8081)
    private const val BASE_URL_PRODUCTS = "http://10.0.2.2:8081/"

    val retrofitProducts: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_PRODUCTS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: com.android.kairoscoffeemovile.data.remote.api.ApiService by lazy {
        retrofitProducts.create(com.android.kairoscoffeemovile.data.remote.api.ApiService::class.java)
    }
}
