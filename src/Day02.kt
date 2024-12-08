import kotlin.math.abs

typealias Report = List<Int>
typealias LevelPair = Pair<Level, Level>
typealias Level = Int

fun LevelPair.isDifferenceSafe() = abs(first - second) in 1..3
fun LevelPair.isAscendingAndSafe() = first < second && isDifferenceSafe()
fun LevelPair.isDescendingAndSafe() = first > second && isDifferenceSafe()

fun Report.isSafe(): Boolean = zipWithNext().let { levelPairs ->
    levelPairs.all { it.isAscendingAndSafe() } || levelPairs.all { it.isDescendingAndSafe() }
}

fun Report.isSafeWithDampener(): Boolean = indices.any { index ->
    val mutableReport = toMutableList()
    mutableReport.removeAt(index)
    mutableReport.toList().isSafe()
}

fun main() {
    fun part1(input: List<String>): Int = input.map {
        it.split(" ").map { level -> level.toInt() }
    }.count { it.isSafe() }

    fun part2(input: List<String>): Int = input.map {
        it.split(" ").map { level -> level.toInt() }
    }.count {
        it.isSafeWithDampener()
    }

    val testInput = readInput("Day02_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 2)

    val input = readInput("Day02")
    val result = part1(input)
    println(result)
    check(result == 230)

    val testResult2 = part2(testInput)
    println(testResult2)
    check(testResult2 == 4)

    val result2 = part2(input)
    println(result2)
    check(result2 == 301)
}
