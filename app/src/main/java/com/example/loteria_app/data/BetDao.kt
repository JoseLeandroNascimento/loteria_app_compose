package com.example.loteria_app.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BetDao {

    @Insert
    fun insert(bet: Bet)

}