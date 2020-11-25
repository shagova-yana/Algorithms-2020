@file:Suppress("UNUSED_PARAMETER")

package lesson7

import kotlin.math.max

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    val longest = StringBuilder()
    val n = first.length
    val m = second.length
    val table = Array(n + 1) { IntArray(m + 1) }
    for (i in 1..n) {
        val charFirst = first[i - 1]
        for (j in 1..m) {
            val charSecond = second[j - 1]
            if (charFirst == charSecond)
                table[i][j] = table[i - 1][j - 1] + 1
            else table[i][j] = max(table[i][j - 1], table[i - 1][j])
        }
    }
    var i = n
    var j = m
    while (i > 0 && j > 0) {
        when {
            first[i - 1] == second[j - 1] -> {
                longest.append(first[i - 1])
                i--
                j--
            }
            table[i - 1][j] == table[i][j] -> i--
            else -> j--
        }
    }
    println(longest)
    println()
    return longest.reverse().toString()
}
// ресурсоёмкость - O(nm) , трудоёмкость - O(nm)

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    when (list.size) {
        0, 1 -> return list
        2 -> return if (list[0] < list[1]) list
        else {
            val seq = mutableListOf<Int>()
            seq.add(list[0])
            seq
        }
    }

    val n = list.size
    val prev = mutableListOf<Int>()
    val seq = mutableListOf<Int>()
    for (i in list.indices) {
        seq.add(i, 1)
        prev.add(i, -1)
        for (j in 0 until i)
            if (list[j] < list[i] && seq[j] + 1 > seq[i]) {
                seq[i] = seq[j] + 1
                prev[i] = j
            }
    }
    var pos = 0
    var length = seq[0]
    for (i in 0 until n)
        if (seq[i] > length) {
            pos = i
            length = seq[i]
        }
    val result = mutableListOf<Int>()
    while (pos != -1) {
        result.add(list[pos])
        pos = prev[pos]
    }
    return result.reversed()
} //ресурсоёмкость - O(n) , трудоёмкость - O(n^2)

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5