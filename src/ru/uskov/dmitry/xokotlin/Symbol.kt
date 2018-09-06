package ru.uskov.dmitry.xokotlin

enum class Symbol(val value: Char) {
    X('X'),
    O('O'),
    EMPTY(' ')
}

class SymbolSupplier {
    private var iter = 0
    private val array = arrayOf(Symbol.X, Symbol.O)
    fun next(): Symbol {
        return array[iter++ % array.size]
    }
}