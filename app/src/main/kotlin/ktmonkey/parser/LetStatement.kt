package ktmonkey.parser

import ktmonkey.lexer.Token

data class LetStatement(val letToken: Token, val name: Identifier, val value: Expression?) : Statement {
    override fun tokenLiteral(): String = letToken.literal
}
data class Identifier(val identToken: Token, val value: String) : Expression {
    override fun tokenLiteral(): String = identToken.literal
}
