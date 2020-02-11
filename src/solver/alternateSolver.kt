@file:Suppress("UNUSED_PARAMETER", "unused")

package solver

import main.*

var treeOfMoves = mutableListOf<Condition>()
var counter = difficult


fun createLevel(condition: Condition): MutableList<Condition> {

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
    return listOfConditions.toMutableList()
}


fun createResultsTree(level: MutableList<List<Condition>>): Boolean {

    val actualLevel = mutableListOf<List<Condition>>()
    var actualList: MutableList<Condition>
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
    return false
}

fun createResultsTree(){
    createResultsTree(mutableListOf())
}


fun sheepBestMove(condition: Condition): Int{

    var bestMove = Int.MAX_VALUE
    var currentMove: Int

    for (sheepDir in moveSet){
        if (condition.sheep.checkMove(sheepDir, condition.field)){
            condition.sheep.move(sheepDir, condition.field)
            currentMove = evaluate(condition.field, condition.sheep)
            condition.sheep.backStep(condition.field)
            if (currentMove < bestMove){
                bestMove = currentMove
            }
        }
    }
    return bestMove
}


fun analyzePath(level: List<List<Condition>>) {

    var actualLevel = mutableListOf<List<Condition>>()
    var minMoveCount = Int.MAX_VALUE
    var maxMoveCount = 0

    if (counter > 1){
        counter--
        for (i in level){
            for (k in i){
                actualLevel = actualLevel.plus(k.getChildren()).toMutableList()
            }
        }
        analyzePath(actualLevel)
    }

    if (counter == difficult)
        for (i in level){
            for (k in i){
                for (children in k.children){
                    for (l in children){
                        if (l.movesToWin > maxMoveCount){
                            maxMoveCount = l.movesToWin
                        }
                    }
                    if (minMoveCount > maxMoveCount){
                        minMoveCount = maxMoveCount
                    }
                    maxMoveCount = 0
                }
                k.movesToWin = minMoveCount
                minMoveCount = Int.MAX_VALUE
            }
        }

    if (counter == 1){
        for (i in level){
            for (k in i){
                k.movesToWin = sheepBestMove(k)
            }
        }
        counter = difficult
    }
}

fun analyzePath(condition: Condition) = analyzePath(listOf(listOf(condition)))


fun chooseMoveAlt(): Pair<Int, String> {

    createResultsTree()
    val resultsTree = treeOfMoves
    var maxMoveCount = 0
    var rightNumber = 0
    var actualMoveCount: Int

    for (i in resultsTree){
        analyzePath(i)
        actualMoveCount = i.movesToWin
        if (actualMoveCount > maxMoveCount){
            maxMoveCount = actualMoveCount
            rightNumber = resultsTree.indexOf(i)
        }
    }

    return Pair(resultsTree[rightNumber].wolfNumber, resultsTree[rightNumber].direction)
}