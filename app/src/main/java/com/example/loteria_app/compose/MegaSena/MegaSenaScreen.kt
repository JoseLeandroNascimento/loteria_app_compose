package com.example.loteria_app.compose.MegaSena

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.loteria_app.App
import com.example.loteria_app.R
import com.example.loteria_app.components.LoItemType
import com.example.loteria_app.components.LoNumberTextField
import com.example.loteria_app.data.Bet
import kotlinx.coroutines.launch
import java.util.Random

@Composable
fun MegaScreen(modifier: Modifier = Modifier) {

    val db = (LocalContext.current.applicationContext as App).db

    var qtdNumbers by remember { mutableStateOf("") }
    var qtdBets by remember { mutableStateOf("") }
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val scope = rememberCoroutineScope()
    var result by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    var showAlertDialog by remember { mutableStateOf(false) }

    val resultToSave = mutableListOf<String>()


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
                        resultToSave.clear()

                        for (i in 1..bets) {
                            val res = numberGenerator(qtdNumbers)
                            resultToSave.add(res)
                            result += "[$i] "
                            result += res
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
                        Thread {
                            for (res in resultToSave) {
                                val bet = Bet(type = "megasena", numbers = res)
                                db.betDao().insert(bet)
                            }
                        }.start()

                        showAlertDialog = false
                    }) {
                        Text(text = stringResource(id = R.string.save))
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