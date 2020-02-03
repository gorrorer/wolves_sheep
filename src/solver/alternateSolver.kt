package solver

import main.*

var treeOfMoves = listOf<Condition>()
var counter = difficult


fun createLevel(condition: Condition): List<Condition> {

    val listOfConditions = mutableListOf<Condition>()

    for (wolf in condition.listOfWolves) {
        for (dir in wolvesDirects) {
            if (wolf.checkMove(dir, condition.field)) {
                wolf.move(dir, condition.field)
                listOfConditions.add(
                    Condition(
                        condition.field.clone(), condition.listOfWolves.indexOf(wolf)
                        , dir, condition.listOfWolves.clone(), condition.sheep.clone(), mutableListOf()
                    )
                )
                wolf.backStep(condition.field)
            }
        }
    }
    return listOfConditions
}


fun createResultsTree(level: MutableList<List<Condition>>): Boolean {

    val actualLevel = mutableListOf<List<Condition>>()
    var actualList: List<Condition>
    var sheepCopy: GameField
    var deskCopy: Matrix<GameField>

    if (counter == 0){
        counter = difficult
        return true
    }

    if (counter == difficult){
        actualList = createLevel(Condition(deskCop, 0, "", listOfWolvesCop, sheepCop, mutableListOf()))
        treeOfMoves = actualList
        actualLevel.add(actualList)
        counter--
        createResultsTree(actualLevel)
    } else if (counter > 0 && counter < difficult){
        for (i in level) {
            for (k in i) {
                for (dir in moveSet) {
                    sheepCopy = k.sheep.clone()
                    deskCopy = k.field.clone()
                    sheepCopy.move(dir, deskCopy)
                    actualList = createLevel(
                        Condition(
                            deskCopy, 0, "",
                            k.listOfWolves.clone(), sheepCopy, mutableListOf()
                        )
                    ).toMutableList()
                    k.children.add(actualList)
                    actualLevel.add(actualList)
                }
            }
        }
        counter--
        createResultsTree(actualLevel)
    }
    return true
}

fun createResultsTree(){
    createResultsTree(mutableListOf())
}

fun analyzePath(level: MutableList<List<Condition>>): Int {

    var result = Int.MAX_VALUE
    var actualLevel = mutableListOf<List<Condition>>()
    var sheepCopy: GameField
    var deskCopy: Matrix<GameField>
    var actualResult: Int

    if (counter > 1) {
        for (i in level) {
            for (k in i) {
                actualLevel = actualLevel.plus(k.getChildren()).toMutableList()
            }
        }
        counter--
        analyzePath(actualLevel)
    } else {
        for (i in level) {
            for (k in i) {
                for (dir in moveSet) {
                    sheepCopy = k.sheep.clone()
                    deskCopy = k.field.clone()
                    sheepCopy.move(dir, deskCopy)
                    actualResult = evaluate(deskCopy, sheepCopy)
                    if (actualResult < result)
                        result = actualResult
                }
            }
        }
        counter = difficult
        return result
    }
    return 0
}

fun analyzePath(condition: Condition) = analyzePath(mutableListOf(listOf(condition)))


fun chooseMoveAlt2(): Pair<Int, String> {

    createResultsTree()
    val resultsTree = treeOfMoves
    var maxMoveCount = 0
    var rightNumber = 0
    var actualMoveCount: Int

    for (i in resultsTree){
        actualMoveCount = analyzePath(i)
        if (actualMoveCount > maxMoveCount){
            maxMoveCount = actualMoveCount
            rightNumber = resultsTree.indexOf(i)
        }
    }

    return Pair(resultsTree[rightNumber].wolfNumber, resultsTree[rightNumber].direction)
}


fun chooseMoveAlt(): Pair<Int, String> {

    createResultsTree()
    val resultsTree = treeOfMoves
    var minMoveCountLevel2 = Int.MAX_VALUE
    var minMoveCountLevel1 = Int.MAX_VALUE
    var moveCountLevel2 = 0
    var maxMoveCounter = 0
    var rightNumber = 0
    var actualMoveLevel1: Int

    for (i1 in resultsTree) {
        for (k1 in i1.children) {
            for (i2 in k1) {
                for (dir in moveSet) {
                    i2.sheep.move(dir, i2.field)
                    actualMoveLevel1 = evaluate(i2.field, i2.sheep)
                    if (actualMoveLevel1 < minMoveCountLevel2)
                        minMoveCountLevel2 = actualMoveLevel1
                    i2.sheep.backStep(i2.field)
                }
                if (moveCountLevel2 < minMoveCountLevel2) {
                    moveCountLevel2 = minMoveCountLevel2
                }
                minMoveCountLevel2 = Int.MAX_VALUE
            }
            if (minMoveCountLevel1 > moveCountLevel2) {
                minMoveCountLevel1 = moveCountLevel2
            }
            moveCountLevel2 = 0
        }
        if (minMoveCountLevel1 > maxMoveCounter) {
            maxMoveCounter = minMoveCountLevel1
            rightNumber = resultsTree.indexOf(i1)
        }
        minMoveCountLevel1 = Int.MAX_VALUE
    }

    return Pair(resultsTree[rightNumber].wolfNumber, resultsTree[rightNumber].direction)
}
