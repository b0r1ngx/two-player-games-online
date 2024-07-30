import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import components.Player
import components.score.Score

@Composable
fun MathScreen(
    mathViewModel: MathViewModel = MathViewModel(),
    modifier: Modifier = Modifier,
) {
    // todo: add background
    Box(modifier = modifier) {
        Game(mathViewModel)
        Score(
            scoreViewModel = mathViewModel.scoreViewModel,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .rotate(270f)
        )
    }
}

@Composable
fun Game(
    mathViewModel: MathViewModel,
    modifier: Modifier = Modifier,
) {
    val task by remember { mathViewModel.task }
    val answers by remember { mathViewModel.answers }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Answers(
            player = Player.P1,
            answers = answers,
            onAnswerClick = mathViewModel::processAnswer,
            modifier = Modifier.rotate(180f),
        )
        Task(
            task = task,
            modifier = Modifier.rotate(180f),
        )
        Divider()
        Task(task = task)
        Answers(
            player = Player.P2,
            answers = answers,
            onAnswerClick = mathViewModel::processAnswer,
        )
    }
}

@Composable
fun Answers(
    player: Player,
    answers: List<Int>,
    onAnswerClick: (player: Player, tap: Int) -> Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(answers.isNotEmpty()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            for (answer in answers) {
                var isClickCorrect by mutableStateOf<Boolean?>(null)
                val buttonColors = when (isClickCorrect) {
                    null -> {
                        ButtonDefaults.buttonColors()
                    }

                    true -> {
                        ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                    }

                    else -> {
                        ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    }
                }

                Button(
                    onClick = {
                        isClickCorrect = onAnswerClick(player, answer)
                    },
                    colors = buttonColors
                ) {
                    Text(
                        text = answer.toString(),
                    )
                    // todo: stylize text
                }
            }
        }
    }
}

@Composable
fun Task(
    task: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = task,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    // todo: stylize text
}
