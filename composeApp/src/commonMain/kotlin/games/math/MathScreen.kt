import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.Player
import components.score.Score

@Composable
fun MathScreen(
    mathViewModel: MathViewModel,
    modifier: Modifier = Modifier,
) {

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
    val (task, answers) = mathViewModel.generateTask()

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
    onAnswerClick: (player: Player, tap: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        for (answer in answers) {
            Button(
                onClick = { onAnswerClick(player, answer) },
            ) {
                Text(
                    text = answer.toString(),
                )
                // todo: stylize text
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
