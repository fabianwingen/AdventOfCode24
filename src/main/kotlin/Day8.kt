import java.io.File

fun main() {
    val path = "src/main/resources/input8.txt"
    val file = File(path)
    val xMax = file.readLines()[0].length
    val yMax = file.readLines().size
    println(parseInput(file))
    println((0 to 7) - (8 to 0))
    println(part1(parseInput(file),yMax,yMax))
    println(part2(parseInput(file),yMax,xMax))

}

private fun parseInput(file: File): List<Pair<Char, List<Pair<Int, Int>>>> {
    val results = file.readLines()
        .mapIndexed {indexY, line -> line
            .mapIndexed { indexX, char ->
                if (char != '.') (char to (indexX to indexY)) else null}
            .filterNotNull()}
        .flatten()
        .groupBy { it.first }
        .map { it.key to it.value.map { el -> el.second } } // map char to all positions of antennas with this char

    return results

}

private fun part1(antennas: List<Pair<Char, List<Pair<Int, Int>>>>, yMax: Int, xMax:Int): Int {

    val antidotes = mutableSetOf<Pair<Int,Int>>()
   for (antenna in antennas) {
        for (position in antenna.second) {
            antidotes.addAll(
                getAntidotesForASingleAntenna(position, antenna
                    .second
                    .filterNot { it == position }))
        }
    }
    val result = antidotes.filter {it.first in 0 until xMax && it.second in 0 until yMax}
    return result.size

}

private fun getAntidotesForASingleAntenna(position: Pair<Int, Int>,
                                          otherPositions: List<Pair<Int, Int>>): List<Pair<Int,Int>> {

    val result = otherPositions.map { calculateAntidote(position,it) }
    return result
}

private fun calculateAntidote(
    position: Pair<Int, Int>,
    otherPosition: Pair<Int, Int>
): Pair<Int, Int> {
    val difference = otherPosition - position
    val antidote = otherPosition + difference
    return antidote
}

private fun calculateDifference(
    position: Pair<Int, Int>,
    otherPosition: Pair<Int, Int>
): Pair<Int, Int> {
    val difference = otherPosition - position
    return difference
}

private fun part2(antennas: List<Pair<Char, List<Pair<Int, Int>>>>, xMax: Int, yMax: Int): Int {

    val map = getPositionsOfAntennasMappedToTheirDifferencesToOthers(antennas)
    // it.first = position, it.second = vector
    val result = map.map {
        getAllAntidotesFor(it.first, it.second, xMax,yMax) +
        getAllAntidotesFor(it.first,it.second.inverse(),xMax,yMax)}
        .flatten()
        .toSet()
        .size

    return result

}

private fun getAllAntidotesFor(start: Pair<Int, Int>, vector: Pair<Int, Int>,xMax: Int,yMax: Int): MutableSet<Pair<Int,Int>> {
    val antidotes = mutableSetOf<Pair<Int,Int>>()
    var currentAntidote = start
    antidotes.add(start)
    while(currentAntidote.plus(vector).first in 0 until xMax && currentAntidote.plus(vector).second in 0 until yMax) {
        currentAntidote = currentAntidote.plus(vector)
        antidotes.add(currentAntidote)
    }
    return antidotes
}

private fun getPositionsOfAntennasMappedToTheirDifferencesToOthers(antennas: List<Pair<Char, List<Pair<Int, Int>>>>): MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
    val positionToVector = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    for (antenna in antennas) {
        for (position in antenna.second) {
            positionToVector.addAll(getVectorsForASingleAntenna(position, antenna.second.filterNot { it == position })
                .map { position to it })
        }
    }
    println(positionToVector)
    return positionToVector
}

private fun getVectorsForASingleAntenna(position: Pair<Int, Int>, list: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val result = list.map {calculateDifference(position, it)}
    println(result)
    return result
}


private operator fun Pair<Int,Int>.minus(other: Pair<Int,Int>): Pair<Int,Int> {
    return Pair(
        this.first - other.first,
        this.second - other.second
    )
}

private operator fun Pair<Int,Int>.plus(other: Pair<Int,Int>): Pair<Int,Int> {
    return Pair(
        this.first + other.first,
        this.second + other.second
    )
}

private fun Pair<Int,Int>.inverse(): Pair<Int,Int> {
    return Pair(
        this.first * (-1),
        this.second* (-1)
    )
}







