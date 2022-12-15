import java.io.File
import java.nio.file.Paths
import java.util.*

class Star01(override val input: String) : Star() {
    fun solve(part: Int): Int {

        var count= 0
        val pq = PriorityQueue<Int> {t1: Int, t2 : Int -> t2 - t1}

        input.split('\n').forEach {
            if (it.isEmpty()) {
                pq.add(count)
                count = 0
            } else {
                count += it.toInt()
            }
        }
        pq.add(count)

        return if ( part == 1) pq.poll()
        else pq.poll() + pq.poll() + pq.poll()
    }

    override fun part1(): String {
        return "" + solve(1)
    }

    override fun part2(): String {
        return "" + solve(2)
    }
}