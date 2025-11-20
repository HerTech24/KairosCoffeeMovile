package com.android.kairoscoffeemovile.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(navController: NavController, productId: Long) {
    val context = LocalContext.current
    val prefs = PreferencesManager.getInstance(context)
    val productDao = AppDatabase.getDatabase(context).productDao()
    val api = RetrofitClient.productsRetrofit(prefs).create(ProductApi::class.java)
    val repo = remember { ProductRepository(api, productDao) }
    val vm: ProductCrudViewModel = viewModel(factory = ProductCrudViewModelFactory(repo))

    val scope = rememberCoroutineScope()

    // Estados para los campos
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var urlImagen by remember { mutableStateOf("") }
    var idCategoria by remember { mutableStateOf("") }
    var idProveedor by remember { mutableStateOf("") }

    // Cargar datos del producto al entrar
    LaunchedEffect(productId) {
        scope.launch {
            val resp = api.getProduct(productId)
            if (resp.isSuccessful) {
                resp.body()?.let { dto ->
                    nombre = dto.nombre
                    descripcion = dto.descripcion ?: ""
                    precio = dto.precio.toString()
                    stock = dto.stock.toString()
                    urlImagen = dto.urlImagen ?: ""
                    idCategoria = dto.idCategoria.toString()
                    idProveedor = dto.idProveedor?.toString() ?: ""
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = idCategoria, onValueChange = { idCategoria = it }, label = { Text("ID Categoría") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = idProveedor, onValueChange = { idProveedor = it }, label = { Text("ID Proveedor (opcional)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = urlImagen, onValueChange = { urlImagen = it }, label = { Text("URL Imagen") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = {
                    val dto = ProductDto(
                        id = productId,
                        nombre = nombre,
                        descripcion = descripcion.ifBlank { null },
                        precio = precio.toDoubleOrNull() ?: 0.0,
                        stock = stock.toIntOrNull() ?: 0,
                        idCategoria = idCategoria.toIntOrNull() ?: 0,
                        idProveedor = idProveedor.toIntOrNull(),
                        urlImagen = urlImagen.ifBlank { null },
                        ofertaActiva = false
                    )
                    vm.update(productId, dto) { navController.popBackStack() }
                },
                enabled = nombre.isNotBlank()
                        && precio.toDoubleOrNull() != null
                        && idCategoria.toIntOrNull() != null
            ) {
                Text("Guardar cambios")
            }
        }
    }
}
