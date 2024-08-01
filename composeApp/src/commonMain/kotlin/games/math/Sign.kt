package games.math

enum class Sign {
    PLUS, MINUS, MULTIPLY, DIVIDE;

    fun Int.op(o: Int): Int {
        return when (this@Sign) {
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
