package com.android.kairoscoffeemovile.data.remote.api

import com.android.kairoscoffeemovile.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {
    @GET("product/user/list")
    suspend fun getProducts(): Response<List<ProductDto>>

    @GET("product/user/{id}")
    suspend fun getProduct(@Path("id") id: Long): Response<ProductDto>

    @POST("product/admin")
    suspend fun createProduct(@Body product: ProductDto): Response<ProductDto>

    @PUT("product/admin/{id}")
    suspend fun updateProduct(@Path("id") id: Long, @Body product: ProductDto): Response<ProductDto>

    @DELETE("product/admin/{id}")
    suspend fun deleteProduct(@Path("id") id: Long): Response<Unit>
}
