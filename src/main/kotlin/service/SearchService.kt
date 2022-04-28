package service

import model.File
import model.Query
import parser.AST.Expression
import parser.Parser
import preprocessor.Preprocessor

class SearchService {
    private val preprocessor: Preprocessor
    private val parser: Parser
    private var query: String = "flu and spanish or interior"
    private var expression: Expression


    init {
        println("[INFO] Preprocessing started.")

        preprocessor = Preprocessor("stop_words_english")
        preprocessor.preprocess("data") // from resources

        println("[INFO] Preprocessing finished.")

        parser = Parser(preprocessor)
        expression = parser.parse(query)
    }


    fun setQuery(newQuery: Query): Boolean {
        this.query = newQuery.query

        try {
            println("[INFO] Validation of query $query started.")
            expression = parser.parse(this.query)
            println("[INFO] Validation of query finished.")

        } catch (e: Exception) {
            println("[ERROR] ${e.message}")
            return false
        }
        return true
    }


    fun booleanSearch(): MutableSet<File> {
        println("[INFO] Evaluation of \"$query\" using inverted indexes started.")
        val result = expression.evaluateBoolean()
        println("[INFO] Evaluation finished")

        if (result == null) return mutableSetOf()
        return result
    }


    fun sequenceSearch(): MutableSet<File> {
        println("[INFO] Evaluation of \"$query\" using sequence search started.")
        val result = expression.evaluateSequence()
        println("[INFO] Evaluation finished")

        if (result == null) return mutableSetOf()
        return result
    }

}