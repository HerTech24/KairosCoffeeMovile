package com.android.kairoscoffeemovile.data.remote

import com.android.kairoscoffeemovile.utils.PreferencesManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_AUTH = "http://10.0.2.2:8080/"
private const val BASE_PRODUCTS = "http://10.0.2.2:8081/"

object RetrofitClient {

    fun productsRetrofit(prefs: PreferencesManager): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(prefs))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_PRODUCTS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun authRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_AUTH)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
