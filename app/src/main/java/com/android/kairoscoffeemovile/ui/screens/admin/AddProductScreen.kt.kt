import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Agregar Producto") }) }
    ) { padding ->
        Text(
            text = "Aquí irá el formulario para agregar un producto.",
            modifier = Modifier.padding(padding)
        )
    }
}