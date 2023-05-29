package ktmonkey

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import ktmonkey.lexer.Lexer
import ktmonkey.lexer.Token
import ktmonkey.lexer.TokenType.*

class TokenTest : StringSpec({
    "First Test NextToken" {
        val input = """
            =+(){},;
        """.trimIndent()

        val tests = listOf(
            Token(ASSIGN, "="),
            Token(PLUS, "+"),
            Token(LPAREN, "("),
            Token(RPAREN, ")"),
            Token(LSQUIRLY, "{"),
            Token(RSQUIRLY, "}"),
            Token(COMMA, ","),
            Token(SEMI, ";"),
            Token(EOF, "\u0000"),
        )

        val tokens = Lexer(input).parse().toList()
        tokens shouldBeEqual tests
    }
    "Seond Test NextToken" {
        val input = """
            let five = 5;
            let ten = 10;
            let add = fn(x, y) {
                x + y;
            };
            let result = add(five, ten);
            !-/5*;
            5 < 10 > 5;
            if (5 < 10) {
                return true;
            } else {
                return false;
            }
            10 == 10;
            10 != 9;
        """.trimIndent()

        val expected = listOf(
            Token(LET, "let"),
            Token(IDENT, "five"),
            Token(ASSIGN, "="),
            Token(INT, "5"),
            Token(SEMI, ";"),
            Token(LET, "let"),
            Token(IDENT, "ten"),
            Token(ASSIGN, "="),
            Token(INT, "10"),
            Token(SEMI, ";"),
            Token(LET, "let"),
            Token(IDENT, "add"),
            Token(ASSIGN, "="),
            Token(FUNC, "fn"),
            Token(LPAREN, "("),
            Token(IDENT, "x"),
            Token(COMMA, ","),
            Token(IDENT, "y"),
            Token(RPAREN, ")"),
            Token(LSQUIRLY, "{"),
            Token(IDENT, "x"),
            Token(PLUS, "+"),
            Token(IDENT, "y"),
            Token(SEMI, ";"),
            Token(RSQUIRLY, "}"),
            Token(SEMI, ";"),
            Token(LET, "let"),
            Token(IDENT, "result"),
            Token(ASSIGN, "="),
            Token(IDENT, "add"),
            Token(LPAREN, "("),
            Token(IDENT, "five"),
            Token(COMMA, ","),
            Token(IDENT, "ten"),
            Token(RPAREN, ")"),
            Token(SEMI, ";"),
            Token(BANG, "!"),
            Token(MINUS, "-"),
            Token(SLASH, "/"),
            Token(INT, "5"),
            Token(ASTERISK, "*"),
            Token(SEMI, ";"),
            Token(INT, "5"),
            Token(LT, "<"),
            Token(INT, "10"),
            Token(GT, ">"),
            Token(INT, "5"),
            Token(SEMI, ";"),
            Token(IF, "if"),
            Token(LPAREN, "("),
            Token(INT, "5"),
            Token(LT, "<"),
            Token(INT, "10"),
            Token(RPAREN, ")"),
            Token(LSQUIRLY, "{"),
            Token(RETURN, "return"),
            Token(TRUE, "true"),
            Token(SEMI, ";"),
            Token(RSQUIRLY, "}"),
            Token(ELSE, "else"),
            Token(LSQUIRLY, "{"),
            Token(RETURN, "return"),
            Token(FALSE, "false"),
            Token(SEMI, ";"),
            Token(RSQUIRLY, "}"),
            Token(INT, "10"),
            Token(EQ, "=="),
            Token(INT, "10"),
            Token(SEMI, ";"),
            Token(INT, "10"),
            Token(NE, "!="),
            Token(INT, "9"),
            Token(SEMI, ";"),
            Token(EOF, "\u0000"),
        )

        val actual = Lexer(input).parse().toList()

        expected.zip(actual).forEach { (expectedToken, actualToken) ->
            expectedToken.type shouldBe actualToken.type
            expectedToken.literal shouldBe actualToken.literal
        }
    }
})
