import java.io.File
import kotlin.math.abs

fun main() {
    val path = "src/main/resources/input1.txt"
    val file = File(path)
    val parsedInput = parseInputs(file)
    println(part1(parsedInput))
    println(part2(parsedInput))
}

fun part1(inputs: List<List<Int>>): Int {
    val list1Sorted = inputs[0].sorted()
    val list2Sorted = inputs[1].sorted()
    return list1Sorted.mapIndexed { index, el -> abs(el - list2Sorted[index]) }.sum()
}

fun part2(inputs: List<List<Int>>): Int {
    val groupedBy = inputs[1].groupingBy { it }.eachCount()
    return inputs[0].sumOf { it * groupedBy.getOrDefault(it, 0) }
}

fun parseInputs(file: File): List<List<Int>> {
    val (list1, list2) = file.readLines().map { it.split("   ") }
        .map { it[0].toInt() to it[1].toInt() }
        .unzip()
    return listOf(list1, list2)
}
