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
        counter += checkNextChars(it, "XMAS")
        counter += checkNextChars(it, "SAMX")
    }
    return counter
}

private fun checkNextChars(string: String, word:String): Int {
    for (i in string.indices) {
        if (string[i] == word[0] && (i + 1) in string.indices) {
            if (string[i + 1] == word[1] && (i + 2) in string.indices) {
                if (string[i + 2] == word[2] && (i + 3) in string.indices) {
                    if (string[i + 3] == word[3]) {
                        return 1
                    }
                }
            }
        }
    }
    return 0
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
    fun collectDiagonal(startX: Int, startY: Int, stepX: Int, stepY: Int): String {
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


    //top left  to right bottom \
    for (x in 0 until length) {
        diagonalLines.add(collectDiagonal(x, 0, 1, 1))
    }
    // left row to right bottom \
    for (y in 1 until length) { // exclude main diagonal as already checked
        diagonalLines.add(collectDiagonal(0, y, 1, 1))
    }
    // right top to bottom left /
    for (x in 0 until length) {
        diagonalLines.add(collectDiagonal(length - 1 - x, 0, -1, 1))
    }
    // right column to bottom left
    for (y in 1 until length) { //exclude main diagonal as already checked
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

