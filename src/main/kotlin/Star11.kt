import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.floor

class Star11(override val input: String) : Star() {
    override fun part1(): String {
        val monkeyDis = input.split("\n\n")

        val monkeys = monkeyDis.map {
            getMonkey(it)
        }

        repeat(20) {
            monkeys.forEach { it.inspect(monkeys) }
        }

        return monkeys.map{it.processed}.sortedDescending()
            .take(2)
            .reduce { acc, i ->  acc * i }
            .toString()
    }

    override fun part2(): String {
        val monkeyDis = input.split("\n\n")

        val monkeys = monkeyDis.map {
            getMonkey2(it)
        }

        val tests = monkeys.map{it.test}
        monkeys.forEach{monkey->
            monkey.items.forEach{item->
                tests.forEach{item.addTest(it)}
            }
        }

        repeat(10000) {
            if(it == 1 || it == 20 || it == 1000 || it == 2000 || it ==3000) {
                println("After round $it")
                monkeys.forEachIndexed{i, m -> println("Monkey $i: ${m.processed}") }
                println("\n")
            }
            monkeys.forEach { it.inspect(monkeys) }
        }

        return monkeys.map{it.processed}.sortedDescending()
            .take(2)
            .reduce { acc, i ->  acc * i }
            .toString()    }

    fun getMonkey(data:String): Monkey {
        val lines = data.split('\n')
        val items = mutableListOf<Int>()
        var test = 1
        var tm = 0
        var fm = 0
        var op = { old:Int -> old}
        lines.map{it.trim()}.forEach { line ->
            val splits = line.split(":")
            if (splits[0] == "Starting items") {
                items.addAll(splits[1].split(",").map {it.trim().toInt()})
            } else if (splits[0] == "Operation"){
                op = getOp(splits[1])
            } else if (splits[0] == "Test"){
                test = splits[1].split(" ").last().toInt()
            } else if (splits[0] == "If true"){
                tm = splits[1].split(" ").last().toInt()
            } else if (splits[0] == "If false") {
                fm = splits[1].split(" ").last().toInt()
            }
        }
        return Monkey(op, test, tm,fm,items)
    }

    fun getMonkey2(data:String): BigMonkey {
        val lines = data.split('\n')
        val items = mutableListOf<Item>()
        var test = 1
        var tm = 0
        var fm = 0
        var op = { old:Int -> old}
        lines.map{it.trim()}.forEach { line ->
            val splits = line.split(":")
            if (splits[0] == "Starting items") {
                items.addAll(splits[1].split(",").map {Item(it.trim().toInt())})
            } else if (splits[0] == "Operation"){
                op = getOp(splits[1])
            } else if (splits[0] == "Test"){
                test = splits[1].split(" ").last().toInt()
            } else if (splits[0] == "If true"){
                tm = splits[1].split(" ").last().toInt()
            } else if (splits[0] == "If false") {
                fm = splits[1].split(" ").last().toInt()
            }
        }
        return BigMonkey(op, test, tm,fm,items)
    }

    fun getOp (opDef: String): (input: Int) -> Int{
        val equation = opDef.split("=")[1].trim().split(" ")

        if (equation[1] == "+") {
            return {t-> getValue(t, equation[0]) +  getValue(t, equation[2])}
        }
        return {t-> getValue(t, equation[0]) *  getValue(t, equation[2])}
    }

    fun getValue(old:Int, value:String):Int {
        if(value == "old") {
            return old
        }
        return value.toInt()
    }
}



class Monkey(val op: (input: Int) -> Int, val test: Int, val tmonkey: Int, val fmonkey: Int, val items: MutableList<Int>, var processed: Long = 0) {

    fun inspect(monkeys: List<Monkey>) {
        while(!items.isEmpty()) {
            val item = items.removeAt(0)
            var worry = op(item)
            worry /= 3
            if (worry % test == 0) {
                monkeys[tmonkey].receive(worry)
            } else {
                monkeys[fmonkey].receive(worry)
            }
            processed++
        }
    }

    fun receive(item: Int) {
        items.add(item)
    }
}

class BigMonkey(val op: (input: Int) -> Int, val test: Int, val tmonkey: Int, val fmonkey: Int, val items: MutableList<Item>, var processed: Long = 0) {

    fun inspect(monkeys: List<BigMonkey>) {
        while(!items.isEmpty()) {
            val citem = items.removeAt(0)
            citem.update(op)
            val worry:Int = citem.testers[test]!!
            if (worry == 0) {
                monkeys[tmonkey].receive(citem)
            } else {
                monkeys[fmonkey].receive(citem)
            }
            processed++
        }
    }

    fun receive(item: Item) {
        items.add(item)
    }
}

class Item(val initVal:Int , val testers:MutableMap<Int, Int> = mutableMapOf()) {
    fun addTest(test:Int) {
        testers.putIfAbsent(test, initVal%test)
    }
    fun update(op: (input: Int) -> Int) {
        testers.forEach {
            testers.put(it.key, op(it.value)%it.key)
        }
    }
}