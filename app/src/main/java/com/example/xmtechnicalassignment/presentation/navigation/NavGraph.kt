package com.example.xmtechnicalassignment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.xmtechnicalassignment.presentation.ui.main.addHome
import com.example.xmtechnicalassignment.presentation.ui.questions.addQuestions

/**
 * Navigation Graph to control the navigation.
 *
 * @param startDestination the start destination of the graph
 */
@Composable
fun NavGraph(startDestination: String = Screen.Home.route) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val actions = remember(navController) { Actions(navController) }

    NavHost(navController = navController, startDestination = startDestination) {
        addHome(actions)
        addQuestions(actions)
    }
}

class Actions(
    private val navController: NavHostController,
) {

    val openQuestions: () -> Unit = {
        navController.navigate(Screen.Questions.route)
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }

    val popBackStack: () -> Unit = {
        navController.popBackStack()
    }

}
