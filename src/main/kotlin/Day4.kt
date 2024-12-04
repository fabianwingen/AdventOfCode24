import java.io.File

fun main() {
    val path = "src/main/resources/input4.txt"
    val file = File(path)
    val parsedInput = parseInputs(file)
    println(part1(parsedInput))
    println(part2(parseInputs2(file)))
}

private fun part1(input: List<String>): Int {
    val lines = getAllLines(input)
    var counter = 0
    lines.forEach {
        for (i in it.indices) {
            if (it[i] == 'X' && (i+1) in it.indices) {
                if (it[i+1] == 'M' && (i+2) in it.indices) {
                    if (it[i+2] == 'A' && (i+3) in it.indices) {
                        if (it[i+3] == 'S') {
                            counter++
                        }
                    }
                }
            }
            if (it[i] == 'S' && (i+1) in it.indices) {
                if (it[i+1] == 'A' && (i+2) in it.indices) {
                    if (it[i+2] == 'M' && (i+3) in it.indices) {
                        if (it[i+3] == 'X') {
                            counter++
                        }
                    }
                }
            }
        }
    }
    return counter
}

private fun part2(inputs: List<String>): Int {
    val pattern = Regex("^(M.M.A.S.S|S.M.A.S.M|M.S.A.M.S|S.S.A.M.M)")
    return inputs.filter { pattern.containsMatchIn(it) }.size

}

private fun getAllLines(input: List<String>): List<String> {
    val length = input[0].length
    val verticalLines = mutableListOf<String>()
    for (columnIndex in 0 until length) {
        val columnString = buildString {
            for (line in input) {
                append(line[columnIndex])
            }
        }
        verticalLines.add(columnString)
    }
    val diagonalLines = getDiagonalLines(input)
    val horizontalLines = input
    val allLines = diagonalLines + horizontalLines + verticalLines
    return allLines
}




fun getDiagonalLines(input: List<String>): List<String> {
    val length = input.size
    val diagonalLines = mutableListOf<String>()

    // Helper function to extract a diagonal
    fun collectDiagonal(
        startX: Int,
        startY: Int,
        stepX: Int,
        stepY: Int
    ): String {
        val diagonalLine = mutableListOf<Char>()
        var x = startX
        var y = startY
        while (x in 0 until length && y in input.indices) {
            diagonalLine.add(input[y][x])
            x += stepX
            y += stepY
        }
        return diagonalLine.joinToString("")
    }

    // Top-right diagonals
    for (x in 0 until length) {
        diagonalLines.add(collectDiagonal(x, 0, 1, 1))
    }
    // Bottom-left diagonals (excluding main diagonal)
    for (y in 1 until length) {
        diagonalLines.add(collectDiagonal(0, y, 1, 1))
    }
    // Top-left diagonals
    for (x in 0 until length) {
        diagonalLines.add(collectDiagonal(length - 1 - x, 0, -1, 1))
    }
    // Bottom-right diagonals (excluding main diagonal)
    for (y in 1 until length) {
        diagonalLines.add(collectDiagonal(length - 1, y, -1, 1))
    }

    return diagonalLines
}


private fun parseInputs(file: File): List<String> {
    return file.readLines()
}

private fun parseInputs2(file: File): List<String> {
    val list = file.readLines()
    val kasten = mutableListOf<String>()
    for ( x in list[0].indices) {
        for ( y in list.indices) {
            if (x+2 in list[0].indices && y+2 in list.indices) {
                kasten.add(
                    listOf(list[y][x],list[y][x+1],list[y][x+2],list[y+1][x],list[y+1][x+1],list[y+1][x+2]
                        ,list[y+2][x], list[y+2][x+1] ,list[y+2][x+2])
                        .joinToString(separator = ""))
            }
        }
    }
    return kasten.toList()
}

