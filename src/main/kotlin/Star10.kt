class Star10(override val input: String) : Star() {
    override fun part1(): String {
        var data = input.split("\n")
        val imps = setOf(20, 60, 100, 140,180, 220)
        var cycle = 1
        var reg = 1
        var sum = 0
        data.forEach {
            val line = it.split(" ")
            if (line[0] == "noop") {
                cycle++
            } else {
                cycle++
                if (cycle in imps) {
                    sum+= reg * cycle
                    println(cycle.toString() + "a: " + reg * cycle)
                }
                cycle++

                reg+=line[1].toInt()
            }
            if (cycle in imps) {
                sum+= reg * cycle
                println(cycle.toString()  + "b: " + reg * cycle)
            }
        }
        return sum.toString()
    }

    override fun part2(): String {
        var data = input.split("\n")
        var cycle = 1
        var reg = 1
        var sum = 0
        print("#")
        data.forEach {
            val line = it.split(" ")

            if (line[0] == "noop") {
                cycle++
            } else {
                cycle++

                if(cycle%40 == reg || cycle%40 == reg+1 || cycle%40 == reg+2)
                    print("O")
                else
                    print(".")
                if(cycle%40 == 0)
                    println()
                cycle++
                reg+=line[1].toInt()
            }
            if(cycle%40 == reg || cycle%40 == reg+1 || cycle%40 == reg+2)
                print("O")
            else
                print(".")
            if(cycle%40 == 0)
                println()
        }
        return sum.toString()
    }

}