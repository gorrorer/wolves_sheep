@file:Suppress("UNUSED_PARAMETER", "unused")

package solver

import main.*

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

