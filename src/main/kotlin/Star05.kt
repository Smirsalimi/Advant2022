import java.util.*
import javax.xml.stream.events.ProcessingInstruction
import kotlin.collections.ArrayList

class Star05(override val input: String) :Star() {
    override fun part1(): String {
        var data = input.split("\n\n")
        val inpCrate = data[0].split('\n')
        val crateC = inpCrate.last().last().digitToInt()
        var crates = ArrayList<ArrayList<String>>(crateC)
        for (i in 1..crateC) {
            crates.add(ArrayList<String>())
        }

        inpCrate.forEachIndexed(){index, element ->
            if( index != inpCrate.size - 1) {
                var i =0
                while(i < element.length) {
                    var n = getVal(element.substring(i,i+3))
                    if(!n.isEmpty()) {
                        crates[i/4].add(n)
                    }
                    i+=4
                }
            }
        }
        for (i in 0 until crateC) {
            crates[i].reverse()
        }

        data[1].split('\n').forEach {
            moveItem(crates, it)
        }
        var result:String = ""
        for (i in 0 until crateC) {
            if(crates[i].isNotEmpty())
                result += crates[i].last()
        }
        return result
    }

    override fun part2(): String {
        var data = input.split("\n\n")
        val inpCrate = data[0].split('\n')
        val crateC = inpCrate.last().last().digitToInt()
        var crates = ArrayList<kotlin.collections.ArrayList<String>>(crateC)
        for (i in 1..crateC) {
            crates.add(ArrayList<String>())
        }

        inpCrate.forEachIndexed(){index, element ->
            if( index != inpCrate.size - 1) {
                var i =0
                while(i < element.length) {
                    var n = getVal(element.substring(i,i+3))
                    if(!n.isEmpty()) {
                        crates[i/4].add(n)
                    }
                    i+=4
                }
            }
        }
        for (i in 0 until crateC) {
            crates[i].reverse()
        }

        data[1].split('\n').forEach {
            moveItem2(crates, it)
        }
        var result:String = ""
        for (i in 0 until crateC) {
            if(crates[i].isNotEmpty())
                result += crates[i].last()
        }
        return result
    }

    fun getVal(data:String): String{
        if (data == " " || data == "" || data.trim().all { Character.isDigit(it) }) {
            return ""
        }
        return data.replace("[", "").replace("]", "")
    }

    fun moveItem(crates:List<ArrayList<String>>, instruction: String) {
        val moves = instruction.split(' ')
        for( i in 0 until moves[1].trim().toInt()) {
            printCrates(crates)
            val item = crates[moves[3].trim().toInt()-1].removeLast()
            crates[moves[5].trim().toInt()-1].add(item)
            print("\n\n\n")
        }
    }

    fun moveItem2(crates:List<ArrayList<String>>, instruction: String) {
        val moves = instruction.split(' ')
        var cratesToMove = ArrayList<String>()
        for( i in 0 until moves[1].trim().toInt()) {
            printCrates(crates)
            val item = crates[moves[3].trim().toInt()-1].removeLast()
            cratesToMove.add(item)
            print("\n\n\n")
        }
        crates[moves[5].trim().toInt()-1].addAll(cratesToMove.reversed())
    }


    fun printCrates (crates:List<ArrayList<String>>) {
        for (i in 0 until 6) {
            var line = ""
            for (j in crates.indices) {
                if (crates[j].size > i) {
                    line += crates[j][i]+ " "
                }
            }
            print(line)
            print("\n")
        }
    }
}