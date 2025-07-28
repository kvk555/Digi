package com.example.appplus.data

import com.example.appplus.data.database.AppDatabase
import com.example.appplus.data.database.toEntity
import com.example.appplus.data.network.CatalogApi
import com.example.appplus.data.network.toDbEntity
import com.example.appplus.data.network.toEntity
import com.example.appplus.domain.entities.ItemEntity
import com.example.appplus.domain.repository.CatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatalogRepositoryImpl(
    private val api: CatalogApi,
    private val database: AppDatabase
) : CatalogRepository {

    override suspend fun getCatalog(maxId: String?): Result<List<ItemEntity>> {

        val items = try {
            withContext(Dispatchers.IO) {
                api.getCatalog(maxId = maxId)
            }
        } catch (e: Exception) {
            val catalog = fetchCatalog()
            return if (catalog.isEmpty()) {
                Result.failure(e)
            } else {
                Result.success(catalog)
            }
        }

        database.getAppDao().insert(items.map { it.toDbEntity() })

        return Result.success(items.map { it.toEntity() })
    }

    override suspend fun fetchCatalog(): List<ItemEntity> =
        database.getAppDao().fetchItems().map { it.toEntity() }
}
