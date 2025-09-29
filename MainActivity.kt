package com.example.ejercicioprop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ejercicioprop.ui.theme.EJERCICIOPROPTheme
import java.net.URLEncoder
import java.net.URLDecoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EJERCICIOPROPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavegacionApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NavegacionApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "pantalla1",
        modifier = modifier
    ) {
        composable("pantalla1") {
            Pantalla1(navController)
        }
        composable("pantalla2/{nombre}/{codigo}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
            Pantalla2(navController, nombre, codigo)
        }
    }
}

@Composable
fun Pantalla1(navController: NavHostController) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var codigo by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Bienvenido", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { input ->
                nombre = input.filterNot { it == '/' || it == '\\' }
            },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = codigo,
            onValueChange = { input ->
                val filtered = input.filter { it.isDigit() }
                if (filtered.length <= 8) codigo = filtered
            },
            label = { Text("Código (8 dígitos)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (codigo.length == 8 && nombre.isNotBlank()) {
                    val nombreEnc = URLEncoder.encode(nombre, "UTF-8")
                    val codigoEnc = URLEncoder.encode(codigo, "UTF-8")
                    navController.navigate("pantalla2/$nombreEnc/$codigoEnc")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Siguiente")
        }
    }
}

@Composable
fun Pantalla2(navController: NavController, nombreEncoded: String, codigoEncoded: String) {
    val nombre = try { URLDecoder.decode(nombreEncoded, "UTF-8") } catch (_: Exception) { nombreEncoded }
    val codigo = try { URLDecoder.decode(codigoEncoded, "UTF-8") } catch (_: Exception) { codigoEncoded }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Hola, $nombre", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Tu código es: $codigo")
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack("pantalla1", inclusive = false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Pantalla1Preview() {
    EJERCICIOPROPTheme {
        Pantalla1(rememberNavController())
    }
}
