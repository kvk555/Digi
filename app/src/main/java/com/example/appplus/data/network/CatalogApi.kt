package com.example.appplus.data.network

import com.example.appplus.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


const val BASE_URL = "https://marlove.net/e/mock/v1/"

interface CatalogApi {

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("items")
    suspend fun getCatalog(
        @Query("since_id") sinceId: String? = null,
        @Query("max_id") maxId: String? = null
    ): List<Item>
}
