package com.example.passgen.presentation.passwordlist

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListScreen(
    viewModel: PasswordListViewModel
) {
    val passwords by viewModel.passwords.collectAsState()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) { uri ->
        uri?.let {
            viewModel.exportPasswords(context, uri)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val grouped = passwords.groupBy { it.filePath ?: "Сгенерированные" }

        grouped.forEach { (group, passwordsInGroup) ->
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = group,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        viewModel.setCurrentGroup(group)
                        launcher.launch("passwords_export.txt")
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Выгрузить")
                    }
                }
            }

            items(passwordsInGroup) { password ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Пароль: ${password.value}")
                        Text("Энтропия: ${"%.2f".format(password.entropy)}")
                        Text("Набор: ${password.charset}")

                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            IconButton(onClick = {
                                viewModel.copyToClipboard(context, password.value)
                            }) {
                                Icon(Icons.Default.Lock, contentDescription = "Копировать")
                            }

                            IconButton(onClick = {
                                viewModel.deletePassword(password)
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Удалить")
                            }
                        }
                    }
                }
            }
        }
    }
}
