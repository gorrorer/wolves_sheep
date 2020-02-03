@file:Suppress("UNUSED_PARAMETER", "unused")



class GameField(var x: Int,var y: Int,var type: Int) {
    var prevx = x
    var prevy = y
}

class Condition(var field: Matrix<GameField>, var wolfNumber: Int, var direction: String,
                var listOfWolves: List<GameField>, var sheep: GameField,
                var children: MutableList<MutableList<Condition>>)