package com.android.kairoscoffeemovile.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.kairoscoffeemovile.navigation.Routes
import com.android.kairoscoffeemovile.ui.viewmodels.ProductCrudViewModel
import com.android.kairoscoffeemovile.ui.viewmodels.ProductCrudViewModelFactory
import com.android.kairoscoffeemovile.utils.PreferencesManager
import com.android.kairoscoffeemovile.data.local.AppDatabase
import com.android.kairoscoffeemovile.data.remote.RetrofitClient
import com.android.kairoscoffeemovile.data.remote.api.ProductApi
import com.android.kairoscoffeemovile.data.repository.ProductRepository
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCrudScreen(navController: NavController) {
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

    var showDeleteDialog by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(Unit) { vm.load() }

    // Mostrar errores en Snackbar
    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Panel Admin — Productos") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.CATALOG) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver al catálogo")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.ADMIN_ADD) }) {
                        Icon(Icons.Filled.Add, contentDescription = "Agregar")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            if (loading) LinearProgressIndicator(Modifier.fillMaxWidth())

            LazyColumn(Modifier.fillMaxSize()) {
                items(products) { p ->
                    ListItem(
                        headlineContent = { Text(p.nombre) },
                        supportingContent = {
                            Text("Precio: $${p.precio} | Stock: ${p.stock}")
                        },
                        trailingContent = {
                            Row {
                                IconButton(onClick = {
                                    navController.navigate("admin_edit/${p.id}")
                                }) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Editar")
                                }
                                IconButton(onClick = { showDeleteDialog = p.id }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                                }
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }

    // Diálogo de confirmación para eliminar
    showDeleteDialog?.let { productId ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar este producto?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        vm.delete(productId)
                        showDeleteDialog = null
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}