package com.example.loteria_app.compose.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loteria_app.R
import com.example.loteria_app.viewmodel.BetDetailsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BetsListScreen(
    betViewModel: BetDetailsViewModel = viewModel(factory = BetDetailsViewModel.Factory)
) {

    val bets = betViewModel.bets.collectAsState().value
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme
            .colorScheme.background
    ) {

        Scaffold(
        ) {

            LazyColumn(modifier = Modifier.padding(it)) {

                itemsIndexed(bets) { index, bet ->
                    Text(
                        text = stringResource(
                            id = R.string.list_response, index,
                            sdf.format(bet.date),
                            bet.numbers
                        ),
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)
                    )
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun BetsListScreenPreview() {
    BetsListScreen()
}