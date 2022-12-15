class Star08 (override val input: String) : Star() {

    fun visibleTrees(input: String) :String {
        var data = input.split("\n")
        var seen = ArrayList<ArrayList<Boolean>>()
        var max = ArrayList<ArrayList<Int>>()
        input.split("\n").forEach {
            var t = ArrayList<Boolean>(data[0].length)
            var t2 = ArrayList<Int>(data[0].length)

            repeat(data[0].length) {
                t.add(false)
                t2.add(0)
            }
            seen.add(t)
            max.add(t2)
        }
        for(i in data.indices) {
            for( j in 0 until data[0].length) {
                if (i == 0 || j == 0 || i == data.size-1 || j == data[0].length -1) {
                    seen[i][j] = true
                    max[i][j] = data[i][j].digitToInt()
                }
            }
        }
        for(i in 1 until data.size -1) {
            for (j in 0 until data[0].length-1) {
                if ( j  == 0) {
                    max[i][j] = data[i][j].digitToInt()
                } else {
                    if ((data[i][j].digitToInt() > max[i][j - 1])) {
                        max[i][j] = data[i][j].digitToInt()
                        seen[i][j] = true
                    } else {
                        max[i][j] = max[i][j-1]
                    }
                }
            }
        }
        for(i in 1 until data.size -1) {
            for (j in 1 until data[0].length-1) {
                if ((data[i][j].digitToInt() > max[i-1][j])) {
                    max[i][j] = data[i][j].digitToInt()
                    seen[i][j] = true
                } else {
                    max[i][j] = max[i-1][j]
                }
            }
        }

        for(i in data.size -2 downTo 1) {
            for (j in data[0].length-2 downTo 1) {
                if ((data[i][j].digitToInt() > max[i+1][j])) {
                    max[i][j] = data[i][j].digitToInt()
                    seen[i][j] = true
                } else {
                    max[i][j] = max[i+1][j]
                }
            }
        }
        for(i in data.size -2 downTo 1) {
            for (j in data[0].length-2 downTo 1) {
                if(j == 3 && i == 2)
                    print("HUH")
                if ((data[i][j].digitToInt() > max[i][j+1])) {
                    max[i][j] = data[i][j].digitToInt()
                    seen[i][j] = true
                } else {
                    max[i][j] = max[i][j+1]
                }
            }
        }

        val sum =  seen.map { it-> it.count{it}}.sum()
        return "$sum"
    }

    override fun part1(): String {

        return ""+visibleTrees(input)
    }

    fun getView(i:Int, j:Int, input:List<String>): Int {
        if(i ==0 || j ==0 ||i == input.size -1 || j == input[0].length -1) {
            return 1
        }
        var left = 0
        for (l in j-1 downTo 0) {
            left ++

            if(input[i][j].digitToInt() <= input[i][l].digitToInt()) {
                break
            }

        }

        var right = 0
        for (r in j+1 until input[0].length) {
            right ++
            if(input[i][j].digitToInt() <= input[i][r].digitToInt()) {
                break
            }
        }
        var down = 0
        for (d in i+1 until input.size) {
            down++

            if(input[i][j].digitToInt() <= input[d][j].digitToInt()) {

                break
            }
        }
        var up = 0
        for (u in i-1 downTo  0) {
            up ++

            if(input[i][j].digitToInt() <= input[u][j].digitToInt()) {

                break
            }
        }

        return left * down * up * right
    }

    override fun part2(): String {
        var data = input.split('\n')
        var max = 0
        for(i in 1 until data.size) {
            for( j in 1 until data[0].length) {
                max = max.coerceAtLeast(getView(i, j, data))
            }
        }
       return "" + max
    }
}