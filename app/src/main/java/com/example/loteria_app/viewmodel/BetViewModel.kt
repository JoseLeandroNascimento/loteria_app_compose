package com.example.loteria_app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.loteria_app.App
import com.example.loteria_app.data.Bet
import com.example.loteria_app.data.BetRepository
import kotlinx.coroutines.launch
import java.util.Random

class BetViewModel(
    private val betRepository: BetRepository
) : ViewModel() {

    var qtdNumbers by mutableStateOf("")
        private set

    var qtdBets by mutableStateOf("")
        private set

    private var _result = MutableLiveData("")
    val result: LiveData<String>
        get() = _result

    val numbers = mutableListOf<String>()

    private var _showAlert = MutableLiveData(false)
    val showAlert: LiveData<Boolean>
        get() = _showAlert

    fun updateQtdNumbers(newNumber: String) {
        if (newNumber.length < 3) {
            qtdNumbers = validateInput(newNumber)
        }
    }

    fun updateQtdBets(newBet: String) {
        if (newBet.length < 3) {
            qtdBets = validateInput(newBet)
        }
    }

    fun updateNumbers(rule: Int) {
        _result.value = ""
        numbers.clear()

        for (i in 1..qtdBets.toInt()) {
            val res = numberGenerator(qtdNumbers, rule)
            numbers.add(res)
            _result.value += "[$i] "
            _result.value += res
            _result.value += "\n\n"
        }

        _showAlert.value = true
    }

    private fun validateInput(value: String): String {

        return value.filter {
            it in "0123456789"
        }
    }

    fun saveBet(type: String) {

        viewModelScope.launch {
            for (num in numbers) {
                val bet = Bet(type = type, numbers = num)
                betRepository.insertBet(bet)
                dismissAlert()
            }
        }
    }

    fun dismissAlert() {
        _showAlert.value = false
    }


    private fun numberGenerator(qtd: String, rule: Int): String {
        val numbers = mutableSetOf<Int>()

        while (true) {
            val n = Random().nextInt(60)

            if (rule == 1) {
                if (n % 2 == 0) continue
            } else if (rule == 2) {
                if (n % 2 != 0) continue
            }

            numbers.add(n + 1)

            if (numbers.size == qtd.toInt()) break

        }

        return numbers.joinToString(" - ")
    }


    @Suppress("UNCHECKED_CAST")
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application = extras[APPLICATION_KEY] as App
                val dao = application.db.betDao()
                val betRepository = BetRepository.getInstance(dao)

                return BetViewModel(betRepository) as T
            }

        }

    }
}