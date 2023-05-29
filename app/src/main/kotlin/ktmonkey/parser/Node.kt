package ktmonkey.parser

sealed interface Node {
    fun tokenLiteral(): String
}

sealed interface ParsingStatementResult
data class ParsingStatementFailure(val error: String) : ParsingStatementResult
interface Statement : Node, ParsingStatementResult
interface Expression : Node

data class Program(val statments: MutableList<Statement>) : Node {
    override fun tokenLiteral(): String =
        if (statments.size > 0) statments.first().tokenLiteral() else ""
}
