package com.example.passgen.presentation.passwordlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListScreen(
    viewModel: PasswordListViewModel
)
{
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {Text(text = "Словари и пароли")},
                navigationIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Lock",
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                        )
                },
                actions = {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Lock",
                        )
                    }
                }
                ) },floatingActionButton = { IconButton(onClick = {}) { } }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(state.passwords) { password ->
                Card(Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(text = password.value)
                        password.entropy?.let {
                            Text("Энтропия: ${"%.2f".format(it)}")
                        }
                        Row {
                            IconButton(onClick = { viewModel.copy(password) }) {
                                Icon(Icons.Default.Share, contentDescription = "Copy")
                            }
                            IconButton(onClick = { viewModel.delete(password) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
