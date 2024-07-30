package components.score

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Score(
    scoreViewModel: ScoreViewModel,
    modifier: Modifier = Modifier,
) {
    val p1score by remember { scoreViewModel.p1score }
    val p2score by remember { scoreViewModel.p2score }
    Box() {
        // todo: add background
        Text(
            text = "$p2score : $p1score",
            modifier = modifier,
            textAlign = TextAlign.Center
        )
        // todo: stylize text
    }
}