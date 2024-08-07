package games.math

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import components.Player
import components.score.ScoreViewModel
import games.Difficult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val DELAY_IN_MILLIS = 1_000L

class MathViewModel(
    difficult: Difficult = Difficult.MEDIUM,
) : ViewModel() {
    private val range = when (difficult) {
        Difficult.EASY -> 1..10
        Difficult.MEDIUM -> 1..50
        Difficult.HARD -> 1..100
        Difficult.IMPOSSIBLE -> -1000..1000
    }

    val scoreViewModel = ScoreViewModel()

    var task = mutableStateOf("")
        private set
    var answers = mutableStateOf<List<Int>>(listOf())
        private set

    var isButtonsEnabled = mutableStateOf(true)

    private var answer = 0

    init {
        generateTask()
    }

    private fun generateTask() {
        val (newTask, newAnswer) = prepareTask()

        task.value = newTask
        answer = newAnswer

        viewModelScope.launch(Dispatchers.Main) {
            delay(DELAY_IN_MILLIS)
            isButtonsEnabled.value = true
            answers.value = prepareWrongAnswers(newAnswer)
        }
    }

    // todo: add this to game configure?: improper fractions
    // todo: add this to game configure?: negative numbers
    private fun prepareTask(sign: Sign = Sign.entries.random()): Pair<String, Int> {
        val numbers = List(2) { range.random() }
        val (first, second) = numbers

        val answer = with(sign) { first.op(second) }
        if (sign == Sign.DIVIDE) {
            val realAnswer = first / second.toFloat()
            if (answer.toFloat() != realAnswer) {
                return prepareTask(sign = Sign.DIVIDE)
            }
        }

        val task = "$first $sign $second"
        return task to answer
    }

    private fun prepareWrongAnswers(answer: Int): List<Int> {
        val wrongAnswers = mutableListOf(answer)
        val wrongsRange = 1..10
        wrongAnswers.add(answer.plus(wrongsRange.random()))
        wrongAnswers.add(answer.minus(wrongsRange.random()))
        wrongAnswers.shuffle()
        return wrongAnswers
    }

    fun processAnswer(player: Player, tap: Int): Boolean {
        val result = when (player) {
            Player.P1 -> if (tap == answer) {
                scoreViewModel.p1Wins()
                true
            } else {
                scoreViewModel.p2Wins()
                false
            }

            Player.P2 -> if (tap == answer) {
                scoreViewModel.p2Wins()
                true
            } else {
                scoreViewModel.p1Wins()
                false
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            delay(DELAY_IN_MILLIS)
            answers.value = listOf()
            generateTask()
        }

        return result
    }
}
