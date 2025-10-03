package com.lucasfaura.miprimercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucasfaura.miprimercompose.ui.theme.MiPrimerComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiPrimerComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),

                    ) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var total by remember { mutableDoubleStateOf(0.0) }
    var porPersona by remember { mutableDoubleStateOf(0.0) }
    var result by remember { mutableStateOf(false) }
    var porcentajePropina by remember { mutableIntStateOf(0) }
    var mensajeError by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(50.dp)) {

        TextField(
            value = text1,
            onValueChange = { newText1 -> text1 = newText1 },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = text2,
            onValueChange = { newText2 -> text2 = newText2},
            label = { Text("Comensales") },
            modifier = Modifier.fillMaxWidth()
        )

        Row {
            Text(
                text = "Redondear Propina",
                        modifier = modifier
            )

            Switch(
            checked = checked,
            onCheckedChange = {
                checked = it }
            )
        }

        Text(
            text = "Valoración",
            modifier = modifier
        )

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            steps = 4,
            valueRange = 0f..5f,
            enabled = checked
        )

        if (checked) {
            porcentajePropina = (sliderPosition.toInt() * 5)
            Text(
                text = "Propina seleccionada: $porcentajePropina%",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(onClick = {
            val cantidad = text1.toDoubleOrNull()
            val comensales = text2.toIntOrNull()
            if (comensales == null || comensales <= 0) {
                mensajeError = "Introduce al menos un comensal"
                result = false
            }
            if (cantidad != null && comensales !=null && comensales > 0){
                total = if (porcentajePropina != 0) {
                    cantidad + ((cantidad*porcentajePropina)/100)
                } else {
                    cantidad
                }
                porPersona = total/comensales
                result = true
            } else {
                result = false
            }
        }) {
            Text("Calcular")
        }

        if (!result && mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (result){
            Text(
                text = "Valor total: $total",
                modifier = modifier
            )

            Text(
                text = "Valor por Persona: $porPersona",
                modifier = modifier
            )
        }

    }
}