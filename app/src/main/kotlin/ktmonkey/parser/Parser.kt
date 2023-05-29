package ktmonkey.parser

import ktmonkey.lexer.Lexer
import ktmonkey.lexer.Token
import ktmonkey.lexer.TokenType
import ktmonkey.lexer.TokenType.*

data class Parser(val lexer: Lexer) {
    val lexerTokens = lexer.parse().iterator() // A const reference to the iterator of the tokens in the Lexer
    lateinit var curToken: Token
    var peekToken: Token
    private fun nextToken() {
        curToken = peekToken
        peekToken = lexerTokens.iterator().next()
    }
    private fun hasMoreTokens(): Boolean = lexerTokens.hasNext()
    init {
        peekToken = lexerTokens.next()
        if (hasMoreTokens()) nextToken()
    }
    fun parseProgram(): Program {
        val program = Program(mutableListOf())
        while (hasMoreTokens() && peekToken.type != EOF) {
            val stmt = parseStatment()
            when (stmt) {
                is Statement -> program.statments.add(stmt)
                is ParsingStatementFailure -> println(stmt.error)
            }
            if (hasMoreTokens()) nextToken()
        }
        return program
    }

    private fun parseStatment(): ParsingStatementResult = when (curToken.type) {
        LET -> parseLetStatment()
        else -> ParsingStatementFailure("Can't handle this yet")
    }
    private fun expectPeek(tokenType: TokenType): Boolean {
        if (peekToken.type == tokenType) {
            nextToken()
            return true
        }
        return false
    }
    private fun curTokenIs(tokenType: TokenType): Boolean = curToken.type == tokenType
    private fun parseLetStatment(): ParsingStatementResult {
        val letToken = curToken
        if (!expectPeek(IDENT)) {
            return ParsingStatementFailure("Let statment : No identifier after let")
        }
        val nameToken = curToken

        if (!expectPeek(ASSIGN)) {
            return ParsingStatementFailure("Let statment : No assisgn sign after identifier")
        }

        while (!curTokenIs(SEMI)) {
            nextToken()
        }
        return LetStatement(letToken, Identifier(nameToken, nameToken.literal), null)
    }
}
