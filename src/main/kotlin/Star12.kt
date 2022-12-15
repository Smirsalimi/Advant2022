import kotlin.math.abs

class Star12(override val input: String) : Star() {
    override fun part1(): String {
        val map = input.split("\n")
        var start = findStart()

        val dists = mutableMapOf<Pair<Int, Int>,Int>()
        dists[start] = 0
        val visited = mutableListOf(start)
        val s = shortestPath2(map, dists, visited)
        printMap(dists)
        return "$s"
    }

    fun checkDistance(map:List<String>, current:Pair<Int, Int>, next: Pair<Int, Int>): Boolean {
        return getVal(map, next) - getVal(map,current) <= 1
    }

    fun findStart(): Pair<Int,Int> {
        input.split("\n").forEachIndexed { y, line ->
            val sFind = line.indexOf('S')
            if (sFind >= 0) {
                return Pair(sFind, y)
            }
        }
        return Pair(0,0)
    }
    fun shortestPath2(map:List<String>,
                     dists:MutableMap<Pair<Int, Int>,Int>,
                     visited:MutableList<Pair<Int,Int>>): Int {
        while(visited.isNotEmpty()) {
            val current = visited.removeFirst()
            val dist = dists[current]!!
            Dir.values().forEach {
                val next = move(current, it)
                if (next.first < 0
                    || next.first >= map[0].length
                    || next.second < 0
                    || next.second >= map.size
                    || dists.containsKey(next)
                    || !checkDistance(map, current, next)) {

                    //do nothing
                } else {
                    if(map[next.second][next.first] == 'E') {
                        return dist +1
                    }
                    dists[next] = dist + 1
                    visited.add(next)
                }
            }
        }
        return -1
    }

    fun getVal(map:List<String>, current:Pair<Int, Int>): Char {
        val value = map[current.second][current.first]
        if (value == 'E') {
            return 'z'
        } else if (value == 'S') {
            return 'a'
        }
        return map[current.second][current.first]
    }

    fun move ( p:Pair<Int, Int>, dir:Dir) :  Pair<Int, Int> {
        return Pair(p.first + dir.move.first, p.second + dir.move.second)
    }

    override fun part2(): String {
        val map = input.split("\n")
        val paths = mutableListOf<Int>()
        map.forEachIndexed {y, line ->
            line.forEachIndexed { x, c ->
                if(c == 'a' || c == 'S') {
                    val dists = mutableMapOf<Pair<Int, Int>,Int>()
                    dists[Pair(x,y)] = 0
                    val visited = mutableListOf(Pair(x,y))
                    paths.add(shortestPath2(map, dists, visited))
                }
            }
        }


        return "" + paths.filter { it>=0 }.minOrNull()
    }
}

fun printMap(visited:MutableMap<Pair<Int, Int>,Int>){
    for(i in 0 until 5) {
        for(j in 0 until 8) {
            var t = visited.getOrDefault(Pair(j, i), 0)
            val x = if (t == Int.MAX_VALUE) {
                "X"
            } else {
                "$t"
            }
            print("\t$x")
        }
        println()
    }
}

enum class Dir(val move: Pair<Int, Int>) {
    LEFT(Pair(-1,0)),
    RIGHT(Pair(1,0)),
    UP(Pair(0,-1)),
    DOWN(Pair(0,1))
}