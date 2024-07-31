package components.score

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun Score(
    scoreViewModel: ScoreViewModel,
    modifier: Modifier = Modifier,
) {
    val p1score by remember { scoreViewModel.p1score }
    val p2score by remember { scoreViewModel.p2score }
    val rectColor = MaterialTheme.colorScheme.primary

    Box(modifier = modifier.fillMaxSize()) {
        Text(
            "$p2score : $p1score",
            modifier = Modifier
                .rotate(270f)
                .align(alignment = Alignment.CenterStart)
                .drawBehind {
                    // todo: stylize background
                    drawRect(
                        color = rectColor,
                    )
                },
            // todo: stylize text
            fontSize = 24.sp,
            style = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
        )
    }
}
