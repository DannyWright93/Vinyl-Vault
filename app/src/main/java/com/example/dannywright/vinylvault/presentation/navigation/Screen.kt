package com.example.dannywright.vinylvault.presentation.navigation

sealed class Screen(val route: String) {
    object Collection : Screen("collection")
    object Search : Screen("search")
    object Detail : Screen("detail/{releaseId}") {
        fun createRoute(releaseId: Int) = "detail/$releaseId"
    }
}