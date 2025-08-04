package com.example.passgen.presentation.passwordlist

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListScreen(
    viewModel: PasswordListViewModel,
    onNavigateToGenerator: () -> Unit
) {
    val passwords by viewModel.passwords.collectAsState()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) { uri ->
        uri?.let {
            viewModel.exportPasswords(context, uri)
        }
    }


    val expandedGroups = remember { mutableStateOf(setOf<String>()) }
    val generatedPasswords = passwords.filter { it.filePath.isNullOrBlank() }
    val groupedPasswords = passwords.filter { !it.filePath.isNullOrBlank() }.groupBy { it.filePath!! }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Ваши пароли") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToGenerator) {
                Icon(Icons.Default.Add, contentDescription = "Сгенерировать пароль")
            }
        }
    ) { paddingValues ->
        if (passwords.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Нет сохранённых паролей")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(8.dp)
                    .padding(6.dp)
            ) {
                // Папки
                groupedPasswords.forEach { (group, passwordsInGroup) ->
                    val isExpanded = expandedGroups.value.contains(group)
                    item{
                        Text("Папки:", fontSize = 20.sp)
                        Spacer(modifier = Modifier.padding(top = 15.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                                .clickable {
                                    expandedGroups.value = if (isExpanded) {
                                        expandedGroups.value - group
                                    } else {
                                        expandedGroups.value + group
                                    }
                                }                                .padding(vertical = 8.dp)
                            ,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isExpanded) "▼ $group" else "▶ $group",
                                fontSize = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(Icons.Default.Folder, contentDescription = "Папка")

                            IconButton(onClick = {
                                viewModel.setCurrentGroup(group)
                                launcher.launch("passwords_export.txt")
                            }) {
                                Icon(Icons.Default.Share, contentDescription = "Выгрузить")
                            }
                        }
                    }

                    if (isExpanded) {
                        items(passwordsInGroup) { password ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp, vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("Пароль: ${password.value}", modifier = Modifier.weight(1f))
                                        Row {
                                            IconButton(onClick = {
                                                viewModel.copyToClipboard(context, password.value)
                                            }) {
                                                Icon(Icons.Default.ContentCopy, contentDescription = "Копировать")
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

                // Сгенерированные пароли
                if (generatedPasswords.isNotEmpty()) {
                    item{
                        Spacer(modifier = Modifier.padding(top = 15.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Сгенерированные пароли:",
                                fontSize = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {
                                viewModel.setCurrentGroup("__generated__")
                                launcher.launch("generated_passwords_export.txt")
                            }) {
                                Icon(Icons.Default.Share, contentDescription = "Выгрузить")
                            }
                        }
                    }
                    items(generatedPasswords) { password ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp, vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Пароль: ${password.value}", modifier = Modifier.weight(1f))
                                    Row {
                                        IconButton(onClick = {
                                            viewModel.copyToClipboard(context, password.value)
                                        }) {
                                            Icon(Icons.Default.ContentCopy, contentDescription = "Копировать")
                                        }
                                        IconButton(onClick = {
                                            viewModel.deletePassword(password)
                                        }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Удалить")
                                        }
                                    }
                                }

                                Text("Энтропия: ${"%.2f".format(password.entropy)}", fontSize = 14.sp)
                                Text("Набор символов: ${password.charset}", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}


