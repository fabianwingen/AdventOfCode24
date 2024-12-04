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
    val diagonalLines = getDiagonalLines(length, input)
    val horizontalLines = input
    val allLines = diagonalLines + horizontalLines + verticalLines
    return allLines
}

private fun getDiagonalLines(length: Int, input: List<String>): List<String> { //TODO: reduce duplicate code
    val diagonalLines = mutableListOf<String>()
    for (x in 0 until length) {
        var diagonalYCounter = 0
        var diagonalXCounter = x;
        val diagonalLine = mutableListOf<Char>()
        while (diagonalXCounter < length && diagonalYCounter in input.indices) {
            diagonalLine.add(input[diagonalYCounter][diagonalXCounter])
            diagonalXCounter++
            diagonalYCounter++
        }
        diagonalLines.add(diagonalLine.joinToString(separator = ""))
    }
    for (y in input.indices) {
        var diagonalYCounter = y + 1 // duplicate main diagonal
        var diagonalXCounter = 0
        val diagonalLine = mutableListOf<Char>()
        while (diagonalXCounter < length && diagonalYCounter in input.indices) {
            diagonalLine.add(input[diagonalYCounter][diagonalXCounter])
            diagonalXCounter++
            diagonalYCounter++
        }
        diagonalLines.add(diagonalLine.joinToString(separator = ""))
    }
    for (x in 0 until length) {
        var diagonalYCounter = 0
        var diagonalXCounter = length-1-x;
        val diagonalLine = mutableListOf<Char>()
        while (diagonalXCounter >= 0 && diagonalYCounter in input.indices) {
            diagonalLine.add(input[diagonalYCounter][diagonalXCounter])
            diagonalXCounter--
            diagonalYCounter++
        }

        diagonalLines.add(diagonalLine.joinToString(separator = ""))
    }
    for (y in input.indices) {
        var diagonalYCounter = 1 + y // duplicate main diagonal
        var diagonalXCounter = length-1
        val diagonalLine = mutableListOf<Char>()
        while (diagonalXCounter >= 0 && diagonalYCounter in input.indices) {
            diagonalLine.add(input[diagonalYCounter][diagonalXCounter])
            diagonalXCounter--
            diagonalYCounter++
        }
        diagonalLines.add(diagonalLine.joinToString(separator = ""))
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