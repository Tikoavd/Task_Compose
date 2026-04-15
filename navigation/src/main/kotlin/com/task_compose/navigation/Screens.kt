package com.task_compose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.task_compose.home.presentation.screen.HomeRoute
import com.task_compose.screens.Screens

fun NavGraphBuilder.homeScreen(
) {
    composable<Screens.Home> {
        HomeRoute()
    }
}
