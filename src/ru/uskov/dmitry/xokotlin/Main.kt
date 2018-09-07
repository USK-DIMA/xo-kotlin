package ru.uskov.dmitry.xokotlin

fun main(arg: Array<String>) {
    val board = Board()
    val symbolSupplier = SymbolSupplier()
    var symbol = symbolSupplier.next()
    do {
        board.printBoard()
        print("$symbol: ")

        try {
            val (x, y) = readCoordinate()
            board.setSymbol(x, y, symbol)
            symbol = symbolSupplier.next()
        } catch (e: InvalidCoordinateException) {
            println(e.message)
        } catch (e: IncorrectInputException) {
            println("Incorrect Input")
        }
    } while (!board.endOfGame())
    board.printBoard()

    val winner = board.checkWinner()
    if (winner != null) {
        println("Winner is ${winner.value}")
    } else {
        println("Standoff")
    }
}

fun readCoordinate(): Pair<Int, Int> {
    try {
        val (x, y) = readLine()!!.split(", ", " ", ",")
        return (x.toInt()-1) to y.toInt()-1
    } catch (e: Exception) {
        throw IncorrectInputException()
    }

}

class IncorrectInputException : Exception()




