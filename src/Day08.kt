
fun main() {
    fun part1(input: List<String>): Int {
        val beacons = input.mapIndexedNotNull { y, s ->
            s.mapIndexedNotNull { x, c ->
                if(c != '.') {
                    c to (x to y)
                } else null
            }
        }.flatten()
        return beacons.groupBy { it.first }.map { group ->
            val beacons = group.value.map { it.second }
            beacons.map { beacon1 ->
                beacons.mapNotNull { beacon2 ->
                    if(beacon1 == beacon2) {
                        null
                    } else {
                        val vector: Pair<Int, Int> = (beacon1.first - beacon2.first) to (beacon1.second - beacon2.second)
                        val antinode = beacon1.first+vector.first to beacon1.second+vector.second
                        if(antinode.first < 0 || antinode.second < 0 || antinode.first >= input.first().length || antinode.second >= input.size) {
                            null
                        } else antinode
                    }
                }
            }.flatten()
        }.flatten().distinct().count()
    }

    fun part2(input: List<String>): Int  {
        val beacons = input.mapIndexedNotNull { y, s ->
            s.mapIndexedNotNull { x, c ->
                if(c != '.') {
                    c to (x to y)
                } else null
            }
        }.flatten()
        val b =  beacons.groupBy { it.first }.map { group ->
            val beacons = group.value.map { it.second }
            val c = beacons.flatMap { beacon1 ->
                beacons.mapNotNull { beacon2 ->
                    if(beacon1 == beacon2) {
                        null
                    } else {
                        val vector: Pair<Int, Int> = (beacon1.first - beacon2.first) to (beacon1.second - beacon2.second)
                        val antinodes = mutableListOf<Pair<Int, Int>>()
                        var i = 0
                        do {
                            var antinodeInRange = true
                            val antinode = beacon1.first+vector.first*i to beacon1.second+vector.second*i
                            if(antinode.first < 0 || antinode.second < 0 || antinode.first >= input.first().length || antinode.second >= input.size) {
                                antinodeInRange = false
                            } else {
                                antinodes.add(antinode)
                            }
                            i++

                        } while (antinodeInRange)
                        antinodes
                    }
                }
            }.flatten()
            c
        }.flatten().distinct()
            return b.count()
    }


    val testInput = readInput("Day08_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 14)

    val input = readInput("Day08")
    val result = part1(input)
    println(result)
    check(result == 379)

    val testResult2 = part2(testInput)
    println(testResult2)
    check(testResult2 == 34)

    val result2 = part2(input)
    println(result2)
    check(result2 == 1339)
}
