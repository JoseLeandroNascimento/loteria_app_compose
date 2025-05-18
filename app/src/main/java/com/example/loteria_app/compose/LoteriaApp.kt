package com.example.loteria_app.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.loteria_app.compose.MegaSena.MegaScreen
import com.example.loteria_app.compose.details.BetsListScreen
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
    MEGA_SENA("mega_sena"),
    BET_LIST_DETAILS("bet_list_details")
}


@Composable
fun LoteriaNavHost(modifier: Modifier = Modifier, navController: NavHostController) {


    NavHost(
        modifier = modifier,
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

        composable(
            route = AppRouter.BET_LIST_DETAILS.route + "/{type}",
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                }
            )
        ) {
            val type = it.arguments?.getString("type") ?: throw Exception("Tipo não encontrado")

            BetsListScreen()

        }

        composable(AppRouter.MEGA_SENA.route) {
            MegaScreen(
                onBackClick = { navController.navigateUp() },
                onClick = {
                    navController.navigate((AppRouter.BET_LIST_DETAILS.route + "/$it"))
                }
            )
        }

        composable(AppRouter.QUINA.route) {
            QuinaScreen()
        }
    }
}