package com.lucasfaura.miprimercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
    var cantidadText by remember { mutableStateOf("") }
    var comensalesText by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var total by remember { mutableDoubleStateOf(0.0) }
    var porPersona by remember { mutableDoubleStateOf(0.0) }
    var result by remember { mutableStateOf(false) }
    var porcentajePropina by remember { mutableIntStateOf(0) }
    var mensajeError by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            value = cantidadText,
            onValueChange = { cantidadText = it },
            label = { Text(stringResource(R.string.amount)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            value = comensalesText,
            onValueChange = { comensalesText = it },
            label = { Text(stringResource(R.string.guests)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.round_tip),
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Text(
            text = stringResource(R.string.rating),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            steps = 4,
            valueRange = 0f..5f,
            enabled = checked,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary
            )
        )

        if (checked) {
            porcentajePropina = (sliderPosition.toInt() * 5)
            Text(
                text = stringResource(R.string.selected_tip, porcentajePropina),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                val cantidad = cantidadText.toDoubleOrNull()
                val comensales = comensalesText.toIntOrNull()
                if (comensales == null || comensales <= 0) {
                    mensajeError = context.getString(R.string.error_guests)
                    result = false
                }
                if (cantidad != null && comensales != null && comensales > 0) {
                    total = if (porcentajePropina != 0) {
                        cantidad + ((cantidad * porcentajePropina) / 100)
                    } else {
                        cantidad
                    }
                    porPersona = total / comensales
                    result = true
                } else {
                    result = false
                    if (cantidad == null) mensajeError =
                        context.getString(R.string.error_amount)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_calculate),
                    contentDescription = "Icono de calcular",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.calculate))
            }
        }

        if (!result && mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (result) {
            Text(
                text = stringResource(R.string.total_value, total.toString()),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = stringResource(R.string.per_person, porPersona.toString()),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

