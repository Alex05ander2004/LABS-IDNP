package com.example.lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab5.ui.theme.LAB5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LAB5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FormularioApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FormularioApp(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("Masculino") }
    var notificationsEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nombre:")
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Ingrese su nombre") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Contraseña:")
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Ingrese su contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Acepto los términos y condiciones")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Text("Género:")
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedGender == "Masculino",
                    onClick = { selectedGender = "Masculino" }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Masculino")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedGender == "Femenino",
                    onClick = { selectedGender = "Femenino" }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Femenino")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Habilitar notificaciones")
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                println("Formulario Enviado")
                println("Nombre: $name")
                println("Contraseña: $password")
                println("Términos aceptados: $isChecked")
                println("Género: $selectedGender")
                println("Notificaciones habilitadas: $notificationsEnabled")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioPreview() {
    LAB5Theme {
        FormularioApp()
    }
}
