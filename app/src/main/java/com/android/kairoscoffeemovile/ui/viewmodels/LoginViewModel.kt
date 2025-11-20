package com.android.kairoscoffeemovile.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kairoscoffeemovile.utils.JwtUtils
import com.android.kairoscoffeemovile.utils.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val prefs: PreferencesManager
) : ViewModel() {

    val loading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    fun setError(msg: String) { error.value = msg }

    fun auth0SignedIn(idToken: String, onDone: () -> Unit) {
        viewModelScope.launch {
            loading.value = true

            val email = JwtUtils.extractEmail(idToken)

            prefs.saveLoginState(
                token = idToken,
                role = "CUSTOMER",
                email = email ?: ""
            )

            loading.value = false
            onDone()
        }
    }

    fun adminLogin(email: String, password: String, onDone: () -> Unit) {
        viewModelScope.launch {
            loading.value = true
            try {
                prefs.saveLoginState(
                    token = "dummy-admin",
                    role = "ADMIN",
                    email = email
                )
                loading.value = false
                onDone()
            } catch (ex: Exception) {
                loading.value = false
                error.value = ex.message
            }
        }
    }
}
