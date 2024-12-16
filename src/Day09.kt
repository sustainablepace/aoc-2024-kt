
fun main() {
    fun part1(input: List<String>): Long {
        val diskMap = (input.first()+"0").chunked(2).map {
            it.chunked(1).map { it.toInt() }
        }.let {
            val chars = it.map { it.first() }.sum()
            it
        }.flatMapIndexed { index, (blocks, blanks) ->
            val l = MutableList(blocks) { index.toString() }
                l.addAll(MutableList(blanks) {"."})
            l
        }

        var backIndex = diskMap.size-1
        val compactedDiskMap = diskMap.mapIndexedNotNull { index, c ->
            if(index > backIndex) {
                null
            } else if(c != ".") {
                c
            } else {
                var b = diskMap[backIndex]
                backIndex--

                while(b == "." ) {
                    b = diskMap[backIndex]
                    backIndex--
                }
                b
            }
        }

        return compactedDiskMap.mapIndexed { index, c ->
            (c.toInt() * index).toLong()
        }.sum()
    }

    fun part2(input: List<String>): Long {
        return 0L
    }


    val testInput = readInput("Day09_test")

    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 1928L)

    val input = readInput("Day09")
    val result = part1(input)
    println(result)
    check(result == 6359213660505L)

    val testResult2 = part2(testInput)
    println(testResult2)
    check(testResult2 == 2858L)

    val result2 = part2(input)
    println(result2)
    check(result2 == TODO())
}
