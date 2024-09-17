package com.example.calculadora_resistencia.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun menu() {

    val context = LocalContext.current

    var banda1 by remember { mutableStateOf<String?>(null) }
    var banda2 by remember { mutableStateOf<String?>(null) }
    var banda3 by remember { mutableStateOf<String?>(null) }
    var tolerancia by remember { mutableStateOf<String?>(null) }
    var isExpanded by remember { mutableStateOf(false) }
    var resistencia by remember { mutableStateOf<String>("") }

    // Función para calcular la resistencia
    fun calcularResistencia() {
        val colorCode = mapOf(
            "Negro" to 0, "Marrón" to 1, "Rojo" to 2, "Naranja" to 3,
            "Amarillo" to 4, "Verde" to 5, "Azul" to 6, "Violeta" to 7,
            "Gris" to 8, "Blanco" to 9
        )

        val toleranciaCode = mapOf(
            "Dorado" to 0.05, "Plata" to 0.10, "Blanco" to 0.25
        )

        val valor1 = banda1?.let { colorCode[it] } ?: return
        val valor2 = banda2?.let { colorCode[it] } ?: return
        val valor3 = banda3?.let { colorCode[it] } ?: return
        val toleranciaValor = tolerancia?.let { toleranciaCode[it] } ?: return

        val resistenciaValor = (valor1 * 100 + valor2 * 10 + valor3) * Math.pow(10.0, 0.0)  // El multiplicador es 1
        resistencia = String.format("%.2f Ω ± %.2f%%", resistenciaValor, toleranciaValor * 100)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Banda 1
        DropdownMenuSelector(
            label = "Selecciona Banda 1",
            selectedValue = banda1,
            onValueChange = { banda1 = it },
            menuItems = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
        )

        // Banda 2
        DropdownMenuSelector(
            label = "Selecciona Banda 2",
            selectedValue = banda2,
            onValueChange = { banda2 = it },
            menuItems = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
        )

        // Banda 3
        DropdownMenuSelector(
            label = "Selecciona Banda 3",
            selectedValue = banda3,
            onValueChange = { banda3 = it },
            menuItems = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
        )

        // Tolerancia
        DropdownMenuSelector(
            label = "Selecciona Tolerancia",
            selectedValue = tolerancia,
            onValueChange = { tolerancia = it },
            menuItems = listOf("Dorado", "Plata", "Blanco")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { calcularResistencia() }) {
            Text("Calcular Resistencia")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Resistencia: $resistencia", fontSize = 20.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuSelector(
    label: String,
    selectedValue: String?,
    onValueChange: (String) -> Unit,
    menuItems: List<String>
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        TextField(
            value = selectedValue ?: label,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        isExpanded = false
                    }
                )
            }
        }
    }
}
