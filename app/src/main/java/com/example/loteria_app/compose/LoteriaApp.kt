package com.example.loteria_app.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loteria_app.compose.MegaSena.MegaScreen
import com.example.loteria_app.compose.home.HomeScreen
import com.example.loteria_app.compose.quina.QuinaScreen

@Composable
fun LoteriaApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    LoteriaNavHost(navController = navController)
}

enum class AppRouter(val route: String) {

    HOME("home"),
    QUINA("quina"),
    MEGA_SENA("mega_sena")
}


@Composable
fun LoteriaNavHost(modifier: Modifier = Modifier, navController: NavHostController) {

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = AppRouter.HOME.route
        ) {
            composable(AppRouter.HOME.route) {
                HomeScreen(onClick = {
                    val route = when (it.id) {
                        1 -> AppRouter.MEGA_SENA

                        2 -> AppRouter.QUINA

                        else -> AppRouter.HOME

                    }

                    navController.navigate(route.route)
                })
            }

            composable(AppRouter.MEGA_SENA.route) {
                MegaScreen()
            }

            composable(AppRouter.QUINA.route) {
                QuinaScreen()
            }
        }
    }
}