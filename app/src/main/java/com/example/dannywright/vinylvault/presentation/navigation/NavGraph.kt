package com.example.dannywright.vinylvault.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dannywright.vinylvault.presentation.collection.CollectionScreen
import com.example.dannywright.vinylvault.presentation.detail.DetailScreen
import com.example.dannywright.vinylvault.presentation.search.SearchScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Collection.route
    ) {
        composable(route = Screen.Collection.route) {
            CollectionScreen(
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                },
                onNavigateToDetail = { releaseId ->
                    navController.navigate(Screen.Detail.createRoute(releaseId))
                }
            )
        }

        composable(route = Screen.Search.route) {
            SearchScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { releaseId ->
                    navController.navigate(Screen.Detail.createRoute(releaseId))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("releaseId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val releaseId = backStackEntry.arguments?.getInt("releaseId") ?: return@composable
            DetailScreen(
                releaseId = releaseId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}