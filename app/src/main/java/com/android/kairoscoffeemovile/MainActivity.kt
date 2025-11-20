package com.android.kairoscoffeemovile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.android.kairoscoffeemovile.navigation.NavGraphWithAuth0
import com.android.kairoscoffeemovile.ui.theme.KairosCoffeeMovileTheme
import com.android.kairoscoffeemovile.ui.viewmodels.LoginViewModel
import com.android.kairoscoffeemovile.ui.viewmodels.LoginViewModelFactory
import com.android.kairoscoffeemovile.utils.PreferencesManager

import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.callback.Callback
import com.auth0.android.authentication.AuthenticationException

class MainActivity : ComponentActivity() {

    private lateinit var auth0: Auth0

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(PreferencesManager.getInstance(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth0 = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain)
        )

        setContent {
            KairosCoffeeMovileTheme {
                NavGraphWithAuth0(
                    onAuth0LoginRequested = { startAuth0Login() }
                )
            }
        }
    }

    private fun startAuth0Login() {
        WebAuthProvider
            .login(auth0)
            .withScheme("demo")
            .withScope("openid profile email")
            .start(
                this,
                object : Callback<Credentials, AuthenticationException> {
                    override fun onSuccess(result: Credentials) {
                        val idToken = result.idToken
                        loginViewModel.auth0SignedIn(idToken) {
                            // Navegación automática por NavGraph
                        }
                    }
                    override fun onFailure(error: AuthenticationException) {
                        loginViewModel.setError("Auth0 Error: ${error.message ?: "Error desconocido"}")
                    }
                }
            )
    }
}
