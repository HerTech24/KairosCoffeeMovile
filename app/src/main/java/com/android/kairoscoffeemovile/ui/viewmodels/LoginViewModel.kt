package com.android.kairoscoffeemovile.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kairoscoffeemovile.data.repository.AuthRepository
import com.android.kairoscoffeemovile.utils.JwtUtils
import com.android.kairoscoffeemovile.utils.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    val prefs: PreferencesManager,
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    val loading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    fun setError(msg: String?) {
        error.value = msg
    }

    fun auth0SignedIn(idToken: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val email = JwtUtils.extractEmail(idToken)
                prefs.saveLoginState(
                    token = idToken,
                    role = "CUSTOMER",
                    email = email ?: ""
                )
            } catch (ex: Exception) {
                error.value = "Error al procesar Auth0: ${ex.message}"
            } finally {
                loading.value = false
            }
        }
    }

    fun adminLogin(email: String, password: String) {
        viewModelScope.launch {
            loading.value = true
            error.value = null
            try {
                val token = authRepository.adminLogin(email, password)
                if (token == null) {
                    error.value = "Credenciales inválidas"
                } else {
                    prefs.saveLoginState(
                        token = token,
                        role = "ADMIN",
                        email = email
                    )
                }
            } catch (ex: Exception) {
                error.value = ex.message ?: "Error desconocido al iniciar sesión"
            } finally {
                loading.value = false
            }
        }
    }
}