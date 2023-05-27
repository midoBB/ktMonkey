package ktmonkey

import ktmonkey.TokenType.*

object Lexer {
    fun parse(value: String): Sequence<Token> = sequence {
        val tokenizer = Tokenizer(value)
        while (tokenizer.hasNext()) {
            val next = tokenizer.peek()
            val peeked = tokenizer.peekNext()
            when {
                next.isLetter() ->
                    yield(tokenizer.readUntil { !it.isLetterOrDigit() && !it.isUnderscore() }.asToken())
                next.isDigit() ->
                    yield(Token(type = INT, value = tokenizer.readUntil { !it.isDigit() }))
                next.isDoubleQuote() ->
                    yield(Token(type = STRING, value = tokenizer.readTo(true) { it.isDoubleQuote() }))
                else -> {
                    val tok = tokenizer.next().asToken(peeked)
                    if (tok.type == EQ || tok.type == NE) tokenizer.skip()
                    yield(tok)
                }
            }
        }
        yield(Token(type = EOF))
    }
}
