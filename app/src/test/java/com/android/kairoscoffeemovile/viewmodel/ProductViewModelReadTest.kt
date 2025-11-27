package com.android.kairoscoffeemovile.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kairoscoffeemovile.data.local.entities.Product
import com.android.kairoscoffeemovile.data.remote.dto.ProductDto
import com.android.kairoscoffeemovile.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductCrudViewModel(private val repository: ProductRepository) : ViewModel() {

    // Flujo de productos desde la Base de Datos Local
    val products: StateFlow<List<Product>> = repository.observeLocal()
        ?.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        ?: MutableStateFlow(emptyList())

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // --- ESTA ES LA FUNCIÓN QUE TE FALTA ---
    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            try {
                // refresh() llama a la API y guarda en Room
                repository.refresh()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al cargar: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
    // ----------------------------------------

    fun create(productDto: ProductDto, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.create(productDto)
                onSuccess()
                loadProducts() // Recargar lista
            } catch (e: Exception) {
                _error.value = "Error al crear: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                // No hace falta refresh() si el DAO devuelve Flow, Room avisa solo
            } catch (e: Exception) {
                _error.value = "Error al eliminar: ${e.message}"
            }
        }
    }

    // (Opcional) Método update si lo tienes...
    fun update(id: Long, dto: ProductDto, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.update(id, dto)
                onSuccess()
            } catch (e: Exception) {
                _error.value = "Error al actualizar: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}