package com.example.appplus.presentation

import com.example.appplus.domain.entities.ItemEntity

const val MAX_ITEMS_COUNT = 30

class DataLoadHandler(
    private val initialId: String?,
    private val onLoadUpdated: (Boolean, String?, Boolean) -> Unit,
    private val onRequest: suspend (nextKey: String?) -> Result<List<ItemEntity>>,
    private val getNextKey: suspend (result: List<ItemEntity>) -> String,
    private val onError: suspend (Throwable?) -> Unit,
    private val onSuccess: suspend (result: List<ItemEntity>, isRefreshing: Boolean) -> Unit,
    private val endReached: () -> Boolean
) {
    private var currentId = initialId
    private var isMakingRequest = false
    private var isEndReached = false

    suspend fun loadNextItems(isRefreshing: Boolean) {
        if (isMakingRequest || isEndReached) {
            return
        }

        isMakingRequest = true
        onLoadUpdated(true, currentId, isRefreshing)

        val result = onRequest(currentId)
        isMakingRequest = false

        val response = result.getOrElse {
            onError(it)
            onLoadUpdated(false, currentId, false)
            return
        }

        currentId = getNextKey(response)

        onSuccess(response, isRefreshing)

        onLoadUpdated(false, currentId, false)

        isEndReached = endReached()
    }

    fun reset() {
        currentId = initialId
        isEndReached = false
        isMakingRequest = false
    }
}
