package com.android.kairoscoffeemovile.ui.screens.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.kairoscoffeemovile.utils.PreferencesManager
import com.android.kairoscoffeemovile.data.local.AppDatabase
import com.android.kairoscoffeemovile.data.remote.RetrofitClient
import com.android.kairoscoffeemovile.data.remote.api.ProductApi
import com.android.kairoscoffeemovile.data.repository.ProductRepository
import com.android.kairoscoffeemovile.ui.viewmodels.ProductCrudViewModel
import com.android.kairoscoffeemovile.ui.viewmodels.ProductCrudViewModelFactory
import androidx.compose.ui.unit.dp

@Composable
fun CatalogScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs = PreferencesManager.getInstance(context)
    val productDao = AppDatabase.getDatabase(context).productDao()
    val api = RetrofitClient.productsRetrofit(prefs).create(ProductApi::class.java)
    val repo = remember { ProductRepository(api, productDao) }
    val vm: ProductCrudViewModel = viewModel(factory = ProductCrudViewModelFactory(repo))

    val products by vm.products.collectAsState()
    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()

    LaunchedEffect(Unit) { vm.load() }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text("Catálogo de productos ☕", style = MaterialTheme.typography.headlineMedium)
            if (loading) LinearProgressIndicator(Modifier.fillMaxWidth())
            if (!error.isNullOrEmpty()) Text(error ?: "", color = MaterialTheme.colorScheme.error)

            LazyColumn {
                items(products) { p ->
                    ListItem(
                        headlineContent = { Text(p.nombre) },
                        supportingContent = { Text("Precio: ${p.precio}") }
                    )
                    Divider()
                }
            }
        }
    }
}
