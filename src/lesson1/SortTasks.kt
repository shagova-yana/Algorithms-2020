@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val map = mutableMapOf<Int, Int>()
    for (i in -2730..5000) map[i] = 0 //O(N), где N = max(5000) - min(2730) + 1 ресурсоёмкость и трудоёмкость
    var sum = 0
    for (line in File(inputName).readLines()) { // O(N), где N = max(5000) - min(2730) + 1 трудоёмкость
        val key = (line.toDouble() * 10).toInt()
        val value = map.getValue(key)
        map[key] = value + 1
        sum++
    }
    val outputStream = File(outputName).bufferedWriter()
    for ((key, value) in map) { // O(N), где N = max(5000) - min(2730) + 1 трудоёмкость
        if (value >= 1)
            for (i in 1..value) { // O(n) в худшем и O(1)в лучшем трудоёмкость
                outputStream.write((key.toDouble() / 10).toString())
                sum--
                if (sum != 0) outputStream.newLine()
            }
    }
    outputStream.close()
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    val map = mutableMapOf<Int, Int>() // O(n) - ресурсоёмкость
    val sequence = mutableListOf<Int>() // O(n) - ресурсоёмкость
    var count = 0
    var min = 0
    var f = false
    val result = mutableMapOf<Int, Int>()
    for (line in File(inputName).readLines()) { // O(n) - трудоёмкость
        val element = line.toInt()
        sequence.add(element)
        if (map.containsKey(element)) {
            val value = map[element]
            if (value != null) {
                map[element] = value + 1
            }
        } else map[element] = 1
        if (map.getValue(element) > count && f) {
            result.clear()
            min = element
            count = map.getValue(element)
            f = false
        }
        if (map.getValue(element) > count) {
            count = map.getValue(element)
            min = element
        }
        if (map.getValue(element) == count) {
            result[min] = count
            result[element] = count
            f = true
        }
    }
    if (result.size > 1) {
        val list = result.keys // O(n) - ресурсоёмкость
        min = list.minOrNull()!!.toInt()
        count = result.getValue(min)
    }
    val output = File(outputName).bufferedWriter()
    for (element in sequence) { // O(n) - трудоёмкость
        if (element != min) {
            output.write(element.toString())
            output.newLine()
        }
    }
    for (i in 1..count) { // O(k) трудоёмкость, где k - количество повторов числа
        output.write(min.toString())
        if (i != count) output.newLine()
    }
    output.close()
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    TODO()
}

