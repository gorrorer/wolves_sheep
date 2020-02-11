@file:Suppress("UNUSED_PARAMETER", "unused")

package main

import solver.*


val desk = createMatrix(8, 8, GameField(0, 0, 0))
val evaMatrix = createMatrix(8, 8, 0)
val listOfWolves = mutableListOf<GameField>()
val sheep = GameField(4, 7, 2)
val moveSet = listOf("a", "s", "q", "w")
var deskCop = desk.clone()
var listOfWolvesCop = listOfWolves.clone()
var sheepCop = sheep.clone()
var difficult = 2

//∎⎕⏹☐■
fun draw(field: Matrix<GameField>){
    for (i in 0..7){
        for (k in 0..7) {
            if (field[k, i].type == 0) {
                if (k % 2 == 0 && i % 2 == 0)
                    print("■")
                if (k % 2 == 1 && i % 2 == 0)
                    print(" ")
                if (k % 2 == 0 && i % 2 == 1)
                    print(" ")
                if (k % 2 == 1 && i % 2 == 1)
                    print("■")
            }
            if (field[k, i].type == 1)
                print("W")
            if (field[k, i].type == 2)
                print("S")
            print("  ")
        }
        println()
}
}

fun GameField.move(){
    val direction = readLine()!!
    if (direction !in moveSet)
        this.move()
    this.tryMove(direction, desk)
}

fun GameField.move(direct: String, field: Matrix<GameField>){
    if (direct !in moveSet)
        throw Exception("Wrong direction")
    if (this.checkMove(direct, field))
        this.tryMove(direct, field)
}

fun GameField.tryMove(direction: String, field: Matrix<GameField>){
        if (direction == "a"){
            field[x, y] = GameField(x, y, 0)
            field[x - 1, y + 1] = this
            this.prevx = this.x
            this.prevy = this.y
            this.x -= 1
            this.y += 1
        }
        if (direction == "s"){
            field[x, y] = GameField(x, y, 0)
            field[x + 1, y + 1] = this
            this.prevx = this.x
            this.prevy = this.y
            this.x += 1
            this.y += 1
        }
        if (direction == "q"){
            field[x, y] = GameField(x, y, 0)
            field[x - 1, y - 1] = this
            this.prevx = this.x
            this.prevy = this.y
            this.x -= 1
            this.y -= 1
        }
        if (direction == "w"){
            field[x, y] = GameField(x, y, 0)
            field[x + 1, y - 1] = this
            this.prevx = this.x
            this.prevy = this.y
            this.x += 1
            this.y -= 1
        }
}

fun GameField.backStep(field: Matrix<GameField>){
        field[x, y] = GameField(x, y, 0)
        field[prevx, prevy] = this
        x = prevx
        y = prevy
}

fun GameField.checkMove(direction: String, field: Matrix<GameField>): Boolean {

    if (direction == "a" && x > 0 && y < 7){
        if (field[x - 1, y + 1].type == 0)
            return true
    }
    if (direction == "s" && x < 7 && y < 7){
        if (field[x + 1, y + 1].type == 0)
            return true
    }
    if (direction == "q" && x > 0 && y > 0){
        if (field[x - 1, y - 1].type == 0)
            return true
    }
    if (direction == "w" && x < 7 && y > 0){
        if (field[x + 1, y - 1].type == 0)
            return true
    }
    return false
}

fun game() {
    for (i in 0..3) {
        listOfWolves.add(GameField(i * 2 + 1, 0, 1))
        desk[listOfWolves[i].x, listOfWolves[i].y] = listOfWolves[i]
    }
    for (i in 0..7){
        if (i % 2 == 0)
            desk[i, 0] = GameField(i, 0, 0)
    }
    for (y in 1..7)
        for (x in 0..7){
            if (x == sheep.x && y == sheep.y)
                else
                desk[x, y] = GameField(x, y, 0)
        }

    var bestMove: Pair<Int, String>
    desk[sheep.x, sheep.y] = sheep

    draw(desk)

    while ((sheep.checkMove("q", desk) || sheep.checkMove("w", desk)
        || sheep.checkMove("a", desk) || sheep.checkMove("s", desk)) && (sheep.y != 0)) {
        sheep.move()
        deskCop = desk.clone()
        listOfWolvesCop = listOfWolves.clone()
        sheepCop = sheep.clone()
        bestMove = chooseMoveAlt()
        listOfWolves[bestMove.first].move(bestMove.second, desk)
        draw(desk)
    }
}

fun main(){
    readLine()
    game()
}