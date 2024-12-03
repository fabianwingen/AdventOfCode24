import java.io.File
import kotlin.math.abs

val isDifferenceValid: (Pair<Int, Int>) -> Boolean = { abs(it.first - it.second) <= 3 }
val isIncreasing: (Pair<Int, Int>) -> Boolean = { it.first < it.second }
val isDecreasing: (Pair<Int, Int>) -> Boolean = { it.first > it.second }

fun main() {
    val path = "src/main/resources/input2.txt"
    val file = File(path)
    val parsedInput = parseInputs(file)
    println(parsedInput)
    println(part1(parsedInput))
    println(part2(parsedInput))
}

private fun part1(inputs: List<List<Int>>): Int {
    return getValidLines(inputs).size
}
private fun part2(inputs: List<List<Int>>): Int {
    val validLines = getValidLines(inputs)
    val newValidLines = inputs.filterNot { validLines.contains(it) }.filter { list -> checkIfValid(list) }

    return validLines.size + newValidLines.size
}

private fun getValidLines(inputs: List<List<Int>>): List<List<Int>> {
    val validLines = inputs.filter {
        val pairs = it.zipWithNext()
        pairs.all { pair -> isDifferenceValid(pair) && isIncreasing(pair) } ||
                pairs.all { pair -> isDifferenceValid(pair) && isDecreasing(pair) }
    }
    return validLines
}

fun checkIfValid(list: List<Int>): Boolean { //TODO: fix, brute force
    for (i in list.indices) {  val newList = list.filterIndexed {index, _ -> index != i}
        if (getValidLines(listOf(newList)).isNotEmpty()) {
            return true
        }
    }
    return false
}

private fun parseInputs(file: File): List<List<Int>> {
    val list = file.readLines().map { it.split(" ").map { el -> el.toInt() } }
    return list
}

