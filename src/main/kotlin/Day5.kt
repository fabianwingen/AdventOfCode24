import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val path = "src/main/resources/input5.txt"
    val file = File(path)
    val parsedRules = parseInputsOrderingRules(file)
    val parsedUpdates = parseUpdates(file)
    println(part1(parsedRules,parsedUpdates))
    val time = measureTimeMillis {
        val result = part2(parsedRules, parsedUpdates)
    }
    println(time)
}

private fun part1(rules: Map<Int, List<Int>>, updates: List<List<Int>>): Int {
    return updates
        .filter { isUpdateValid(rules,it) }
        .map { it.get(it.size / 2) }
        .sum()
}

private fun part2(rules: Map<Int, List<Int>>, updates: List<List<Int>>): Int {

    fun sortSingleUpdate(singleUpdate: List<Int>): List<Int> {
        val newRules = rules
            .filter { singleUpdate.contains(it.key) }
            .mapValues { (_, value) -> value.filter { it in singleUpdate } }
            .filterValues { it.isNotEmpty() }

        val customSorted = singleUpdate.sortedWith { a, b -> // values before keys, keys after values
            when {
                (newRules[a] ?: listOf()).contains(b) -> 1
                (newRules[b] ?: listOf()).contains(a) -> -1
                else -> 0
            }
        }
        return customSorted
    }


    return updates
        .filterNot { isUpdateValid(rules,it) }
        .map { sortSingleUpdate(it) }
        .sumOf { it[it.size / 2] }
}

private fun isUpdateValid(rules: Map<Int, List<Int>>, singleUpdate: List<Int>): Boolean {

    fun isASingleRuleBroken(numberToCheck: Int, key: Int, singleUpdate: List<Int>): Boolean {
        val index = singleUpdate.indexOf(key)
        if (singleUpdate.indexOf(numberToCheck) >= index) {
            return false
        }
        return true
    }

    for (i in singleUpdate) {
        val list = rules[i]?.filter { singleUpdate.contains(it) } ?: listOf()
        if (list.isEmpty()) {
            continue
        } else {
            for (number in list) {
                if(!isASingleRuleBroken(number, i, singleUpdate)) {
                    return false
                }
            }
        }
    }
    return true
}

private fun parseUpdates(file: File): List<List<Int>> {
    val updates = file.readLines().takeLastWhile { it.isNotEmpty() }.map { it.split(",").map { el -> el.toInt() } }
    return updates
}
private fun parseInputsOrderingRules(file: File): Map<Int, List<Int>> {
    val rules = file.readLines().takeWhile {
        it.isNotEmpty() }
        .map { it.split("|")
            .map {el -> el.toInt()} }
        .groupBy({it.last()}, {it.first()})
    println(rules)

    return rules
}