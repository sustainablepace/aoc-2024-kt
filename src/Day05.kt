typealias PageOrderingRules = List<PageOrderingRule>
typealias PageOrderingRule = List<PageNumber>
typealias PageNumber = Int
typealias Update = List<PageNumber>

fun List<Update>.addMiddleNumbers() = sumOf {
    val index = (it.size - 1) / 2
    it[index]
}

fun main() {
    fun part1(input: List<String>): Int = input.joinToString("\n").split("\n\n").let { (a, b) ->
        val pageOrderingRules = a.split("\n").map { it.split("|").map(String::toInt) }
        val updates = b.split("\n").map { it.split(",").map(String::toInt) }

        updates.filter { update ->
            pageOrderingRules.filter {
                update.containsAll(it)
            }.all { (a, b) ->
                update.indexOf(a) < update.indexOf(b)
            }
        }.addMiddleNumbers()
    }

    fun part2(input: List<String>): Int = input.joinToString("\n").split("\n\n").let { (a, b) ->
        val pageOrderingRules = a.split("\n").map { it.split("|").map(String::toInt) }
        val updates = b.split("\n").map { it.split(",").map(String::toInt) }

        updates.filterNot { update ->
            pageOrderingRules.filter {
                update.containsAll(it)
            }.all { (a, b) ->
                update.indexOf(a) < update.indexOf(b)
            }
        }.map { update ->
            update.sortedWith { a, b ->
                pageOrderingRules.find { it.contains(a) && it.contains(b) }?.let { (l, r) ->
                    when (a) {
                        l -> -1
                        r -> 1
                        else -> 0
                    }
                } ?: 0
            }
        }.addMiddleNumbers()
    }

    val testInput = readInput("Day05_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 143)

    val input = readInput("Day05")
    val result = part1(input)
    println(result)
    check(result == 6498)

    val testResult2 = part2(testInput)
    println(testResult2)
    check(testResult2 == 123)

    val result2 = part2(input)
    println(result2)
    check(result2 == 5017)
}
