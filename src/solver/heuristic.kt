package solver

import main.GameField
import main.Matrix
import main.checkMove

fun GameField.getNeighbours(prev: Set<GameField>, field: Matrix<GameField>): MutableSet<GameField>{

    val output = mutableSetOf<GameField>()

    if (this.checkMove("q", field))
        if (field[this.x - 1, this.y - 1] !in prev)
            output.add(field[this.x - 1, this.y - 1])
    if (this.checkMove("w", field))
        if (field[this.x + 1, this.y - 1] !in prev)
            output.add(field[this.x + 1, this.y - 1])
    if (this.checkMove("a", field))
        if (field[this.x - 1, this.y + 1] !in prev)
            output.add(field[this.x - 1, this.y + 1])
    if (this.checkMove("s", field))
        if (field[this.x + 1, this.y + 1] !in prev)
            output.add(field[this.x + 1, this.y + 1])
    return output
}

fun evaluate(field: Matrix<GameField>, sheep: GameField): Int {

    var prevCells = mutableSetOf<GameField>()
    var currCells = mutableSetOf<GameField>()
    var usedCells = mutableSetOf(sheep)
    var steps = 0

    prevCells.add(sheep)

    while (test(usedCells)){
        steps++
        for (i in prevCells){
            currCells = currCells.plus(i.getNeighbours(prevCells, field)).toMutableSet()
        }
        usedCells = usedCells.plus(currCells).toMutableSet()
        prevCells = currCells
        currCells = mutableSetOf()
        if (steps > 20) return Int.MAX_VALUE
    }
    return steps
}

fun test(prevCells: Set<GameField>): Boolean{
    for (i in prevCells)
        if (i.x in listOf(1,3,5,7) && i.y == 0) return false
    return true
}