package com.example.passgen.presentation.generator

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
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

    var customSymbolsText by remember { mutableStateOf(TextFieldValue("")) }
    val saveCompleted by viewModel.saveCompleted.collectAsState()


    LaunchedEffect(saveCompleted) {
        if (saveCompleted) {

            navController.previousBackStackEntry?.savedStateHandle?.set("refresh", true)

            navController.navigate("passwordlist") {
                popUpTo("generator") { inclusive = true }
            }
            viewModel.resetSaveCompleted()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Сгенерировать пароль") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { filePicker.launch(arrayOf("text/plain")) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Загрузить пароли из файла")
            }

            Text("Длина пароля: ${viewModel.length}")
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

            OutlinedTextField(
                value = customSymbolsText,
                onValueChange = {
                    customSymbolsText = it
                    viewModel.customSymbols = it.text
                },
                label = { Text("Свои символы (необязательно)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Button(
                onClick = { viewModel.generate() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сгенерировать")
            }

            if (password.isNotEmpty()) {
                Text("Пароль: $password", style = MaterialTheme.typography.bodyLarge)
                Text("Энтропия: ${"%.2f".format(entropy)}", style = MaterialTheme.typography.bodySmall)

                Button(
                    onClick = { viewModel.savePassword() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}
