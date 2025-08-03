package com.example.passgen.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.passgen.ui.theme.PassGenTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen()
{
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Словари и пароли")},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray)
            )
                 },
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Hello World!",
        )
    }
}
