package com.subha.optymize.data.remote.api

import com.subha.optymize.core.util.Endpoints
import com.subha.optymize.data.model.Product
import com.subha.optymize.data.model.ProductListRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ShoeaApi {

    @GET(Endpoints.PRODUCTS)
    suspend fun getproductlist(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): ProductListRes

    @GET()
    suspend fun getproductlist(@Url url: String): Response<Product>

}