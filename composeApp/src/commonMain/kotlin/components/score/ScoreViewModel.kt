package components.score

import androidx.compose.runtime.mutableStateOf

class ScoreViewModel {
    var p1score = mutableStateOf(0)
        private set

    var p2score = mutableStateOf(0)
        private set

    fun p1Wins() {
        p1score.value += 1
    }

    fun p2Wins() {
        p2score.value += 1
    }
}