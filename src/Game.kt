@file:Suppress("UNUSED_PARAMETER", "unused")


val desk = createMatrix(8, 8, GameField(0, 0, 0))
val evaMatrix = createMatrix(8, 8, 0)
val listOfWolves = mutableListOf<GameField>()
val sheep = GameField(4, 7, 2)
val moveSet = listOf("a", "s", "q", "w")

//∎⎕⏹☐■
fun draw(){
    for (i in 0..7){
        for (k in 0..7) {
            if (desk[k, i].type == 0) {
                if (k % 2 == 0 && i % 2 == 0)
                    print("■")
                if (k % 2 == 1 && i % 2 == 0)
                    print(" ")
                if (k % 2 == 0 && i % 2 == 1)
                    print(" ")
                if (k % 2 == 1 && i % 2 == 1)
                    print("■")
            }
            if (desk[k, i].type == 1)
                print("W")
            if (desk[k, i].type == 2)
                print("S")
            print("  ")
        }
        println()
}
}

fun GameField.move(){
    var direction = ""
    direction = readLine()!!
    if (direction !in moveSet)
        this.move()
    this.tryMove(direction)
}

fun GameField.move(direct: String){
    if (direct !in moveSet)
        throw Exception("Wrong direction")
    if (this.checkMove(direct))
        this.tryMove(direct)
}

fun GameField.tryMove(direction: String){
        if (direction == "a"){
            desk[x, y] = GameField(x, y, 0)
            desk[x - 1, y + 1] = this
            this.prevx = this.x
            this.prevy = this.y
            this.x -= 1
            this.y += 1
        }
        if (direction == "s"){
            desk[x, y] = GameField(x, y, 0)
            desk[x + 1, y + 1] = this
            this.prevx = this.x
            this.prevy = this.y
            this.x += 1
            this.y += 1
        }
        if (direction == "q"){
            desk[x, y] = GameField(x, y, 0)
            desk[x - 1, y - 1] = this
            this.prevx = this.x
            this.prevy = this.y
            this.x -= 1
            this.y -= 1
        }
        if (direction == "w"){
            desk[x, y] = GameField(x, y, 0)
            desk[x + 1, y - 1] = this
            this.prevx = this.x
            this.prevy = this.y
            this.x += 1
            this.y -= 1
        }
}

fun GameField.backStep(){
        desk[x, y] = GameField(x, y, 0)
        desk[prevx, prevy] = this
        x = prevx
        y = prevy
}

fun GameField.checkMove(direction: String): Boolean {
    if (direction == "a" && x > 0 && y < 7){
        if (desk[x - 1, y + 1].type == 0)
        return true
    }
    if (direction == "s" && x < 7 && y < 7){
        if (desk[x + 1, y + 1].type == 0)
        return true
    }
    if (direction == "q" && x > 0 && y > 0){
        if (desk[x - 1, y - 1].type == 0)
        return true
    }
    if (direction == "w" && x < 7 && y > 0){
        if (desk[x + 1, y - 1].type == 0)
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

    draw()

    while (sheep.checkMove("q") || sheep.checkMove("w") || sheep.checkMove("a") || sheep.checkMove("s")) {
        sheep.move()
        draw()
        bestMove = chooseMove()
        listOfWolves[bestMove.first].move(bestMove.second)
        draw()
    }
}

fun main(){
    readLine()
    for (i in 0..2047)
        90+20
    game()
}