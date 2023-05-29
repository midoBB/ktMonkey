package ktmonkey.lexer

import ktmonkey.lexer.TokenType.*
import java.util.*

enum class TokenType { COMMA, EOF, ASSIGN, FUNC, IDENT, ILLEGAL, INT, LET, LPAREN,
    LSQUIRLY, PLUS, RPAREN, RSQUIRLY, SEMI, STRING, TRUE, FALSE, IF, ELSE, RETURN,
    EQ, NE, BANG, MINUS, SLASH, ASTERISK, GT, LT }
data class Token(val type: TokenType, val literal: String) {
    constructor(type: TokenType, literal: Char) : this(type, literal.toString())

    override fun toString(): String =
        "[$type -> $literal ]"
}

fun Char.isDoubleQuote() = this == '"'
fun Char.isUnderscore() = this == '_'

fun String.asToken() = when (this.lowercase(Locale.getDefault())) {
    "let" -> Token(LET, this)
    "fn" -> Token(FUNC, this)
    "true" -> Token(TRUE, this)
    "false" -> Token(FALSE, this)
    "if" -> Token(IF, this)
    "else" -> Token(ELSE, this)
    "return" -> Token(RETURN, this)
    else -> Token(IDENT, this)
}

fun Char.asToken(peeked: Char?) = when (this) {
    '=' -> isPeekMatch(this, '=', peeked, EQ, ASSIGN)
    '!' -> isPeekMatch(this, '=', peeked, NE, BANG)
    '-' -> Token(MINUS, this)
    '/' -> Token(SLASH, this)
    '>' -> Token(GT, this)
    '<' -> Token(LT, this)
    '*' -> Token(ASTERISK, this)
    '+' -> Token(PLUS, this)
    ',' -> Token(COMMA, this)
    ';' -> Token(SEMI, this)
    '(' -> Token(LPAREN, this)
    ')' -> Token(RPAREN, this)
    '{' -> Token(LSQUIRLY, this)
    '}' -> Token(RSQUIRLY, this)
    else -> Token(ILLEGAL, this)
}

fun isPeekMatch(current: Char, want: Char, peeked: Char?, matched: TokenType, default: TokenType): Token =
    when (peeked) {
        want -> Token(matched, "$current$peeked")
        else -> Token(default, current)
    }
