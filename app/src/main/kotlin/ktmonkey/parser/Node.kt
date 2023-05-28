package ktmonkey.parser

sealed interface Node {
    fun tokenLiteral() : String
}

interface Statement : Node
interface Expression: Node

data class Program(val statments: List<Statement>, ): Node{
    override fun tokenLiteral(): String =
        if (statments.size >0 ) statments.first().tokenLiteral() else ""
}
