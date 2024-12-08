fun MatchResult.getIndex() = groups.first()!!.range.first
fun MatchResult.multiply() = (groups[1]?.value?.toInt() ?: 0) * (groups[2]?.value?.toInt() ?: 0)
fun Sequence<Int>.closestTo(index: Int) = filter { it < index }.maxOrNull()


fun main() {

    val mulRegex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
    val doRegex = Regex("do\\(\\)")
    val dontRegex = Regex("don't\\(\\)")

    fun part1(input: List<String>): Int = listOf(input.joinToString("")).first().let { row ->
        mulRegex.findAll(row).sumOf { it.multiply() }
    }

    fun part2(input: List<String>): Int = listOf(input.joinToString("")).first().let { row ->
        val mulValues = mulRegex.findAll(row).let { mulMatches ->
            mulMatches.map(MatchResult::getIndex).zip(mulMatches.map(MatchResult::multiply))
        }
        val doIndices = doRegex.findAll(row).map(MatchResult::getIndex)
        val dontIndices = dontRegex.findAll(row).map(MatchResult::getIndex)

        mulValues.filter { (index, _) ->
            val doIndex = doIndices.closestTo(index)
            val dontIndex = dontIndices.closestTo(index)
            dontIndex == null || doIndex != null && doIndex > dontIndex
        }.sumOf { (_, value) -> value }
    }

    val testInput = readInput("Day03_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 161)

    val input = readInput("Day03")
    val result = part1(input)
    println(result)
    check(result == 174561379)

    val testResult2 = part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"))
    println(testResult2)
    check(testResult2 == 48)

    val result2 = part2(input)
    println(result2)
    check(result2 == 106921067)
}
