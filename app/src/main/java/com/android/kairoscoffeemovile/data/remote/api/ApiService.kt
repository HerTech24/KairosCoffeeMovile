package com.android.kairoscoffeemovile.data.remote.api

import com.android.kairoscoffeemovile.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Admin login (usa puerto 8080 tal como lo tienes)
    @POST("http://10.0.2.2:8080/auth/login")
    suspend fun adminLogin(@Body body: Map<String, String>): Response<Map<String, String>>

    // Productos (serviceproduct en 8081)
    @GET("product/user/list")
    suspend fun getProducts(@Header("Authorization") bearer: String): Response<List<ProductDto>>

    @GET("product/user/{id}")
    suspend fun getProduct(@Header("Authorization") bearer: String, @Path("id") id: Long): Response<ProductDto>

    // Admin CRUD
    @POST("product/admin")
    suspend fun createProduct(@Header("Authorization") bearer: String, @Body product: ProductDto): Response<ProductDto>

    @PUT("product/admin/{id}")
    suspend fun updateProduct(@Header("Authorization") bearer: String, @Path("id") id: Long, @Body product: ProductDto): Response<ProductDto>

    @DELETE("product/admin/{id}")
    suspend fun deleteProduct(@Header("Authorization") bearer: String, @Path("id") id: Long): Response<Unit>
}
