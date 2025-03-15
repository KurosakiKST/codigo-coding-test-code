package com.ryan.codigo2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ryan.codigo2.presentation.detail.DetailScreen
import com.ryan.codigo2.presentation.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToDetail = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
        ) { entry ->
            val movieId = entry.arguments?.getInt("movieId") ?: -1
            DetailScreen(
                movieId = movieId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
}