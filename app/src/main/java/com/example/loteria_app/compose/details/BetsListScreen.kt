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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loteria_app.App
import com.example.loteria_app.R
import com.example.loteria_app.data.Bet
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BetsListScreen(type: String) {

    val bets = remember { mutableListOf<Bet>() }
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))

    val db = (LocalContext
        .current.applicationContext as App).db

    Thread {
        val res = db.betDao().getNumbersByType(type)
        bets.clear()
        bets.addAll(res)

    }.start()

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
    BetsListScreen(type = "megasena")
}