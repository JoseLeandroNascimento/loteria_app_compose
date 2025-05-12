package com.example.loteria_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loteria_app.components.LoItemType
import com.example.loteria_app.components.LoNumberTextField
import com.example.loteria_app.model.MainItem
import com.example.loteria_app.ui.theme.Blue
import com.example.loteria_app.ui.theme.Green
import com.example.loteria_app.ui.theme.LoteriaappTheme
import kotlinx.coroutines.launch
import java.util.Random

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

    val mainItems = mutableListOf(
        MainItem(id = 1, "Mega Sena", Green, R.drawable.trevo),
        MainItem(id = 2, "Quina", Blue, R.drawable.trevo),
    )
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            items(items = mainItems) {
                LotteryItem(item = it, onClick = onClick)
            }
        }


    }

}

@Composable
fun LotteryItem(
    modifier: Modifier = Modifier,
    item: MainItem,
    onClick: () -> Unit
) {

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
                .background(item.color)
                .padding(8.dp)
        ) {

            LoItemType(
                name = item.name,
                color = Color.White,
                bgColor = item.color,
                icon = item.icon
            )
        }
    }

}

@Composable
fun FormScreen(modifier: Modifier = Modifier) {

    var qtdNumbers by remember { mutableStateOf("") }
    var qtdBets by remember { mutableStateOf("") }
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val scope = rememberCoroutineScope()
    var result by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    var showAlertDialog by remember { mutableStateOf(false) }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(state = scrollState)
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


            OutlinedButton(
                enabled = qtdNumbers.isNotEmpty() && qtdBets.isNotEmpty(),
                onClick = {

                    val bets = qtdBets.toInt()
                    val numbers = qtdNumbers.toInt()

                    if (bets < 1 || qtdBets.toInt() > 10) {

                        scope.launch {
                            snackBarHostState.showSnackbar("Máximo número de apostas permitidos: 10")
                        }
                    } else if (numbers.toInt() < 1 || numbers.toInt() > 15) {
                        scope.launch {
                            snackBarHostState.showSnackbar("Números devem ser entre 1 e 15")
                        }
                    } else {
                        result = ""
                        for (i in 1..bets) {
                            result += "[$i] "
                            result += numberGenerator(qtdNumbers)
                            result += "\n\n"
                        }

                        showAlertDialog = true

                    }

                    keyboardController?.hide()

                }

            ) {
                Text(text = stringResource(id = R.string.bets_generates))
            }

            Text(text = result)


        }
        Box {
            SnackbarHost(
                modifier = Modifier.align(Alignment.BottomEnd),
                hostState = snackBarHostState
            )
        }

        if (showAlertDialog) {

            AlertDialog(
                onDismissRequest = {
                    showAlertDialog = false
                },
                title = { Text(text = stringResource(R.string.app_name)) },
                text = {
                    Text(text = stringResource(R.string.good_luck))
                },
                confirmButton = {
                    TextButton(onClick = {
                        showAlertDialog = false
                    }) {
                        Text(text = stringResource(id = android.R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showAlertDialog = false
                    }) {
                        Text(text = stringResource(id = android.R.string.cancel))
                    }
                }
            )
        }

    }

}

private fun numberGenerator(qtd: String): String {
    val numbers = mutableSetOf<Int>()

    while (true) {
        val n = Random().nextInt(60)
        numbers.add(n + 1)

        if (numbers.size == qtd.toInt()) break

    }

    return numbers.joinToString(" - ")
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