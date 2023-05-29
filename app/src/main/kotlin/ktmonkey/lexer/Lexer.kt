package ktmonkey.lexer

import ktmonkey.lexer.TokenType.*

data class Lexer(val input: String) {
    fun parse(): Sequence<Token> = sequence {
        val tokenizer = Tokenizer(input)
        while (tokenizer.hasNext()) {
            val next = tokenizer.peek()
            val peeked = tokenizer.peekNext()
            when {
                next.isLetter() ->
                    yield(tokenizer.readUntil { !it.isLetterOrDigit() && !it.isUnderscore() }.asToken())
                next.isDigit() ->
                    yield(Token(type = INT, literal = tokenizer.readUntil { !it.isDigit() }))
                next.isDoubleQuote() ->
                    yield(Token(type = STRING, literal = tokenizer.readTo(true) { it.isDoubleQuote() }))
                else -> {
                    val tok = tokenizer.next().asToken(peeked)
                    if (tok.type == EQ || tok.type == NE) tokenizer.skip()
                    yield(tok)
                }
            }
        }
        yield(Token(type = EOF, "\u0000"))
    }
}
