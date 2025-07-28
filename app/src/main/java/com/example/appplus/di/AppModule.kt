package com.example.appplus.di

import android.content.Context
import com.example.appplus.data.CatalogRepositoryImpl
import com.example.appplus.data.database.AppDatabase
import com.example.appplus.data.network.BASE_URL
import com.example.appplus.data.network.CatalogApi
import com.example.appplus.domain.repository.CatalogRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppModule {
    val api: CatalogApi
    val database: AppDatabase
    val repository: CatalogRepository
}

class AppModuleImpl(
    private val appContext: Context
) : AppModule {

    override val api: CatalogApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CatalogApi::class.java)
    }

    override val database: AppDatabase by lazy {
        AppDatabase.getInstance(appContext)
    }

    override val repository: CatalogRepository by lazy {
        CatalogRepositoryImpl(api, database)
    }
}