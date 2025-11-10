package com.android.kairoscoffeemovile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.kairoscoffeemovile.ui.screens.login.LoginScreen
import com.android.kairoscoffeemovile.ui.screens.catalog.CatalogScreen
import com.android.kairoscoffeemovile.ui.screens.cart.CartScreen
import com.android.kairoscoffeemovile.ui.screens.admin.ProductCrudScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) { LoginScreen(navController) }
        composable(Routes.CATALOG) { CatalogScreen(navController) }
        composable(Routes.CART) { CartScreen(navController) }
        composable(Routes.ADMIN) { ProductCrudScreen(navController) }
    }
}