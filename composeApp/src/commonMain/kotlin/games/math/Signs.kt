package games.math

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
