package com.devmarcus.dicegame

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class DiceUiState(
    @DrawableRes val rolledDiceValue1: Int? = null,
    @DrawableRes val rolledDiceValue2: Int? = null,
    @DrawableRes val rolledDiceValue3: Int? = null,
    val numberOfRolls: Int = 0,
    val rolledDiceList: List<RolledDices> = emptyList<RolledDices>()
)

class DiceViewModel: ViewModel(){
    private val _uiState = MutableStateFlow(DiceUiState())
    public val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    private val _uiStateLiveData = MutableLiveData<DiceUiState>()
    public val uiStateLiveData = _uiStateLiveData


    fun rollDice(){
        val rd1 = Random.nextInt(from = 1, until = 7)
        val rd2 = Random.nextInt(from = 1, until = 7)
        val rd3 = Random.nextInt(from = 1, until = 7)

        val _rolledDices = RolledDices(rd1, rd2, rd3)

        _uiState.update { currentState ->

            val currentRolledDiceList = currentState.rolledDiceList.toMutableList()
            currentRolledDiceList.add(_rolledDices)
            val updatedRolledDiceList = currentRolledDiceList.toList()
            currentState.copy(
                rolledDiceValue1 = getDiceImageResource(rd1),
                rolledDiceValue2 = getDiceImageResource(rd2),
                rolledDiceValue3 = getDiceImageResource(rd3),
                numberOfRolls = currentState.numberOfRolls + 1,
                rolledDiceList = updatedRolledDiceList
            )
        }
        _uiStateLiveData.value = DiceUiState(
            rolledDiceValue1 = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
            rolledDiceValue2 = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
            rolledDiceValue3 = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
            numberOfRolls = (_uiStateLiveData.value?.numberOfRolls ?: 0) + 1,
        )
    }


}

fun getDiceImageResource(diceValue: Int): Int{
    return when(diceValue){
        1-> R.drawable.dice_six_faces_one
        2 -> R.drawable.dice_six_faces_two
        3 -> R.drawable.dice_six_faces_three
        4 -> R.drawable.dice_six_faces_four
        5 -> R.drawable.dice_six_faces_five
        6 -> R.drawable.dice_six_faces_six
        else -> R.drawable.dice_six_faces_one
    }
}