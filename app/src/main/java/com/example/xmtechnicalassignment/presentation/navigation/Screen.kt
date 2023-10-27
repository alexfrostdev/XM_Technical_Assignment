package com.example.xmtechnicalassignment.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen(route = "home")
    object Questions : Screen(route = "questions")
}
