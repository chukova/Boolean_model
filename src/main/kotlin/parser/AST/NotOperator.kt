package parser.AST

import java.util.*

class NotOperator(op : Expression) : Expression(){
    private val operand: Expression

    init {
        operand = op
    }


    override fun evaluateExpression(): MutableSet<Int>? {
        TODO("Not yet implemented")
    }

}