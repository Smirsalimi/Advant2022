import java.io.File
import java.nio.file.Paths

class Star02(override val input: String) : Star() {
    fun solve(part: Int): Int {
        var total = 0
        input.split('\n').forEach {
            val hands = it.split(" ")
            total += calcScore(hands, part)
        }
        return total
    }

    private fun calcScore(hands: List<String>, part: Int): Int {
        if (part == 1) {
            return getValuePart1(hands[1]) + getRoundScorePart1(hands)
        }
        return getValuePart2(hands[1]) + getRoundScorePart2(hands)
    }

    private fun getRoundScorePart1(hand: List<String>): Int {
        return when (hand[0]) {
            "A" -> when (hand[1]) {
                "X" -> 3
                "Y" -> 6
                else -> {0}
            }
            "B" -> when (hand[1]) {
                "X" -> 0
                "Y" -> 3
                else -> {
                    6
                }
            } else -> {
                when (hand[1]) {
                    "X" -> 6
                    "Y" -> 0
                    else -> {3}
                }
            }
        }
    }

    private fun getValuePart1(hand: String): Int {
        return when (hand) {
            "X" -> 1
            "Y" -> 2
            else -> { // Note the block
                3
            }
        }
    }
    private fun getRoundScorePart2(hand: List<String>): Int {
        return when (hand[0]) {
            "A" -> when (hand[1]) {
                "X" -> 3
                "Y" -> 1
                else -> {2}
            }
            "B" -> when (hand[1]) {
                "X" -> 1
                "Y" -> 2
                else -> {
                    3
                }
            } else -> {
                when (hand[1]) {
                    "X" -> 2
                    "Y" -> 3
                    else -> {1}
                }
            }
        }
    }

    private fun getValuePart2(hand: String): Int {
        return when (hand) {
            "X" -> 0
            "Y" -> 3
            else -> { // Note the block
                6
            }
        }
    }

    override fun part1(): String {
        return "" + solve(1)
    }

    override fun part2(): String {
        return "" + solve(2)
    }
}