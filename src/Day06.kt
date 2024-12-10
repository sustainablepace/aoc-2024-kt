import Direction.*

enum class Direction {
    N, S, W, E
}

fun List<String>.findGuard() = mapIndexedNotNull { y, s ->
    s.mapIndexedNotNull { x, c ->
        when (c) {
            '^' -> Coordinates(x, y)
            else -> null
        }
    }
}.flatten().first()

fun List<String>.findObstacles() = mapIndexedNotNull { y, s ->
    s.mapIndexedNotNull { x, c ->
        when (c) {
            '#' -> Coordinates(x, y)
            else -> null
        }
    }
}.flatten()

data class Coordinates(val x: Int, val y: Int)
data class Guard(val direction: Direction, val coordinates: Coordinates)

fun Guard.movesTo(option: Coordinates): Guard =
    Guard(
        when (direction) {
            N -> E
            S -> W
            W -> N
            E -> S
        }, when (direction) {
            N -> Coordinates(option.x, option.y + 1)
            S -> Coordinates(option.x, option.y - 1)
            W -> Coordinates(option.x + 1, option.y)
            E -> Coordinates(option.x - 1, option.y)
        }
    )

fun Guard.fieldsOnWayToObstacle(obstacle: Coordinates): List<Guard> = when (direction) {
    N -> (coordinates.y downTo obstacle.y + 1).map { Coordinates(coordinates.x, it) }
    S -> (coordinates.y..<obstacle.y).map { Coordinates(coordinates.x, it) }
    W -> (coordinates.x downTo obstacle.x + 1).map { Coordinates(it, coordinates.y) }
    E -> (coordinates.x..<obstacle.x).map { Coordinates(it, coordinates.y) }
}.map { Guard(direction, it) }

fun Guard.fieldsOnWayToBorder(dimensions: Coordinates): List<Guard> = when (direction) {
    N -> (coordinates.y downTo 0).map { Coordinates(coordinates.x, it) }
    S -> (coordinates.y..<dimensions.y).map { Coordinates(coordinates.x, it) }
    W -> (coordinates.x downTo 0).map { Coordinates(it, coordinates.y) }
    E -> (coordinates.x..<dimensions.x).map { Coordinates(it, coordinates.y) }
}.map { Guard(direction, it) }

fun Guard.findNextObstacle(obstaclesX: Map<Int, List<Coordinates>>, obstaclesY: Map<Int, List<Coordinates>>): Coordinates? = when (direction) {
    N -> obstaclesX[coordinates.x]?.filter { it.y < coordinates.y }?.maxByOrNull { it.y }
    S -> obstaclesX[coordinates.x]?.filter { it.y > coordinates.y }?.minByOrNull { it.y }
    W -> obstaclesY[coordinates.y]?.filter { it.x < coordinates.x }?.maxByOrNull { it.x }
    E -> obstaclesY[coordinates.y]?.filter { it.x > coordinates.x }?.minByOrNull { it.x }
}

data class Walk(
    val obstacles: List<Coordinates>,
    val dimensions: Coordinates,
    var guard: Guard,
    val fieldsVisited: MutableList<Guard> = mutableListOf()
) {

    fun start(): Boolean {
        var guardLeftArea = false
        var obstaclesVisited = mutableListOf<Guard>()
        val obstaclesX = mutableMapOf<Int, MutableList<Coordinates>>()
        val obstaclesY = mutableMapOf<Int, MutableList<Coordinates>>()

        obstacles.forEach {
            if(obstaclesX[it.x] == null) {
                obstaclesX[it.x] = mutableListOf(it)
            } else {
                obstaclesX[it.x]?.add(it)
            }
            if(obstaclesY[it.y] == null) {
                obstaclesY[it.y] = mutableListOf(it)
            } else {
                obstaclesY[it.y]?.add(it)
            }
        }
        while (!guardLeftArea) {
            val obstacle = guard.findNextObstacle(obstaclesX, obstaclesY)
            val newFields = if (obstacle == null) {
                guard.fieldsOnWayToBorder(dimensions)
            } else {
                guard.fieldsOnWayToObstacle(obstacle)
            }
            fieldsVisited.addAll(newFields)

            if (obstacle != null) {
                guard = guard.movesTo(obstacle)
                if(obstaclesVisited.contains(guard)) {
                    return false
                }
                obstaclesVisited.add(guard)
            } else {
                guardLeftArea = true
            }
        }
        return true
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val dimensions = Coordinates(input.first().length, input.size)
        val obstacles = input.findObstacles()
        val guard = Guard(N, input.findGuard())

        val walk = Walk(obstacles, dimensions, guard)
        walk.start()
        return walk.fieldsVisited.distinctBy { it.coordinates }.count()
    }

    fun part2(input: List<String>): Int {
        val dimensions = Coordinates(input.first().length, input.size)
        val obstacles = input.findObstacles()
        val guard = Guard(N, input.findGuard())

        val walk = Walk(obstacles, dimensions, guard)
        walk.start()

        val additionalObstacles =
            walk.fieldsVisited.map { it.coordinates }.distinct().toMutableList().minus(guard.coordinates)

        return additionalObstacles.count {
            val o = obstacles.toMutableList()
            o.add(it)
            !Walk(o, dimensions, guard).start()
        }
    }

    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 41)

    val result = part1(input)
    println(result)
    check(result == 5312)

    val testResult2 = part2(testInput)
    println(testResult2)
    check(testResult2 == 6)

    val result2 = part2(input)
    println(result2)
    check(result2 == 1748)
}
