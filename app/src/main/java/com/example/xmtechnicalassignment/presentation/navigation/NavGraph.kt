package com.example.xmtechnicalassignment.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xmtechnicalassignment.presentation.ui.main.MainScreen
import com.example.xmtechnicalassignment.presentation.ui.questions.QuestionsScreen

/**
 * Navigation Graph to control the navigation.
 *
 * @param startDestination the start destination of the graph
 */
@Composable
fun NavGraph(startDestination: String = "home") {
    val navController = rememberNavController()
    val context = LocalContext.current

    val actions = remember(navController) { Actions(navController, context) }

    NavHost(navController = navController, startDestination = startDestination) {

        composable("home") {
            MainScreen(onOpenQuestions = actions.openQuestions)
        }

        composable("questions") {
            QuestionsScreen(
                onUpPress = actions.popBackStack,
            )
        }
    }
}

internal class Actions(
    private val navController: NavHostController,
    private val context: Context
) {

    val openQuestions: () -> Unit = {
        navController.navigate("questions")
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }

    val popBackStack: () -> Unit = {
        navController.popBackStack()
    }

}
