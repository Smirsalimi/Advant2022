import java.lang.Math.abs

class Star09(override val input: String) : Star() {

    fun dirs(dir:String): Pair<Int,Int> {
        if (dir == "R") {
            return Pair(1,0)
        }
        if (dir == "L") {
            return Pair(-1,0)
        }
        if (dir == "U") {
            return Pair(0,1)
        }
        if (dir == "D") {
            return Pair(0, -1)
        }
        if (dir == "DR") {
            return Pair(1, -1)
        }
        if (dir == "DL") {
            return Pair(-1, -1)
        }
        if (dir == "UR") {
            return Pair(1, 1)
        }
        //"UL"
        return Pair(-1, 1)

    }

    override fun part1(): String {
        var seen = HashSet<Pair<Int, Int>>()
        var h = Pair(0,0)
        var t = Pair(0,0)
        seen.add(t)
        input.split("\n") .forEach {
            val movement = it.split(" ")
            repeat(movement[1].toInt()) {
                h = move(h, dirs(movement[0]))
                t = moveTailMove(h, t)
                println(t.toString() + " " + h.toString())
                seen.add(t)
            }
        }
        return seen.size.toString()
    }

    fun moveTailMove(h:Pair<Int, Int>, t:Pair<Int, Int>):Pair<Int, Int> {

        if(!tooFar(h,t)) {
            return t
        }
        if (h.first == t.first) {
            if((h.second - t.second) > 1) {
                return move(t,dirs("U"))
            } else if((h.second - t.second) < -1) {
                return move(t,dirs("D"))
            }
        } else  if (h.second == t.second) {
            if((h.first - t.first) > 1) {
                return move(t,dirs("R"))
            } else if((h.first - t.first) < -1) {
                return move(t,dirs("L"))
            }
        } else {
            if (h.first > t.first) {
                if(h.second > t.second) {
                    return move(t,dirs("UR"))
                } else {
                    return move(t,dirs("DR"))
                }
            } else{
                if(h.second > t.second) {
                    return move(t,dirs("UL"))
                } else {
                    return move(t,dirs("DL"))
                }
            }
        }
        return t
    }

    fun tooFar(h:Pair<Int, Int>, t:Pair<Int, Int>):Boolean {

        if (h.first == t.first) {
            return kotlin.math.abs(h.second - t.second) > 1
        }
        if (h.second == t.second) {
            return kotlin.math.abs(h.first - t.first) > 1
        }

        return  (kotlin.math.abs(h.first - t.first) > 1) || kotlin.math.abs(h.second - t.second) > 1
    }

    fun move ( p:Pair<Int, Int>, dir:Pair<Int, Int>) :  Pair<Int, Int> {
        return Pair(p.first + dir.first, p.second + dir.second)
    }

    override fun part2(): String {
        var seen = HashSet<Pair<Int, Int>>()
        var h = Pair(0,0)
        var tails = ArrayList<Pair<Int,Int>> ()
        repeat(9) {
            tails.add(Pair(0,0))
        }
        seen.add(tails[0])
        input.split("\n") .forEach {
            val movement = it.split(" ")
            repeat(movement[1].toInt()) {
                h = move(h, dirs(movement[0]))
                tails[0] = moveTailMove(h, tails[0])
                for (i in 1 until tails.size) {
                    tails[i] = moveTailMove(tails[i-1], tails[i])
                }
                seen.add(tails[8])
            }
        }
        return seen.size.toString()
    }

}