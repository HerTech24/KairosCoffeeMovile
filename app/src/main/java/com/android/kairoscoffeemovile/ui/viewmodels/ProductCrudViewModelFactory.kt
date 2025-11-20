package com.android.kairoscoffeemovile.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.kairoscoffeemovile.data.repository.ProductRepository

class ProductCrudViewModelFactory(
    private val repo: ProductRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductCrudViewModel::class.java)) {
            return ProductCrudViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
