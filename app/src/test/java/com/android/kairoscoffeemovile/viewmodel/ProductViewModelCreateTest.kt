package com.android.kairoscoffeemovile.viewmodels

import com.android.kairoscoffeemovile.data.local.entities.Product
import com.android.kairoscoffeemovile.data.remote.dto.ProductDto
import com.android.kairoscoffeemovile.data.repository.ProductRepository
import com.android.kairoscoffeemovile.ui.viewmodels.ProductCrudViewModel
import com.android.kairoscoffeemovile.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ProductCrudViewModelTest {

    // REGLA: Permite usar viewModelScope en los tests
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var repository: ProductRepository

    private lateinit var viewModel: ProductCrudViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        // Inicializamos el ViewModel real con el Mock del repositorio
        viewModel = ProductCrudViewModel(repository)
    }

    @Test
    fun `crear producto correctamente llama al repositorio y finaliza carga`() = runTest {
        // 1. GIVEN (Datos de entrada y respuesta esperada)
        val inputDto = ProductDto(
            nombre = "Café Latte",
            precio = 3500.0,
            stock = 20,
            categoriaId = 1L,
            descripcion = "Delicioso café"
        )

        val expectedResult = Product(
            id = 100L, // El ID lo genera el backend/BD
            nombre = "Café Latte",
            precio = 3500.0,
            stock = 20,
            categoriaId = 1L,
            descripcion = "Delicioso café"
        )

        // Simulamos el comportamiento del repositorio:
        // "Cuando llamen a create con cualquier cosa, devuelve expectedResult"
        whenever(repository.create(any())).thenReturn(expectedResult)

        // 2. WHEN (Acción)
        // Ejecutamos la función. Pasamos lambda vacía {} para el callback onSuccess
        viewModel.create(inputDto) {}

        // 3. THEN (Verificación)

        // A) Verificamos que el método del repositorio fue llamado con el DTO correcto
        Mockito.verify(repository).create(inputDto)

        // B) Verificamos el estado del Loading
        // Al terminar, el loading debería ser false
        val isLoading = viewModel.loading.first()
        assertEquals(false, isLoading)
    }
}