package games.math

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
private fun Game(
    mathViewModel: MathViewModel,
    modifier: Modifier = Modifier,
) {
    val task by remember { mathViewModel.task }
    val answers by remember { mathViewModel.answers }
    val isButtonsEnabled = remember { mathViewModel.isButtonsEnabled }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Answers(
            player = Player.P1,
            answers = answers,
            onAnswerClick = mathViewModel::processAnswer,
            isButtonsEnabled = isButtonsEnabled,
            modifier = Modifier.rotate(180f).weight(1 / 4f),
        )
        Task(
            task = task,
            modifier = Modifier.rotate(180f).weight(1 / 4f),
        )
        HorizontalDivider()
        Task(
            task = task,
            modifier = Modifier.weight(1 / 4f),
        )
        Answers(
            player = Player.P2,
            answers = answers,
            onAnswerClick = mathViewModel::processAnswer,
            isButtonsEnabled = isButtonsEnabled,
            modifier = Modifier.weight(1 / 4f),
        )
    }
}

@Composable
private fun Answers(
    player: Player,
    answers: List<Int>,
    onAnswerClick: (player: Player, tap: Int) -> Boolean,
    isButtonsEnabled: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        AnimatedVisibility(
            visible = answers.isNotEmpty(),
            enter = scaleIn(),
            exit = scaleOut(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 40.dp, alignment = Alignment.CenterHorizontally),
            ) {
                for (answer in answers) {
                    var isClickCorrect by remember { mutableStateOf<Boolean?>(null) }
                    val buttonColors = when (isClickCorrect) {
                        null -> {
                            ButtonDefaults.buttonColors()
                        }

                        true -> {
                            ButtonDefaults.buttonColors(
                                containerColor = Color.Green,
                                disabledContainerColor = Color.Green
                            )
                        }

                        else -> {
                            ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                disabledContainerColor = Color.Red
                            )
                        }
                    }

                    Button(
                        onClick = {
                            isClickCorrect = onAnswerClick(player, answer)
                            isButtonsEnabled.value = false
                        },
                        enabled = isButtonsEnabled.value,
                        shape = RoundedCornerShape(25),
                        colors = buttonColors
                    ) {
                        Text(
                            text = answer.toString(),
                            fontSize = 24.sp
                        )
                        // todo: stylize text
                    }
                }
            }
        }
    }
}

@Composable
private fun Task(
    task: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = task,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        // todo: stylize text
    }
}
