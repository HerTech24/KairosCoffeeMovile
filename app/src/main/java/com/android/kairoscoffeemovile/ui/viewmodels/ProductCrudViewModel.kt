package com.android.kairoscoffeemovile.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kairoscoffeemovile.data.local.entities.Product
import com.android.kairoscoffeemovile.data.remote.dto.ProductDto
import com.android.kairoscoffeemovile.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductCrudViewModel(
    private val repo: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _products.value = repo.refresh()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun create(dto: ProductDto, onDone: (() -> Unit)? = null) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.create(dto)
                _products.value = repo.refresh()
                onDone?.invoke()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun update(id: Long, dto: ProductDto, onDone: (() -> Unit)? = null) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.update(id, dto)
                _products.value = repo.refresh()
                onDone?.invoke()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.delete(id)
                _products.value = repo.refresh()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
