fun testPermutation(sum: Long, testValue: Long, remainingList: List<Long>, includeConcat: Boolean) = when {
    sum > testValue -> false // already too large, can never equal test value
    sum < testValue && remainingList.isEmpty() -> false // no more numbers left, can never equal test value
    sum == testValue && remainingList.isEmpty() -> true // match test value, and cannot exceed it
    else -> isEquationSolvable(remainingList.toList(), testValue, includeConcat, sum) // dig deeper
}

fun evaluateOperatorPermutations(
    pl: Long,
    mu: Long,
    co: Long,
    testValue: Long,
    tail: List<Long>,
    includeConcat: Boolean
) =
    testPermutation(pl, testValue, tail, includeConcat) ||
            testPermutation(mu, testValue, tail, includeConcat) ||
            includeConcat && testPermutation(co, testValue, tail, includeConcat)

fun isEquationSolvable(
    numbers: List<Long>,
    testValue: Long,
    includeConcat: Boolean = false,
    sum: Long = 0L
): Boolean {
    val tail = numbers.toMutableList()
    val head = tail.removeFirst()
    return if (sum == 0L) {
        evaluateOperatorPermutations(head, head, head, testValue, tail.toList(), includeConcat)
    } else {
        evaluateOperatorPermutations(
            sum + head,
            sum * head,
            (sum.toString() + head.toString()).toLong(),
            testValue,
            tail.toList(),
            includeConcat
        )
    }
}

fun totalCalibrationResult(input: List<String>, includeConcat: Boolean) = input.map { row ->
    row.split(": ").let { (l, r) ->
        l.toLong() to r.split(" ").map(String::toLong)
    }
}.filter { (testValue, remainingNumbers) ->
    isEquationSolvable(remainingNumbers, testValue, includeConcat)
}.sumOf { (testValue, _) ->
    testValue
}

fun main() {
    fun part1(input: List<String>): Long = totalCalibrationResult(input, false)
    fun part2(input: List<String>): Long = totalCalibrationResult(input, true)

    val testInput = readInput("Day07_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 3749L)

    val input = readInput("Day07")
    val result = part1(input)
    println(result)
    check(result == 3119088655389L)

    val testResult2 = part2(testInput)
    println(testResult2)
    check(testResult2 == 11387L)

    val result2 = part2(input)
    println(result2)
    check(result2 == 264184041398847L)
}
