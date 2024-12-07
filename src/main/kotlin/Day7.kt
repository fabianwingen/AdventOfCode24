import java.io.File

fun main() {
    val path = "src/main/resources/input7.txt"
    val file = File(path)
    println(part1(parseInput(file)))
    println(part2(parseInput(file)))

}

private fun parseInput(file: File): List<Pair<Long, List<Long>>> {
    val results = file.readLines()
        .map { it.split(":") }
        .map { Pair(it.first().toLong(),it[1]
            .trim()
            .split(" ")
            .map { el -> el.trim().toLong() }) }

    return results

}

private fun part1(list: List<Pair<Long, List<Long>>>): Long {
    val operations = listOf("*","*")
    return list.filter { calculateSingleLine(it,operations) }.sumOf { it.first }

}
private fun part2(list: List<Pair<Long, List<Long>>>): Long {
    val operations = listOf("*","+","|")
    return list.filter { calculateSingleLine(it,operations) }.sumOf { it.first }
}


private fun calculateSingleLine(list: Pair<Long, List<Long>>, operations: List<String>): Boolean {
    var numbers = list.second
    val permutations = generatePermutations(numbers.size - 1, operations)
    for (permutation in permutations) {
        val result = numbers.reduceIndexed { index, acc, element ->
            if (index - 1 < 0) {
                acc
            } else when (permutation[index - 1]) {
                '*' -> acc * element
                '+' -> acc + element
                '|' -> (acc.toString() + "" + element.toString()).toLong()
                else -> acc + element
            }
        }
        if (result == list.first) return true
    }
    return false
}

fun generatePermutations(size: Int,operations: List<String>, current: String = "", result: MutableList<String> = mutableListOf()): List<String> {
    if (current.length == size) {
        result.add(current)
        return result
    }
    for (o in operations) {
        generatePermutations(size,operations, current + o, result)
    }
    return result
}







