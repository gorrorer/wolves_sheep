fun createLevel(condition: Condition): List<Condition> {

    val listOfConditions = mutableListOf<Condition>()

    for (wolf in condition.listOfWolves) {
        for (dir in wolvesDirects) {
            if (wolf.checkMove(dir, condition.field)) {
                wolf.move(dir, condition.field)
                listOfConditions.add(Condition(condition.field.clone(), condition.listOfWolves.indexOf(wolf)
                    , dir, condition.listOfWolves.clone(), condition.sheep.clone(), mutableListOf()))
                wolf.backStep(condition.field)
            }
        }
    }
    return listOfConditions
}

fun createResultsTree(): List<Condition> {

    val tree = createLevel(Condition(deskCop, 0, "", listOfWolvesCop, sheepCop, mutableListOf()))
    var sheepCopy: GameField
    var deskCopy: Matrix<GameField>

    for (i in 0 until tree.size)
        for (dir in moveSet) {
            sheepCopy = tree[i].sheep.clone()
            deskCopy = tree[i].field.clone()
            sheepCopy.move(dir, deskCopy)
            tree[i].children.add(createLevel(Condition(deskCopy, 0, "",
                tree[i].listOfWolves.clone(), sheepCopy, mutableListOf())).toMutableList())
        }
    return tree
}

fun chooseMoveAlt(): Pair<Int, String> {

    val resultsTree = createResultsTree()
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
