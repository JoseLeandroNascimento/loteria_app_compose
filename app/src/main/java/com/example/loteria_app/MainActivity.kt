package com.example.loteria_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.loteria_app.components.AutoTextDropDown
import com.example.loteria_app.ui.theme.LoteriaappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LoteriaappTheme {
//                LoteriaApp()

                var select by remember { mutableStateOf("Teste 1") }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Item selecionado $select")

                    AutoTextDropDown(
                        modifier = Modifier.width(400.dp),
                        list = listOf("Teste 1", "Ola mundo", "Teste 3"),
                        label = "Teste",
                        value = select,
                        onSelect = {select = it}
                    )
                }
            }
        }
    }
}






