import androidx.compose.runtime.mutableStateOf
import components.Player
import components.score.ScoreViewModel

enum class Signs {
    PLUS, MINUS, MULTIPLY, DIVIDE;

    fun Int.op(o: Int): Int {
        return when (this@Signs) {
            PLUS -> this.plus(o)
            MINUS -> this.minus(o)
            MULTIPLY -> this.times(o)
            DIVIDE -> this.div(o)
        }
    }

    override fun toString(): String {
        return when (this) {
            PLUS -> "+"
            MINUS -> "-"
            MULTIPLY -> "*"
            DIVIDE -> "/"
        }
    }
}

class MathViewModel {
    val scoreViewModel = ScoreViewModel()
    private var correctAnswer = 0
    val isLoading = mutableStateOf(false)

    fun generateTask(): Pair<String, List<Int>> {
        val (task, answer) = prepareTask()
        correctAnswer = answer
        return task to prepareWrongAnswers(answer)
    }

    private fun prepareTask(): Pair<String, Int> {
        val sign = Signs.entries.random()

        val range = 1..100
        val numbers = List(2) { range.random() }
        val (first, second) = numbers

        val answer = with(sign) { first.op(second) }
        val task = "$first $sign $second"
        return task to answer
    }

    private fun prepareWrongAnswers(answer: Int): List<Int> {
        val wrongAnswers = mutableListOf(answer)
        val wrongsRange = 1..10
        wrongAnswers.add(answer.plus(wrongsRange.random()))
        wrongAnswers.add(answer.minus(wrongsRange.random()))
        return wrongAnswers
    }

    fun processAnswer(player: Player, tap: Int) {
        when (player) {
            Player.P1 -> if (tap == correctAnswer) scoreViewModel.p1Wins() else scoreViewModel.p2Wins()
            Player.P2 -> if (tap == correctAnswer) scoreViewModel.p2Wins() else scoreViewModel.p1Wins()
        }
    }
}