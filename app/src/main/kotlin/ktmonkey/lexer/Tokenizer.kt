package ktmonkey.lexer

internal class Tokenizer(value: String) {
    private var position = 0
    private val value = value.trim()

    fun peek(): Char {
        skipWhitespace()
        return value[position]
    }

    fun peekNext(): Char? = if (position + 1 < value.length) value.get(position + 1) else null

    fun hasNext() = position < value.length

    fun next(): Char {
        skipWhitespace()
        return value[position++]
    }

    fun readUntil(skipCurrent: Boolean = false, predicate: (Char) -> Boolean): String {
        skipWhitespace()
        if (skipCurrent) skip()
        val oldPosition = position
        while (position < value.length && !predicate(value[position])) position++
        return value.substring(oldPosition, position)
    }

    fun readTo(skipCurrent: Boolean = false, predicate: (Char) -> Boolean): String {
        val result = readUntil(skipCurrent, predicate)
        skip()
        return result
    }

    fun skip() {
        position++
    }

    private fun skipWhitespace() {
        while (value[position].isWhitespace()) position++
    }
}
