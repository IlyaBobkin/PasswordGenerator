package com.example.passgen.presentation.generator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GeneratorScreen(viewModel: GeneratorScreenViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Опции генерации
        Row { /* чекбоксы */ }
        //Slider(value = ..., onValueChange = { ... })

        Button(onClick = { viewModel.generate() }) {
            Text("Сгенерировать")
        }

        Text("Пароль: ${uiState.password}")
        Text("Энтропия: ${uiState.entropy}")

        Button(onClick = { viewModel.savePassword() }) {
            Text("Сохранить")
        }
    }
}