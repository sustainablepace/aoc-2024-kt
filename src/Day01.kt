import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int = input.map { row ->
        row.split("   ").map { entry ->
            entry.toInt()
        }
    }.let { rows ->
        (rows to rows).let { (l, r) ->
            l.map { it.first() }.sorted() to
            r.map { it.last() }.sorted()
        }.let { (left, right) ->
            left.zip(right).sumOf { (l, r) -> abs(l-r) }
        }
    }

    fun part2(input: List<String>): Int = input.map { row ->
        row.split("   ").map { entry ->
            entry.toInt()
        }
    }.let { rows ->
        (rows to rows).let { (l, r) ->
            l.map { it.first() } to r.map { it.last() }
        }.let { (left, right) ->
            left.sumOf { leftEntry -> right.count { it == leftEntry } * leftEntry }
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    val result = part1(input)
    println(result)
    check(result == 1603498)

    part2(input).println()
}
