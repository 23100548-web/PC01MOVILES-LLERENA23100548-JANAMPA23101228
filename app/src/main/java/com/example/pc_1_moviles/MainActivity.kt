package com.example.pc_1_moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pc_1_moviles.ui.theme.Pc_1_movilesTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pc_1_movilesTheme{
                TravelCompanionApp()
            }
        }
    }
}

@Composable
fun TravelCompanionApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            MenuPrincipal(
                irEquipaje = { navController.navigate("equipaje") },
                irPresupuesto = { navController.navigate("presupuesto") },
                irDestinos = { navController.navigate("destinos") },
                irUbicacion = { navController.navigate("ubicacion") }
            )
        }

        composable("equipaje") {
            CalculadoraEquipajeScreen(
                volver = { navController.navigate("menu") }
            )
        }

        composable("presupuesto") {
            PantallaTemporal(
                titulo = "Planificador de Presupuesto de Viaje",
                volver = { navController.navigate("menu") }
            )
        }

        composable("destinos") {
            PantallaTemporal(
                titulo = "Catálogo de Destinos Turísticos",
                volver = { navController.navigate("menu") }
            )
        }

        composable("ubicacion") {
            PantallaTemporal(
                titulo = "Permiso de Ubicación para Asistencia de Viaje",
                volver = { navController.navigate("menu") }
            )
        }
    }
}

@Composable
fun MenuPrincipal(
    irEquipaje: () -> Unit,
    irPresupuesto: () -> Unit,
    irDestinos: () -> Unit,
    irUbicacion: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Travel Companion App",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = irEquipaje,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calculadora de Equipaje")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = irPresupuesto,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Planificador de Presupuesto")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = irDestinos,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Catálogo de Destinos Turísticos")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = irUbicacion,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Permiso de Ubicación")
            }
        }
    }
}

@Composable
fun PantallaTemporal(
    titulo: String,
    volver: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = volver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al menú principal")
            }
        }
    }
}
@Composable
fun CalculadoraEquipajeScreen(volver: () -> Unit) {
    var pesoTexto by remember { mutableStateOf("") }
    var tipoVuelo by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val tiposVuelo = listOf("Nacional", "Internacional")

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Calculadora de Equipaje",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pesoTexto,
                onValueChange = { pesoTexto = it },
                label = { Text("Peso de la maleta en kg") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Tipo de vuelo")

            tiposVuelo.forEach { tipo ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = tipoVuelo == tipo,
                        onClick = { tipoVuelo = tipo }
                    )
                    Text(tipo)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val peso = pesoTexto.toDoubleOrNull()

                    if (pesoTexto.isBlank() || tipoVuelo.isBlank()) {
                        mensajeError = "Todos los campos son obligatorios"
                        resultado = ""
                    } else if (peso == null) {
                        mensajeError = "El peso debe ser un valor numerico"
                        resultado = ""
                    } else if (peso <= 0) {
                        mensajeError = "El peso debe ser mayor a cero"
                        resultado = ""
                    } else {
                        val limite = if (tipoVuelo == "Nacional") 23.0 else 32.0

                        resultado = if (peso <= limite) {
                            "La maleta cumple el limite permitido para vuelo $tipoVuelo."
                        } else {
                            val exceso = peso - limite
                            "La maleta excede el limite permitido por %.2f kg.".format(exceso)
                        }

                        mensajeError = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Verificar equipaje")
            }

            if (mensajeError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (resultado.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(resultado)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = volver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al menu principal")
            }
        }
    }
}