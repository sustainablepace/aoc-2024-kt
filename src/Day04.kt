fun List<String>.transpose() = indices.map { y ->
    indices.map { x -> this[x][y] }.joinToString("")
}

fun List<String>.diagonalsUp() = MutableList(size) { y ->
    var diagonal = ""
    (0..y).map { x ->
        diagonal += this[y - x][x]
    }
    diagonal
} + List(size - 1) { index ->
    var diagonal = ""
    val x = index + 1
    ((size - 1) downTo x).mapIndexed { i, y ->
        diagonal += this[y][x + i]
    }
    diagonal
}

fun List<String>.vertical() = transpose()
fun List<String>.diagonalsDown() = MutableList(size) { y ->
    var diagonal = ""
    (0..<size - y).map { x ->
        diagonal += this[y + x][x]
    }
    diagonal
} + List(size - 1) { index ->
    var diagonal = ""
    val x = index + 1
    (0..<size - x).mapIndexed { i, y ->
        diagonal += this[y][x + y]
    }
    diagonal
}


fun List<String>.countXmas() = sumOf { row ->
    xmas.findAll(row).count() + xmas.findAll(row.reversed()).count()
}

val xmas = Regex("XMAS")
fun main() {
    fun part1(input: List<String>): Int {
        val horizontal = input.countXmas()
        val vertical = input.vertical().countXmas()
        val diagonalsUp = input.diagonalsUp().countXmas()
        val diagonalsDown = input.diagonalsDown().countXmas()
        return horizontal + vertical + diagonalsUp + diagonalsDown
    }

    fun part2(input: List<String>): Int = (1..input.size - 2).flatMap { y ->
        (1..input.size - 2).mapNotNull { x ->
            if (input[y][x] == 'A') {
                x to y
            } else null
        }
    }.filter { (x, y) ->
        val diag1 = listOf(input[y - 1][x - 1], input[y + 1][x + 1])
        val diag2 = listOf(input[y - 1][x + 1], input[y + 1][x - 1])
        diag1.containsAll(listOf('M', 'S')) && diag2.containsAll(listOf('M', 'S'))
    }.size

    val testInput = readInput("Day04_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 18)

    val input = readInput("Day04")
    val result = part1(input)
    println(result)
    check(result == 2662)

    val testResult2 = part2(testInput)
    println(testResult2)
    check(testResult2 == 9)

    val result2 = part2(input)
    println(result2)
    check(result2 == 2034)
}
