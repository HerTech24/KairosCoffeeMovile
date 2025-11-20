package com.android.kairoscoffeemovile.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.kairoscoffeemovile.ui.screens.admin.ProductCrudScreen
import com.android.kairoscoffeemovile.ui.screens.admin.AddProductScreen
import com.android.kairoscoffeemovile.ui.screens.admin.EditProductScreen
import com.android.kairoscoffeemovile.ui.screens.catalog.CatalogScreen
import com.android.kairoscoffeemovile.ui.screens.cart.CartScreen
import com.android.kairoscoffeemovile.ui.screens.login.LoginScreen
import com.android.kairoscoffeemovile.ui.viewmodels.LoginViewModel
import com.android.kairoscoffeemovile.ui.viewmodels.LoginViewModelFactory
import com.android.kairoscoffeemovile.utils.PreferencesManager
import kotlinx.coroutines.flow.first
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NavGraphWithAuth0(onAuth0LoginRequested: () -> Unit) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val prefs = PreferencesManager.getInstance(context)
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(prefs))

    var initialRoute by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val logged = prefs.isLoggedInFlow.first()
        val role = prefs.userRoleFlow.first()

        initialRoute = when {
            !logged -> Routes.LOGIN
            role.equals("ADMIN", true) -> Routes.ADMIN
            else -> Routes.CATALOG
        }
    }

    if (initialRoute == null) return

    NavHost(
        navController = navController,
        startDestination = initialRoute!!
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController,
                viewModel = loginViewModel,
                onAuth0LoginRequested = onAuth0LoginRequested
            )
        }
        composable(Routes.CATALOG) { CatalogScreen(navController) }
        composable(Routes.CART) { CartScreen(navController) }
        composable(Routes.ADMIN) { ProductCrudScreen(navController) }
        composable(Routes.ADMIN_ADD) { AddProductScreen(navController) }
        composable("admin_edit/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")?.toLongOrNull() ?: 0L
            EditProductScreen(navController, productId = id)
        }
    }
}