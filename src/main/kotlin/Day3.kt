import java.io.File
import kotlin.math.abs


fun main() {
    val path = "src/main/resources/input3.txt"
    val file = File(path)
    println(part1(file.readText()))
    println(part2((file.readText())))
}

private fun part1(text: String): Int {
    val pattern = Regex("mul\\((\\d+),(\\d+)\\)")
    val matches = pattern.findAll(text)

    return matches.map { it.groups[1]?.value!!.toInt() to it.groups[2]?.value!!.toInt() }.sumOf { pair -> pair.first * pair.second }
}

private fun part2(text: String): Int {
   val pattern = Regex("(do(n't)?)|mul\\((\\d+),(\\d+)\\)")
    val matches = pattern.findAll(text)

    val mutableList = mutableListOf<Int>()
    var booleanDo = true;
    for ( match in matches) {
        if (match.value == "do" ) {
            booleanDo = true
            continue
        }
        if (match.value == "don't") {
            booleanDo = false
            continue
        }
        if (booleanDo) {
            mutableList.add(match.groups[3]?.value!!.toInt() * match.groups[4]?.value!!.toInt())
        }
    }
    return mutableList.sum()
}

