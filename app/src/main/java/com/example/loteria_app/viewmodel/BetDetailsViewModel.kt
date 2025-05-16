package com.example.loteria_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loteria_app.data.Bet
import com.example.loteria_app.data.BetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BetDetailsViewModel(
    private val type: String,
    private val betRepository: BetRepository
) : ViewModel() {

    private val _bets = MutableStateFlow<List<Bet>>(emptyList())
    val bets = _bets.asStateFlow()

    init {
        viewModelScope.launch {
            val result = betRepository.getBets(type)
            _bets.value = result
        }
    }

}