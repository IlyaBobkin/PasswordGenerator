package com.example.passgen.navigation

sealed class Screen(val route: String) {
    object Loading : Screen("loading")
    object Generator : Screen("generator")
    object PasswordList : Screen("passwordlist")
}