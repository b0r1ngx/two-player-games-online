import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import games.math.MathScreen
import games.math.MathViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val mathViewModel = MathViewModel()
        MathScreen(mathViewModel)
    }
}
