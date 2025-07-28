package com.example.appplus.domain.repository

import com.example.appplus.domain.entities.ItemEntity

interface CatalogRepository {

    suspend fun getCatalog(maxId: String? = null): Result<List<ItemEntity>>

    suspend fun fetchCatalog(): List<ItemEntity>
}
