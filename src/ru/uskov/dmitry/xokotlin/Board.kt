package ru.uskov.dmitry.xokotlin

class Board(private val size: Int) {

    private val table: Array<Array<Symbol>>
    private var winner: Symbol? = null
    private var end: Boolean = false

    /**
     * throw IllegalArgumentException if SIZE < 1
     */
    init {
        if (size < 1) {
            throw IllegalArgumentException("Board SIZE should be > 0. Actual value: $size")
        }
        this.table = Array(size) { Array(size) { Symbol.EMPTY } }
    }

    /**
     * throw InvalidCoordinateException
     * throw IllegalStateException -- if game over
     */
    fun setSymbol(x: Int, y: Int, symbol: Symbol) {
        fun validateCoordinate(value: Int, name: String) {
            if (value < 0 || value >= size) {
                throw InvalidCoordinateException("Coordinate $name should be >=0 and <$size. Actual value: $value")
            }
        }

        if (end) {
            throw IllegalStateException("Could not set symbol. Game over")
        }

        validateCoordinate(x, "x")
        validateCoordinate(y, "y")

        if (table[x][y] != Symbol.EMPTY) {
            throw InvalidCoordinateException("Could not set symbol. Cell is not empty.")
        }
        table[x][y] = symbol
    }

    fun endOfGame(): Boolean {
        if (!end) {
            end = checkWinner() != null || boardIsFull()
        }
        return end
    }

    private fun boardIsFull(): Boolean {
        for (row in table) {
            for (cell in row) {
                if (cell == Symbol.EMPTY) {
                    return false
                }
            }
        }
        return true
    }

    //TODO remove duplicate logic
    fun checkWinner(): Symbol? {
        if (winner != null) {
            return winner
        }
        winner = checkWinnerRow()
        if (winner != null) {
            return winner
        }
        winner = checkWinnerColumn()
        if (winner != null) {
            return winner
        }
        winner = checkWinnerDiagonalBase()
        if (winner != null) {
            return winner
        }
        winner = checkWinnerDiagonalSecondary()
        if (winner != null) {
            return winner
        }

        return null
    }


    private fun checkWinnerRow(): Symbol? {
        rowLoop@ for (x in 0 until size) {
            val s = table[x][0]
            if (s == Symbol.EMPTY) {
                continue
            }
            for (y in 1 until size) {
                if (table[x][y] != s) {
                    continue@rowLoop
                }
            }
            return s
        }
        return null
    }

    private fun checkWinnerColumn(): Symbol? {
        rowLoop@ for (x in 0 until size) {
            val s = table[0][x]
            if (s == Symbol.EMPTY) {
                continue
            }
            for (y in 1 until size) {
                if (table[y][x] != s) {
                    continue@rowLoop
                }
            }
            return s
        }
        return null
    }

    private fun checkWinnerDiagonalBase(): Symbol? {
        val s = table[0][0]
        if (s == Symbol.EMPTY) {
            return null
        }
        for (x in 0 until size) {
            if (table[x][x] != s) {
                return null
            }
        }
        return s

    }

    private fun checkWinnerDiagonalSecondary(): Symbol? {
        val s = table[0][size - 1]
        if (s == Symbol.EMPTY) {
            return null
        }
        for (x in 0 until size) {
            if (table[x][size - 1 - x] != s) {
                return null
            }
        }
        return s
    }

    //todo remove hardcoded println
    fun printBoard() {
        printSeparateLine()
        for (row in table) { //each row
            //print top of the cell
            print("||")
            for (cell in row) {
                print("     ||")
            }
            println()

            //print centr of the cell
            print("||")
            for (cell in row) {
                print("  ${cell.value}  ||")
            }
            println()

            //print bottom of the cell
            print("||")
            for (cell in row) {
                print("     ||")
            }

            println()
            printSeparateLine()
        }
    }


    private fun printSeparateLine() {
        print("==")
        for (index in 1..size) {
            print("=======")
        }
        println()
    }

}

class InvalidCoordinateException(message: String) : Exception(message)