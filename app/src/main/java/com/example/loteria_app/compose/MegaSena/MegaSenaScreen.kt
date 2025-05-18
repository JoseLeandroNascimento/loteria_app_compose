package com.example.loteria_app.compose.MegaSena

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loteria_app.App
import com.example.loteria_app.R
import com.example.loteria_app.components.AutoTextDropDown
import com.example.loteria_app.components.LoItemType
import com.example.loteria_app.components.LoNumberTextField
import com.example.loteria_app.viewmodel.BetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onClick: (String) -> Unit,
    betViewModel: BetViewModel = viewModel(factory = BetViewModel.Factory)
) {

    val db = (LocalContext.current.applicationContext as App).db

    val qtdNumbers = betViewModel.qtdNumbers
    val qtdBets = betViewModel.qtdBets

    val result = betViewModel.result.observeAsState("").value

    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val scope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    val showAlertDialog = betViewModel.showAlert.observeAsState(false).value

    val rules = stringArrayResource(id = R.array.array_bet_rules)
    var selectItem by remember { mutableStateOf(rules.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Apostar")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onClick("megasena")
                        }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "")
                    }
                }
            )
        }
    ) { innerPadding ->

        Surface(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {


            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(state = scrollState)
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
                        betViewModel.updateQtdNumbers(it)
                    },
                    placeholder = R.string.quantity
                )

                LoNumberTextField(
                    value = qtdBets,
                    label = R.string.bets,
                    imeAction = ImeAction.Done,
                    onValueChange = {
                        betViewModel.updateQtdBets(it)
                    },
                    placeholder = R.string.bets_quantity
                )

                Column(modifier.width(280.dp)) {

                    AutoTextDropDown(
                        label = stringResource(id = R.string.bet_rule),
                        value = selectItem,
                        list = rules.toList(),
                        onSelect = {}
                    )
                }


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
                            val rule = rules.indexOf(selectItem)
                            betViewModel.updateNumbers(rule)

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
                        betViewModel.dismissAlert()
                    },
                    title = { Text(text = stringResource(R.string.app_name)) },
                    text = {
                        Text(text = stringResource(R.string.good_luck))
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            betViewModel.dismissAlert()

                        }) {
                            Text(text = stringResource(id = android.R.string.ok))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                          betViewModel.saveBet("megasena")
                        }) {
                            Text(text = stringResource(id = R.string.save))
                        }
                    }
                )
            }
        }


    }
}

