
var wolvesDirects = listOf("a", "s")

fun chooseMove(): Pair<Int, String> {

    var actualMoveCount = 0
    var maxMoveCount = 0
    var wolfNumber = 0
    var bestDirection = "a"
    var recLevel = 0

    if (recLevel == 0) {
        var deskCop = desk
        var listOfWolvesCop = listOfWolves
        var sheepCop = sheep
    }

    for (wolf in listOfWolves) {
        for (dir in wolvesDirects) {
            if (wolf.checkMove(dir)) {
                wolf.move(dir)
                actualMoveCount = sheepEva()
                if (actualMoveCount >= maxMoveCount) {
                    maxMoveCount = actualMoveCount
                    bestDirection = dir
                    wolfNumber = listOfWolves.indexOf(wolf)
                }
                wolf.backStep()
            }
        }
    }
    println(maxMoveCount)
    return Pair(wolfNumber, bestDirection)
}

fun sheepEva(): Int{
    var bestMove = 100
    var currentMove: Int
    for (sheepDir in moveSet){
        if (sheep.checkMove(sheepDir)){
            sheep.move(sheepDir)
            currentMove = evaluate()
            sheep.backStep()
            if (currentMove < bestMove) bestMove = currentMove
        }
    }
    return bestMove
}

fun GameField.getNeighbours(prev: Set<GameField>): MutableSet<GameField>{

    val output = mutableSetOf<GameField>()

    if (this.checkMove("q"))
        if (desk[this.x - 1, this.y - 1] !in prev)
            output.add(desk[this.x - 1, this.y - 1])
    if (this.checkMove("w"))
        if (desk[this.x + 1, this.y - 1] !in prev)
            output.add(desk[this.x + 1, this.y - 1])
    if (this.checkMove("a"))
        if (desk[this.x - 1, this.y + 1] !in prev)
            output.add(desk[this.x - 1, this.y + 1])
    if (this.checkMove("s"))
        if (desk[this.x + 1, this.y + 1] !in prev)
            output.add(desk[this.x + 1, this.y + 1])
    return output
}

fun evaluate(): Int {

    var prevCells = mutableSetOf<GameField>()
    var currCells = mutableSetOf<GameField>()
    var usedCells = mutableSetOf(sheep)
    var steps = 0

    prevCells.add(sheep)

    while (test(prevCells)){
        for (i in prevCells){
           currCells = currCells.plus(i.getNeighbours(prevCells)).toMutableSet()
        }
        usedCells = usedCells.plus(currCells).toMutableSet()
        prevCells = currCells
        currCells = mutableSetOf()
        steps++
        if (steps > 20) return 1001
    }
    return steps
}

fun test(prevCells: Set<GameField>): Boolean{
    for (i in prevCells)
        if (i.x in listOf(1,3,5,7) && i.y == 0) return false
    return true
}