package com.example.loteria_app.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.loteria_app.App
import com.example.loteria_app.data.Bet
import com.example.loteria_app.data.BetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BetDetailsViewModel(
    private val savedStateHandle:SavedStateHandle,
    private val betRepository: BetRepository
) : ViewModel() {

    private val _bets = MutableStateFlow<List<Bet>>(emptyList())
    val bets = _bets.asStateFlow()

    init {
        viewModelScope.launch {
            val type = savedStateHandle.get<String>("type")
            val result = betRepository.getBets(type!!)
            _bets.value = result
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application = extras[APPLICATION_KEY] as App
                val dao = application.db.betDao()
                val betRepository = BetRepository.getInstance(dao)

                val savedStateHandle = extras.createSavedStateHandle()
                return BetDetailsViewModel(savedStateHandle, betRepository) as T
            }

        }

    }

}