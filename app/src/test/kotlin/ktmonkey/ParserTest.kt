package ktmonkey

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ktmonkey.lexer.Lexer
import ktmonkey.parser.LetStatement
import ktmonkey.parser.Parser
import ktmonkey.parser.Statement

class LexerTest : StringSpec({
    "FirstLexerTest" {
        val input = """
            let x = 5;
            let y = 10;
            let foobar = 838383;
        """.trimIndent()
        val lexer = Lexer(input)
        val parser = Parser(lexer)
        val actual = parser.parseProgram()

        val expectedIdents = listOf("x", "y", "foobar")

        actual shouldNotBe null
        actual.statments.size shouldBe 3

        expectedIdents.zip(actual.statments).forEach { (ident, statment) ->
            testLetStatement(ident, statment)
        }
    }
})

fun testLetStatement(ident: String, statment: Statement) {
    statment.tokenLiteral() shouldBe "let"
    (statment is LetStatement) shouldBe true
    val letStatment = statment as LetStatement
    letStatment.name.value shouldBe ident
    letStatment.name.identToken.literal shouldBe ident
}
