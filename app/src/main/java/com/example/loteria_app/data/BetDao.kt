package com.example.loteria_app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BetDao {

    @Insert
    suspend fun insert(bet: Bet)

    @Query("SELECT * FROM bets WHERE type = :betType")
    suspend fun getNumbersByType(betType:String):List<Bet>

}