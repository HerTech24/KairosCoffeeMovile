package com.android.kairoscoffeemovile.ui.screens.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.kairoscoffeemovile.navigation.Routes
import com.android.kairoscoffeemovile.utils.PreferencesManager
import com.android.kairoscoffeemovile.data.local.AppDatabase
import com.android.kairoscoffeemovile.data.remote.RetrofitClient
import com.android.kairoscoffeemovile.data.remote.api.ProductApi
import com.android.kairoscoffeemovile.data.repository.ProductRepository
import com.android.kairoscoffeemovile.ui.viewmodels.ProductCrudViewModel
import com.android.kairoscoffeemovile.ui.viewmodels.ProductCrudViewModelFactory
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
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
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) { vm.load() }

    // Mostrar errores
    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Catálogo Kairos Coffee") },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.CART) }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Catálogo de productos ☕",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(16.dp))

            if (loading) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
            }

            if (products.isEmpty() && !loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        "No hay productos disponibles",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(products) { p ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            ListItem(
                                headlineContent = { Text(p.nombre) },
                                supportingContent = {
                                    Column {
                                        p.descripcion?.let {
                                            Text(it, style = MaterialTheme.typography.bodySmall)
                                        }
                                        Text(
                                            "Precio: $${p.precio}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            "Stock disponible: ${p.stock}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                },
                                trailingContent = {
                                    Button(
                                        onClick = { /* Agregar al carrito */ },
                                        enabled = p.stock > 0
                                    ) {
                                        Text("Agregar")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}