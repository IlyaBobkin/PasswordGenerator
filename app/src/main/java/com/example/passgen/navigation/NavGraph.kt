package com.example.passgen.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.passgen.presentation.generator.GeneratorScreen
import com.example.passgen.presentation.loading.LoadingScreen
import com.example.passgen.presentation.passwordlist.PasswordListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Loading.route) {
        composable(Screen.Loading.route) {
            LoadingScreen(
                viewModel = hiltViewModel(),
                onLoaded = { navController.navigate(Screen.PasswordList.route) }
            )
        }
        composable(Screen.Generator.route) {
            GeneratorScreen(viewModel = hiltViewModel(), navController)
        }
        composable(Screen.PasswordList.route) {
            PasswordListScreen(viewModel = hiltViewModel(), onNavigateToGenerator = {navController.navigate(Screen.Generator.route)})
        }
    }
}