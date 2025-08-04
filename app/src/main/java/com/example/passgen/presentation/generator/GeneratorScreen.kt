package com.example.passgen.presentation.generator

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratorScreen(viewModel: GeneratorScreenViewModel, navController: NavController) {
    val password = viewModel.generatedPassword
    val entropy = viewModel.entropy

    val context = LocalContext.current
    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let { viewModel.importPasswords(context, it) }
        }
    )

    Scaffold(
        topBar = { TopAppBar(title = {Text("Сгенерировать пароль")}, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        })}
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(15.dp)
        ) {
            Button(onClick = { filePicker.launch(arrayOf("text/plain")) }) {
                Text("Загрузить из файла")
            }
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text("Длина: ${viewModel.length}")
            Slider(
                value = viewModel.length.toFloat(),
                onValueChange = { viewModel.length = it.toInt() },
                valueRange = 6f..32f
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = viewModel.useLetters, onCheckedChange = { viewModel.useLetters = it })
                Text("Буквы")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = viewModel.useDigits, onCheckedChange = { viewModel.useDigits = it })
                Text("Цифры")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = viewModel.useSymbols, onCheckedChange = { viewModel.useSymbols = it })
                Text("Символы")
            }

            Button(
                onClick = { viewModel.generate() },
                modifier = Modifier.padding(vertical = 8.dp),
            ) {
                Text("Сгенерировать")
            }

            Text("Пароль: $password")
            Text("Энтропия: ${"%.2f".format(entropy)}")

            Button(enabled = password.isNotEmpty(), onClick = { viewModel.savePassword() }) {
                Text("Сохранить")
            }
        }
    }


}