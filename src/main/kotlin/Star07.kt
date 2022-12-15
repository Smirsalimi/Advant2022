import javax.xml.stream.events.Characters

class Star07 (override val input: String) : Star() {

    override fun part1(): String {
        return "" + parseResults(input.split("\n")).values.filter { it <= 100000 }.sum()
    }

    override fun part2(): String {
        val results =  parseResults(input.split("\n"))
        var total:Long = 70000000 - results.getOrDefault("/", 0)
        return "" + parseResults(input.split("\n")).values.filter { it >= 30000000 - total }.minOrNull()
    }

    fun parseResults(data:List<String>) : HashMap<String, Long>{
        var i = 0
        val dirs = ArrayList<String>()
        val dirSizes = HashMap<String, ArrayList<String>>()

        while( i < data.size) {
            var line = data[i].split(' ')
            if(line[0].trim() == "$") {
                if (line[1].trim() == "cd") {
                    if(line[2].trim() == "..") {
                        dirs.removeLast()
                    } else {
                        dirs.add(line[2].trim())
                    }
                }
            }
            else {
                var dir = dirSizes.getOrPut(getDirName(dirs)) {ArrayList<String>()}
                if(line[0].trim() == "dir") {
                    dir.add(getDirName(dirs)+"/"+line[1])
                } else {
                    dir.add(line[0])
                }
            }
            i++
        }
        var dirSize2 = HashMap<String, Long>()
        dirSizes.keys.forEach{
            dirSize2[it] = getSizes(it, dirSizes)
        }
        return dirSize2
    }

    fun getSizes(dir:String, dirMapping: HashMap<String, ArrayList<String>>): Long {
        var mapping = dirMapping.getOrDefault(dir, emptyList())
        if(mapping.all { n -> n.trim().all {c ->Character.isDigit(c)}}) {
            return mapping.map{s -> s.trim().toLong()}.sum()
        }
        var sum:Long = 0
        mapping.forEach {
            if(it.trim().all {c ->Character.isDigit(c)}) {
                sum += it.toLong()
            }
            else {
                sum += getSizes(it, dirMapping)
            }
        }
        return sum
    }

    fun getDirName(dirs:List<String>) :String {
        return dirs.joinToString(separator = "/")
    }
}