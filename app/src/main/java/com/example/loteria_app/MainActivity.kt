package com.example.loteria_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loteria_app.components.LoItemType
import com.example.loteria_app.components.LoNumberTextField
import com.example.loteria_app.ui.theme.Green
import com.example.loteria_app.ui.theme.LoteriaappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()

                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = AppRouter.HOME.route
                    ) {
                        composable(AppRouter.HOME.route) {
                            HomeScreen(onClick = {
                                navController.navigate(AppRouter.FORM.route)
                            })
                        }

                        composable(AppRouter.FORM.route) {
                            FormScreen()
                        }
                    }
                }
            }
        }
    }
}

enum class AppRouter(val route: String) {

    HOME("home"),
    FORM("form")
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onClick: () -> Unit) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        LotteryItem(name = "Mega Sena", onClick = onClick)

    }

}

@Composable
fun LotteryItem(modifier: Modifier = Modifier, name: String, onClick: () -> Unit) {

    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .wrapContentSize()
            .clickable {
                onClick()
            }

    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(Green)
                .padding(8.dp)
        ) {

            LoItemType(
                name = name,
                color = Color.White
            )
        }
    }

}

@Composable
fun FormScreen(modifier: Modifier = Modifier) {

    var qtdNumbers by remember { mutableStateOf("") }
    var qtdBets by remember { mutableStateOf("") }


    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoItemType(name = "Mega Sena")

            Text(
                text = stringResource(id = R.string.annoucement),
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(8.dp)
            )

            LoNumberTextField(
                value = qtdNumbers,
                label = R.string.mega_rule,
                onValueChange = {
                    if (it.length < 3) {
                        qtdNumbers = validateInput(it)
                    }
                },
                placeholder = R.string.quantity
            )

            LoNumberTextField(
                value = qtdBets,
                label = R.string.bets,
                imeAction = ImeAction.Done,
                onValueChange = {
                    if (it.length < 3) {
                        qtdBets = validateInput(it)
                    }
                },
                placeholder = R.string.bets_quantity
            )


            OutlinedButton(onClick = {}) {
                Text(text = stringResource(id = R.string.bets_generates))
            }

        }
    }

}

private fun validateInput(value: String): String {

    return value.filter {
        it in "0123456789"
    }
}

@Preview
@Composable
private fun FormScreenPreview() {
    FormScreen()
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(onClick = {})
}