@file:Suppress("UNUSED_PARAMETER", "unused")

var wolvesDirects = listOf("a", "s")

fun chooseMove(): Pair<Int, String> {

    var wolfNumber = 0
    var actualDirection = "a"
    var actualMoveCount: Int
    var maxMoveCount = 0

    for (wolf in listOfWolvesCop) {
        for (dir in wolvesDirects) {
            if (wolf.checkMove(dir, deskCop)) {
                wolf.move(dir, deskCop)
                    actualMoveCount = evaluate(deskCop, sheepCop)
                    if (actualMoveCount >= maxMoveCount) {
                        maxMoveCount = actualMoveCount
                        actualDirection = dir
                        wolfNumber = listOfWolvesCop.indexOf(wolf)
                    }
                wolf.backStep(deskCop)
            }
        }
    }
    println(maxMoveCount)
    return Pair(wolfNumber, actualDirection)
}

fun sheepBestMove(field: Matrix<GameField>): String{

    var bestMove = 100
    var currentMove: Int
    var direction = "q"

    for (sheepDir in moveSet){
        if (sheepCop.checkMove(sheepDir, field)){
            sheepCop.move(sheepDir, field)
            currentMove = evaluate(field, sheepCop)
            sheepCop.backStep(field)
            if (currentMove < bestMove){
                bestMove = currentMove
                direction = sheepDir
            }
        }
    }
    return direction
}

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

fun Matrix<GameField>.clone(): Matrix<GameField> {
    val matrix = createMatrix(8, 8, GameField(0, 0, 0))
    for (y in 0..7)
        for (x in 0..7){
            matrix[x, y] = this[x, y].clone()
        }
    return matrix
}

fun List<GameField>.clone(): List<GameField> {
    val list = mutableListOf(GameField(0, 0, 0), GameField(0, 0, 0), GameField(0, 0, 0), GameField(0, 0, 0))
    for (i in 0 until this.size) {
        list[i] = this[i].clone()
    }
    return list
}

fun GameField.clone(): GameField{
    val gameField = GameField(this.x, this.y, this.type)
    gameField.prevx = this.prevx
    gameField.prevy = this.prevy
    return gameField
}

fun test(prevCells: Set<GameField>): Boolean{
    for (i in prevCells)
        if (i.x in listOf(1,3,5,7) && i.y == 0) return false
    return true
}