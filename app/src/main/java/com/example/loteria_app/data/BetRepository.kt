package com.example.loteria_app.data

class BetRepository(private val dao: BetDao) {

    companion object {

        private var instance: BetRepository? = null

        fun getInstance(betDao: BetDao): BetRepository {
            instance ?: BetRepository(betDao).also {
                instance = it
            }

            return instance!!
        }
    }

    suspend fun getBets(type: String): List<Bet> {
        return dao.getNumbersByType(type)
    }

    suspend fun insertBet(bet: Bet) {
        dao.insert(bet)
    }

}