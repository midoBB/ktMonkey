package ktmonkey.parser

import ktmonkey.lexer.Lexer
import ktmonkey.lexer.Token

data class Parser (val lexer:Lexer){
    val lexerTokens = lexer.parse() // A const reference to the iterator of the tokens in the Lexer
    lateinit var curToken: Token
    lateinit var peekToken: Token
    private fun nextToken() : Unit{
        curToken = peekToken
        peekToken = lexerTokens.take(1).first()
    }
    init {
        nextToken()
        nextToken()
    }
    fun parseProgram() : Program? {
        return null
    }
}
