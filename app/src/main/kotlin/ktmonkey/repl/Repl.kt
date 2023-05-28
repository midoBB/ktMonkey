package ktmonkey.repl

import ktmonkey.lexer.Lexer

const val PROMPT = ">> "
object Repl {

    fun start() {
        while (true){
            print(PROMPT)
            val inp = readLine()
            if( inp.isNullOrBlank() || inp.isEmpty()){
                return
            }
            val tokens = Lexer(inp).parse().toList()
            tokens.forEach { println(it) }
        }
    }
}
