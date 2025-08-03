package com.example.passgen.presentation.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen()
{
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
                }
                ) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("d")
            Text("f")
            Text("d")
            Text("f")
            Text("d")
            Text("f")
            Text("d")
            Text("f")
            Text("d")
            Text("f")
            Text("d")
            Text("f")
        }
    }
}
