package com.example.loteria_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.loteria_app.compose.LoteriaApp
import com.example.loteria_app.data.AppDatabase
import com.example.loteria_app.data.Bet
import com.example.loteria_app.ui.theme.LoteriaappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getInstance(this)

        val bet = Bet(
            type = "mega",
            numbers = "1,2,3,4,5,6"
        )
        Thread{

            db.betDao().insert(bet)
        }.start()

        setContent {
            LoteriaappTheme {
                LoteriaApp()
            }
        }
    }
}






