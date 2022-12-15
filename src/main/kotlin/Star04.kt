class Star04(override val input: String) : Star() {
    override fun part1(): String {
        return "" + input.split('\n').sumOf { containOne(it.split(",")) }
    }

    override fun part2(): String {
        return "" + input.split('\n').sumOf { overlap(it.split(",")) }

    }

    fun containOne(assignments: List<String> ): Int {
        val one = assignments[0].split("-")
        val two = assignments[1].split("-")
        if ((one[0].toInt() >= two[0].toInt() && one[1].toInt() <= two[1].toInt()) ||
            (one[0].toInt() <= two[0].toInt() && one[1].toInt() >= two[1].toInt()))
            return 1;
        return 0
    }

    fun overlap(assignments: List<String> ): Int {
        val one = assignments[0].split("-").map { it.toInt() }
        val two = assignments[1].split("-").map { it.toInt() }
        if ((one[0] >= two[0] && one[0] <= two[1]) ||
            (one[1] >= two[0] && one[1] <= two[1]) ||
            (two[0] >= one[0] && two[0] <= one[1]) ||
            (two[0] >= one[0] && two[0] <= one[1]))
            return 1
        return 0
    }
}