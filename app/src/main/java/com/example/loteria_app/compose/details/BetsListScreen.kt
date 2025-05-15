package com.example.loteria_app.compose.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BetsListScreen(modifier: Modifier = Modifier, type: String) {

    Text("Tipo de dados: $type")
}

@Preview(showBackground = true)
@Composable
private fun BetsListScreenPreview() {
    BetsListScreen(type = "megasena")
}