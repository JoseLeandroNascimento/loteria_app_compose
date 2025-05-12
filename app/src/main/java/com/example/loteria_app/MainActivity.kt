package com.example.loteria_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.loteria_app.compose.LoteriaApp
import com.example.loteria_app.ui.theme.LoteriaappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaappTheme {
                LoteriaApp()
            }
        }
    }
}






