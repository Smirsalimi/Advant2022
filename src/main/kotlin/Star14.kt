import kotlin.math.abs


class Star14(override val input: String) : Star(){
    override fun part1(): String {
        val rocks = getRocks()
        val rockCount = rocks.size
        val depth = rocks.maxOf { it.second }
        val start = Pair(500,0)
        var sand = start
        while(sand.second <= depth) {
            if (!rocks.contains(Pair(sand.first, sand.second +1))) {
                sand = Pair(sand.first, sand.second +1)
            } else if (!rocks.contains(Pair(sand.first-1, sand.second +1))) {
                sand = Pair(sand.first-1, sand.second +1)
            } else if (!rocks.contains(Pair(sand.first+1, sand.second +1))) {
                sand = Pair(sand.first+1, sand.second +1)
            } else {
                rocks.add(sand)
                sand = start
            }
        }

        return "${rocks.size - rockCount}"
    }

    fun getRocks():MutableSet<Pair<Int, Int>> {
        var rocks = mutableSetOf<Pair<Int, Int>>()
        input.split("\n").forEach { line ->
            var lastRock: Pair<Int, Int>? = null
            line.split(" -> ")
                .map { it.trim() }
                .forEach {
                    val rock = pairOf(it.split(",").map { num -> num.trim().toInt()})
                    if(lastRock != null) {
                        if (lastRock!!.first != rock.first) {
                            var xStep = (rock.first - lastRock!!.first) / abs(rock.first - lastRock!!.first)
                            var xDiff = xStep
                            while (lastRock!!.first + xDiff != rock.first) {
                                rocks.add(Pair(lastRock!!.first + xDiff, lastRock!!.second))
                                xDiff += xStep
                            }
                        } else if (lastRock!!.second != rock.second) {
                            var yStep = (rock.second - lastRock!!.second) / abs(rock.second - lastRock!!.second)
                            var yDiff = yStep
                            while (lastRock!!.second + yDiff != rock.second) {
                                rocks.add(Pair(lastRock!!.first, lastRock!!.second + yDiff))
                                yDiff += yStep
                            }
                        }
                    }
                    rocks.add(rock)
                    lastRock = rock
                }
        }
        return rocks
    }

    fun pairOf(nums:List<Int>): Pair<Int, Int> {
        if(nums.size > 2) {
            throw RuntimeException("Not a Pair")
        }
        return Pair(nums[0], nums[1])
    }

    override fun part2(): String {
        val rocks = getRocks()
        val rockCount = rocks.size
        val depth = rocks.maxOf { it.second } + 2
        val start = Pair(500,0)
        var sand = start
        while(true) {
            if (sand.second + 1 == depth) {
                rocks.add(sand)
                sand = start
            } else if (!rocks.contains(Pair(sand.first, sand.second +1))) {
                sand = Pair(sand.first, sand.second +1)
            } else if (!rocks.contains(Pair(sand.first-1, sand.second +1))) {
                sand = Pair(sand.first-1, sand.second +1)
            } else if (!rocks.contains(Pair(sand.first+1, sand.second +1))) {
                sand = Pair(sand.first+1, sand.second +1)
            } else {
                rocks.add(sand)
                if (sand == start) {
                    break
                }
                sand = start
            }
        }

        return "${rocks.size - rockCount}"
    }
}