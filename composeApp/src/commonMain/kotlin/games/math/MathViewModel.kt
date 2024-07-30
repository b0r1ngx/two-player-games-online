import androidx.compose.runtime.mutableStateOf
import components.Player
import components.score.ScoreViewModel
import games.math.Signs

const val DELAY_IN_MILLIS = 1_000

class MathViewModel {
    val scoreViewModel = ScoreViewModel()

    var task = mutableStateOf("")
        private set
    var answers = mutableStateOf<List<Int>>(listOf())
        private set

    private var answer = 0

    init {
        generateTask()
    }

    private fun generateTask() {
        val (_task, _answer) = prepareTask()

        task.value = _task
        answer = _answer

        // scope.delay(DELAY_IN_MILLIS)

        answers.value = prepareWrongAnswers(_answer)
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
        wrongAnswers.shuffle()
        return wrongAnswers
    }

    fun processAnswer(player: Player, tap: Int): Boolean {
        val result: Boolean
        when (player) {
            Player.P1 -> if (tap == answer) {
                scoreViewModel.p1Wins()
                result = true
            } else {
                scoreViewModel.p2Wins()
                result = false
            }

            Player.P2 -> if (tap == answer) {
                scoreViewModel.p2Wins()
                result = true
            } else {
                scoreViewModel.p1Wins()
                result = false
            }
        }

        // scope.delay(DELAY_IN_MILLIS)

        generateTask()
        return result
    }
}