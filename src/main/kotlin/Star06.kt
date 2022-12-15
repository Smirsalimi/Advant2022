import java.util.*

class Star06 (override val input: String) : Star() {

    fun findSignal(data: String, l: Int) :Int {
        var index = 0;
        var chars = HashMap<Char, Int>()
        while(index < data.length) {
            if(chars.containsKey(data[index])) {
                val start: Int = chars[data[index]]?.let { chars[data[index]] } ?: index
                chars = chars.filter { e ->
                    e.value > start
                } as HashMap<Char, Int>
                chars[data[index]] = index
            } else {
                chars[data[index]] = index
            }
            if (chars.size >= l) {
                return index+1
            }
            index++
        }
        return 0
    }

    override fun part1(): String {
//        var data = input.split('\n')
//        data.forEach {
//            println(findSignal(it.trim(), 4))
//        }
//
//        return "" + findSignal(data[0].trim(), 4)
        return ""
    }

    override fun part2(): String {
        var data = input.split('\n')
        data.forEach {
            println(findSignal(it.trim(), 14))
        }

        return "" + findSignal(data[0].trim(), 14)
    }
}