class Star13(override val input: String) : Star() {
    override fun part1(): String {
        val data = mutableListOf<Signal>()
        var i = 1
        var sum = 0
        input.split("\n\n").forEach { pair ->
            val data = mutableListOf<Signal>()
            pair.split("\n"). forEach {
                if(it != "") {
                    data.add(readSignal(it))
                }
            }
            if (Order.IN_ORDER == data[0].compareValues(data[1])) {
                sum+=i
            }
            i++
        }
        return "$sum"
    }

    override fun part2(): String {

        val decoders = mutableSetOf<Signal>(
            Signal(null, listOf(Signal(2, null))),
            Signal(null, listOf(Signal(6, null)))
        )
        val data = mutableListOf<Signal>()
        data.addAll(decoders)

        input.split("\n").forEach {
            if(it != "") {
                data.add(readSignal(it))
            }
        }
        data.sortWith(SignalComparator)
        var sum = 1
        data.forEachIndexed { i, s ->
            if(decoders.contains(s)) {
                sum *= (i+1)
            }
        }
        return "$sum"
    }


    fun readSignal(input:String):Signal {
        if (input[0] == '[') {
            val data = mutableListOf<Signal>()
            var i = 1
            while (i <input.length -1) {
                if(input[i].isDigit()) {
                    var s = ""
                    while (input[i].isDigit()) {
                        s += input[i]
                        i++
                    }
                    data.add(Signal(s.toInt(), null))
                } else if (input[i] == '['){
                    i++
                    var bc = 1
                    var ss = "["
                    while(i <input.length -1 && bc > 0) {
                        ss +=input[i]
                        if (input[i] == ']')
                            bc--
                        else if (input[i] == '[')
                            bc++
                        i++
                    }
                    data.add(readSignal(ss))
                } else {
                    i++
                }
            }

            return Signal(null, data)
        }

        return Signal(input.toInt(), null)
    }
}

enum class Order(val comparator: Int) {
    IN_ORDER(-1), OUT_ORDER(1), CONTINUE(0)
}

class Signal(val num:Int?, val lists: List<Signal>?) {
    fun compareValues(os:Signal): Order {
        if (num != null) {
            if (os.num != null) {
                if (num < os.num!!) {
                    return Order.IN_ORDER
                } else if (num == os.num) {
                    return Order.CONTINUE
                }
                return Order.OUT_ORDER
            } else {
                return Signal(null, listOf(this)).compareValues(os)
            }
        }
        if (os.lists != null) {
            lists!!.forEachIndexed { i, s ->
                if (os.lists.size <= i) {
                    return Order.OUT_ORDER
                }
                val check = s.compareValues(os.lists[i])
                if (check != Order.CONTINUE) {
                    return check
                }
            }
            if (os.lists.size > lists.size) {
                return Order.IN_ORDER
            }
            return Order.CONTINUE
        } else {
            return this.compareValues(Signal(null, listOf(os)))
        }

        return Order.OUT_ORDER
    }

}

class SignalComparator {
    companion object : Comparator<Signal> {

        override fun compare(a: Signal, b: Signal): Int {
            return a.compareValues(b).comparator
        }
    }
}