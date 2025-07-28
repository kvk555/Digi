package com.example.appplus.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appplus.domain.entities.ItemEntity
import com.example.appplus.domain.repository.CatalogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CatalogRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(ScreenState())

    val screenState: StateFlow<ScreenState> = _screenState
        .onStart { getCatalog() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ScreenState()
        )

    private val dataLoadHandler = DataLoadHandler(
        initialId = null,
        onLoadUpdated = { isLoading, currentId, isRefreshing ->
            if (isRefreshing) {
                _screenState.update { it.copy(isRefreshing = true) }
            } else {
                _screenState.update {
                    if (currentId == null && isLoading) {
                        it.copy(
                            loadingState = LoadingState.LOADING,
                            isRefreshing = false
                        )
                    } else {
                        it.copy(
                            isLoadingMore = isLoading,
                            isRefreshing = false
                        )
                    }
                }
            }
        },
        onRequest = { currentId ->
            repository.getCatalog(maxId = currentId)
        },
        getNextKey = { items -> items.last().id },
        onError = { throwable ->
            _screenState.update {
                it.copy(
                    loadingState = LoadingState.ERROR,
                    isLoadingMore = false
                )
            }
        },
        onSuccess = { items, isRefreshing ->
            _screenState.update {
                it.copy(
                    items = if (isRefreshing) {
                        items
                    } else {
                        it.items + items
                    },
                    loadingState = LoadingState.COMPLETE,
                    isLoadingMore = false
                )
            }
        },
        endReached = {
            _screenState.value.items.size == MAX_ITEMS_COUNT
        }
    )

    fun getCatalog(isRefreshing: Boolean = false) {
        if (isRefreshing) {
            dataLoadHandler.reset()
        }
        viewModelScope.launch {
            dataLoadHandler.loadNextItems(isRefreshing)
        }
    }

    data class ScreenState(
        val items: List<ItemEntity> = emptyList(),
        val loadingState: LoadingState = LoadingState.INITIAL,
        val isLoadingMore: Boolean = false,
        val isRefreshing: Boolean = false,

        )

    enum class LoadingState {
        INITIAL,
        LOADING,
        COMPLETE,
        ERROR
    }
}
