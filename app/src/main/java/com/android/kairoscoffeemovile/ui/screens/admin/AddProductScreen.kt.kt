package com.android.kairoscoffeemovile.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.kairoscoffeemovile.ui.viewmodels.ProductCrudViewModel
import com.android.kairoscoffeemovile.ui.viewmodels.ProductCrudViewModelFactory
import com.android.kairoscoffeemovile.utils.PreferencesManager
import com.android.kairoscoffeemovile.data.local.AppDatabase
import com.android.kairoscoffeemovile.data.remote.RetrofitClient
import com.android.kairoscoffeemovile.data.remote.api.ProductApi
import com.android.kairoscoffeemovile.data.remote.dto.ProductDto
import com.android.kairoscoffeemovile.data.repository.ProductRepository
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs = PreferencesManager.getInstance(context)
    val productDao = AppDatabase.getDatabase(context).productDao()
    val api = RetrofitClient.productsRetrofit(prefs).create(ProductApi::class.java)
    val repo = remember { ProductRepository(api, productDao) }
    val vm: ProductCrudViewModel = viewModel(factory = ProductCrudViewModelFactory(repo))

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var urlImagen by remember { mutableStateOf("") }
    var idCategoria by remember { mutableStateOf("") }
    var idProveedor by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val loading by vm.loading.collectAsState()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            errorMessage = null
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Agregar Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre *") }, modifier = Modifier.fillMaxWidth(), enabled = !loading)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth(), enabled = !loading)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio *") }, modifier = Modifier.fillMaxWidth(), enabled = !loading)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock *") }, modifier = Modifier.fillMaxWidth(), enabled = !loading)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = idCategoria, onValueChange = { idCategoria = it }, label = { Text("ID Categoría *") }, modifier = Modifier.fillMaxWidth(), enabled = !loading)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = idProveedor, onValueChange = { idProveedor = it }, label = { Text("ID Proveedor (opcional)") }, modifier = Modifier.fillMaxWidth(), enabled = !loading)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = urlImagen, onValueChange = { urlImagen = it }, label = { Text("URL Imagen") }, modifier = Modifier.fillMaxWidth(), enabled = !loading)
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val precioVal = precio.toDoubleOrNull()
                    val stockVal = stock.toIntOrNull()
                    val categoriaVal = idCategoria.toLongOrNull()

                    when {
                        nombre.isBlank() -> errorMessage = "El nombre es obligatorio"
                        precioVal == null || precioVal <= 0 -> errorMessage = "El precio debe ser válido y mayor a 0"
                        stockVal == null || stockVal < 0 -> errorMessage = "El stock debe ser válido y >= 0"
                        categoriaVal == null -> errorMessage = "La categoría es obligatoria y debe ser un número válido"
                        else -> {
                            val dto = ProductDto(
                                id = null,
                                nombre = nombre,
                                descripcion = descripcion.ifBlank { null },
                                precio = precioVal,
                                stock = stockVal,
                                categoriaId = categoriaVal,
                                proveedorId = idProveedor.toLongOrNull(),
                                urlImagen = urlImagen.ifBlank { null },
                                ofertaActiva = false
                            )
                            vm.create(dto) { navController.popBackStack() }
                        }
                    }
                },
                enabled = !loading && nombre.isNotBlank()
                        && precio.toDoubleOrNull() != null
                        && stock.toIntOrNull() != null
                        && idCategoria.toLongOrNull() != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Guardar")
                }
            }
        }
    }
}
