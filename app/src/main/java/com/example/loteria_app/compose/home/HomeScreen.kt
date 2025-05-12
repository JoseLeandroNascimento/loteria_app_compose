package com.example.loteria_app.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.loteria_app.R
import com.example.loteria_app.components.LoItemType
import com.example.loteria_app.model.MainItem
import com.example.loteria_app.ui.theme.Blue
import com.example.loteria_app.ui.theme.Green

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onClick: (item: MainItem) -> Unit) {

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
                LotteryItem(item = it) {
                    onClick(it)
                }
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
