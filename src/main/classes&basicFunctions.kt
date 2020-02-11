@file:Suppress("UNUSED_PARAMETER", "unused")

package main


class GameField(var x: Int,var y: Int,var type: Int) {
    var prevx = x
    var prevy = y
}

class Condition(var field: Matrix<GameField>, var wolfNumber: Int, var direction: String,
                var listOfWolves: List<GameField>, var sheep: GameField,
                var children: MutableList<MutableList<Condition>>) {
    var movesToWin = 0
}

fun Condition.getChildren(): List<List<Condition>> {

    val listOfChildren = mutableListOf<MutableList<Condition>>()

    for (child in this.children){
        listOfChildren.add(child)
    }
    return listOfChildren
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
    val list = mutableListOf(
        GameField(0, 0, 0),
        GameField(0, 0, 0),
        GameField(0, 0, 0),
        GameField(0, 0, 0)
    )
    for (i in 0 until this.size) {
        list[i] = this[i].clone()
    }
    return list
}

fun GameField.clone(): GameField {
    val gameField = GameField(this.x, this.y, this.type)
    gameField.prevx = this.prevx
    gameField.prevy = this.prevy
    return gameField
}