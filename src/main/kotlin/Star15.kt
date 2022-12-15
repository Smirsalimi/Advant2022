import kotlin.math.abs

class Star15(override val input: String) : Star() {
    override fun part1(): String {
        val sensors = readInput()
        val beacons = sensors.map{it.beacon}
        val allpos = mutableListOf<Pair<Int,Int>>()
        sensors.forEach {
            allpos.add(it.pos)
            allpos.add(it.beacon)
        }

        val maxX = allpos.maxOf { it.first }
        val minX = allpos.minOf { it.first }
        val maxD = sensors.maxOf { it.md }
        val row = 2000000
        var sum = 0
        for (i in minX-maxD until maxX + maxD) {
            val cur = Pair(i, row)
            var beacon = true
            sensors.forEach {
                if(getdistance(it.pos, cur) <= it.md && !beacons.contains(cur)) {
                    beacon = false
                }
            }
            if(!beacon) {
                sum++
            }
        }

        return "$sum"
    }

    override fun part2(): String {
        val sensors = readInput()
        val max = 4000000

        for (i in 0 .. max) {
            var ranges = sensors.map{Pair((it.pos.first - (it.md - abs(it.pos.second - i))),
                    (it.pos.first + (it.md - abs(it.pos.second - i))))
            }
            val check = addRanges(ranges, max)
            if(check > 0) {
                return "$check, $i"
            }
        }
        return ""
    }

    private fun getdistance(pair1: Pair<Int,Int>, pair2: Pair<Int,Int>):Int {
        return abs(pair2.first-pair1.first) + abs(pair2.second-pair1.second)
    }

    private fun readInput(): List<Sensor> {
        val sensors = mutableListOf<Sensor>()
        input.split('\n').forEach {line ->
            val parts = line.split(":")
            val sensorPos = parts[0].split(",").map{it.split("=").last().trim().toInt()}
            val bPos = parts[1].split(",").map{it.split("=").last().trim().toInt()}
            sensors.add(
                Sensor(Pair(sensorPos[0], sensorPos[1]),
                    getdistance(Pair(sensorPos[0], sensorPos[1]), Pair(bPos[0], bPos[1])),
                    Pair(bPos[0], bPos[1]))
            )
        }

        return sensors
    }

    private fun addRanges(ranges:List<Pair<Int,Int>>, max:Int ) :Int{
        val sortedList = mutableListOf<Pair<Int,Int>>()
        sortedList.addAll(ranges.sortedBy { it.first })
        var cur = sortedList.removeFirst()
        while(sortedList.isNotEmpty() && cur.second< max) {
            val next = sortedList.removeFirst()
            if(cur.second >= next.first) {
                cur = Pair(cur.first, maxOf(cur.second, next.second))
            } else {
                return cur.second + 1
            }
        }
        return -1
    }
}

class Sensor(val pos:Pair<Int,Int>, val md:Int, val beacon:Pair<Int, Int>)