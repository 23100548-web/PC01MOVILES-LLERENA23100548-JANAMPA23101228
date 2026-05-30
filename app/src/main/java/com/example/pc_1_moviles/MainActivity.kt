//Janampa Diaz
//LLERENA CABRERA
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api


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
            PlanificadorPresupuestoScreen(
                volver = { navController.navigate("menu") }
            )
        }

        composable("destinos") {
            CatalogoDestinosScreen(
                volver = { navController.navigate("menu") }
            )
        }

        composable("ubicacion") {
            PermisoUbicacionScreen(
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

data class Destination(
    val pais: String,
    val ciudad: String,
    val costoPromedio: Double,
    val imageUrl: String
)
// 1. REQUISITO: Crear el Data Class llamado Destination (5 puntos)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorPresupuestoScreen(volver: () -> Unit) {
    var diasTexto by remember { mutableStateOf("") }
    var presupuestoTexto by remember { mutableStateOf("") }
    var tipoAlojamiento by remember { mutableStateOf("Estándar (1.0)") }
    var resultado by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // Opciones del Dropdown con sus respectivos factores de multiplicación
    val opcionesAlojamiento = listOf("Económico (0.8)", "Estándar (1.0)", "Premium (1.5)")

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Planificador de Presupuesto",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Entrada de cantidad de días
            OutlinedTextField(
                value = diasTexto,
                onValueChange = { diasTexto = it },
                label = { Text("Cantidad de días") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Entrada de presupuesto diario
            OutlinedTextField(
                value = presupuestoTexto,
                onValueChange = { presupuestoTexto = it },
                label = { Text("Presupuesto diario ($)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Tipo de alojamiento", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))

            // Menú Desplegable Oficial Obligatorio (Dropdown Menu)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = tipoAlojamiento,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opcionesAlojamiento.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                tipoAlojamiento = opcion
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val dias = diasTexto.toIntOrNull()
                    val diario = presupuestoTexto.toDoubleOrNull()

                    // Validaciones de la rúbrica
                    if (diasTexto.isBlank() || presupuestoTexto.isBlank()) {
                        mensajeError = "Todos los campos son obligatorios"
                        resultado = ""
                    } else if (dias == null || diario == null) {
                        mensajeError = "Los días y el presupuesto deben ser numéricos"
                        resultado = ""
                    } else if (dias <= 0) {
                        mensajeError = "La cantidad de días debe ser mayor a cero"
                        resultado = ""
                    } else if (diario <= 0) {
                        mensajeError = "El presupuesto diario debe ser mayor a cero"
                        resultado = ""
                    } else {
                        // Obtención del factor según el alojamiento seleccionado
                        val factor = when {
                            tipoAlojamiento.contains("Económico") -> 0.8
                            tipoAlojamiento.contains("Premium") -> 1.5
                            else -> 1.0
                        }

                        // Fórmula de cálculo estipulada
                        val total = dias * diario * factor

                        resultado = "Presupuesto total estimado: $${String.format("%.2f", total)}\nEscenario: Viaje planificado para un entorno $tipoAlojamiento durante $dias días."
                        mensajeError = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular Presupuesto")
            }

            if (mensajeError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = mensajeError, color = MaterialTheme.colorScheme.error)
            }

            if (resultado.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = resultado,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

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
fun CatalogoDestinosScreen(volver: () -> Unit) {
    // 2. REQUISITO: Lista simulada con al menos 5 destinos
    val listaDestinos = remember {
        listOf(
            // Nueva URL para Perú (Machu Picchu / Cusco)
            Destination("Perú", "Cusco", 450.00, "https://images.unsplash.com/photo-1526392060635-9d6019884377?w=500"),

            Destination("Francia", "París", 1200.50, "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?w=500"),
            Destination("Japón", "Tokio", 1500.00, "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=500"),
            Destination("Italia", "Roma", 980.00, "https://images.unsplash.com/photo-1552832230-c0197dd311b5?w=500"),

            // Nueva URL para México (Playa / Cancún)
            Destination("México", "Cancún", 650.20, "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=500")
        )
    }

    // Cálculos para el final del listado
    val totalDestinos = listaDestinos.size
    val sumaCostos = listaDestinos.sumOf { it.costoPromedio }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Catálogo de Destinos Turísticos",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 3. REQUISITO: Utilizar LazyColumn para el listado eficiente
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listaDestinos) { destino ->
                    // 4. REQUISITO: Cada destino en un Card utilizando Row, Column y AsyncImage
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Carga de imagen mediante Coil AsyncImage
                            AsyncImage(
                                model = destino.imageUrl,
                                contentDescription = "Imagen de ${destino.ciudad}",
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // Organización de los textos requeridos
                            Column {
                                Text(text = "País: ${destino.pais}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Ciudad: ${destino.ciudad}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Costo promedio: $${String.format("%.2f", destino.costoPromedio)}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }

                // 5. REQUISITO: Al final del listado mostrar cantidad total y suma acumulada
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Resumen del Catálogo",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(text = "Cantidad total de destinos: $totalDestinos", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Suma acumulada de costos: $${String.format("%.2f", sumaCostos)}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Botón obligatorio para regresar al menú
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
fun PermisoUbicacionScreen(volver: () -> Unit) {
    val context = LocalContext.current

    // Estado inicial requerido por la rúbrica: "PENDIENTE"
    var estadoPermiso by remember { mutableStateOf("PENDIENTE") }

    // Función auxiliar para comprobar si el permiso ya fue concedido previamente
    fun comprobarEstadoActual(): String {
        val fineLoc = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLoc = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        return if (fineLoc == PackageManager.PERMISSION_GRANTED || coarseLoc == PackageManager.PERMISSION_GRANTED) {
            "CONCEDIDO"
        } else {
            "PENDIENTE"
        }
    }

    // Inicializamos el estado al cargar la pantalla por si ya se aceptó antes
    remember {
        val actual = comprobarEstadoActual()
        if (actual == "CONCEDIDO") estadoPermiso = "CONCEDIDO"
        actual
    }

    // REQUISITO: Implementar permisos mediante Activity Result API y rememberLauncherForActivityResult
    val launcherPermisos = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        val concedido = permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        // Cambia dinámicamente el estado según la respuesta del usuario
        estadoPermiso = if (concedido) "CONCEDIDO" else "DENEGADO"
    }

    Scaffold { paddingValues ->
        // REQUISITO: Utilizar Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // REQUISITO: Utilizar Text para el título
            Text(
                text = "Permiso de Ubicación para Asistencia de Viaje",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // REQUISITO: Mostrar exactamente los 3 estados solicitados
            val textoEstado = when (estadoPermiso) {
                "CONCEDIDO" -> "Permiso concedido"
                "DENEGADO" -> "Permiso denegado"
                else -> "Permiso pendiente de solicitud"
            }

            // Cambiamos el color según el estado para que se vea genial
            val colorEstado = when (estadoPermiso) {
                "CONCEDIDO" -> MaterialTheme.colorScheme.primary
                "DENEGADO" -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            }

            Text(
                text = textoEstado,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = colorEstado
            )

            Spacer(modifier = Modifier.height(24.dp))

            // REQUISITO: Utilizar Button para solicitar el permiso
            Button(
                onClick = {
                    // Lanza la solicitud de ambos permisos en lote
                    launcherPermisos.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Solicitar Permisos")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón obligatorio para regresar al menú
            Button(
                onClick = volver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al menú principal")
            }
        }
    }
}