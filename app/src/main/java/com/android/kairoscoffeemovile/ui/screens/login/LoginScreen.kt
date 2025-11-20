package com.android.kairoscoffeemovile.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.kairoscoffeemovile.navigation.Routes
import com.android.kairoscoffeemovile.ui.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    onAuth0LoginRequested: (() -> Unit)? = null
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    // Observar cambios en el estado de login para navegar
    val isLoggedIn by viewModel.prefs.isLoggedInFlow.collectAsState(initial = false)
    val userRole by viewModel.prefs.userRoleFlow.collectAsState(initial = "")

    LaunchedEffect(isLoggedIn, userRole) {
        if (isLoggedIn) {
            val destination = if (userRole.equals("ADMIN", true)) {
                Routes.ADMIN
            } else {
                Routes.CATALOG
            }
            navController.navigate(destination) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }
        }
    }

    // Mostrar errores en Snackbar
    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.setError(null)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Surface(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo (Admin)") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !loading
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !loading
                )
                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.adminLogin(email, password)
                        }
                    },
                    enabled = !loading && email.isNotBlank() && password.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ingresar (Admin)")
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Text("O ingresa con Google (Cliente)")
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { onAuth0LoginRequested?.invoke() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !loading
                ) {
                    Text("Ingresar con Google (Auth0)")
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (loading) CircularProgressIndicator()
            }
        }
    }
}