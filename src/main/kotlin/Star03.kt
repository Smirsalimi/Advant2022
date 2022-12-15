class Star03(override val input: String) : Star() {
    override fun part1(): String {
        var total = 0
        input.split('\n')
            .forEach {
                val split = it.length
                val firstHalf = HashSet<Char>()
                var i = 0
                while(i < it.length/2) {
                    firstHalf.add(it[i])
                    i++
                }
                while(i< it.length){
                    if (firstHalf.contains(it[i])) {
                        total += calcVal(it[i])
                        break
                    }
                    i++
                }
            }
        return "$total"
    }

    override fun part2(): String {
        var total = 0
        val data = input.split('\n')
        for (i in data.indices step 3) {
            val badge = data[i].toSet() intersect data[i+1].toSet() intersect data[i+2].toSet()
            total += calcVal(badge.first())
        }
        return "$total"
    }

    fun calcVal(c: Char): Int {
        var total = c.toLowerCase() - 'a' + 1
        if (c.isUpperCase()) {
            total += 26
        }
        return total
    }
}