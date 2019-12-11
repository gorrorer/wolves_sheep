@file:Suppress("UNUSED_PARAMETER", "unused")


val desk = createMatrix(8, 8, GameField(0, 0, 0))

fun game() {
    val listOfWolves = mutableListOf<GameField>()
    val sheep = GameField(4, 7, 2)
    desk[sheep.x, sheep.y] = sheep
    for (i in 0..3) {
        listOfWolves.add(GameField(i * 2 + 1, 0, 1))
        desk[listOfWolves[i].x, listOfWolves[i].y] = listOfWolves[i]
    }
    draw()
    while (true) {
        for (i in 0..3) {
            listOfWolves[i].move()
            draw()
        }
        sheep.move()
        draw()
    }
}
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
    if (direction !in listOf("q", "w", "a", "s"))
        this.move()
    if (this.type == 1){
        if (direction == "a" && x > 0 && y < 7 && desk[x - 1, y + 1].type == 0){
            desk[x, y] = GameField(0, 0, 0)
            desk[x - 1, y + 1] = this
            this.x -= 1
            this.y += 1
        }
        if (direction == "s" && x < 7 && y < 7 && desk[x + 1, y + 1].type == 0){
            desk[x, y] = GameField(0, 0, 0)
            desk[x + 1, y + 1] = this
            this.x += 1
            this.y += 1
        }
    }
    if (this.type == 2){
        if (direction == "a" && x > 0 && y < 7 && desk[x - 1, y + 1].type == 0){
            desk[x, y] = GameField(0, 0, 0)
            desk[x - 1, y + 1] = this
            this.x -= 1
            this.y += 1
        }
        if (direction == "s" && x < 7 && y < 7 && desk[x + 1, y + 1].type == 0){
            desk[x, y] = GameField(0, 0, 0)
            desk[x + 1, y + 1] = this
            this.x += 1
            this.y += 1
        }
        if (direction == "q" && x > 0 && y > 0 && desk[x - 1, y - 1].type == 0){
            desk[x, y] = GameField(0, 0, 0)
            desk[x - 1, y - 1] = this
            this.x -= 1
            this.y -= 1
        }
        if (direction == "w" && x < 7 && y > 0 && desk[x + 1, y - 1].type == 0){
            desk[x, y] = GameField(0, 0, 0)
            desk[x + 1, y - 1] = this
            this.x += 1
            this.y -= 1
        }
    }
}

fun main(){
    game()
}