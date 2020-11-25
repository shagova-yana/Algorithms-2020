@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson6

import lesson6.Graph.Vertex
import lesson6.VertexColor.Colours.WHITE
import java.awt.Color.WHITE
import java.util.*


/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */
fun Graph.findEulerLoop(): List<Graph.Edge> {
    TODO()
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */
fun Graph.minimumSpanningTree(): Graph {
    TODO()
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Если на входе граф с циклами, бросить IllegalArgumentException
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 */
class VertexColor internal constructor(vertexColour: Colours, previous: Vertex?) {
    enum class Colours {
        WHITE, GREY, BlACK
    }

    var vertexColour: Colours = vertexColour
    var previous: Vertex? = previous
}

fun dfs(vertex: Vertex, map: MutableMap<Vertex, VertexColor>, graph: Graph, cycle: MutableList<Int>) {
    map.getValue(vertex).vertexColour = VertexColor.Colours.GREY
    for (neighbour in graph.getNeighbors(vertex)) {
        if (map.getValue(neighbour).vertexColour == VertexColor.Colours.WHITE) {
            map.getValue(neighbour).previous = vertex
            dfs(neighbour, map, graph, cycle)
        }
        if (map.getValue(neighbour).vertexColour == VertexColor.Colours.GREY
            && map.getValue(vertex).previous != neighbour)
            cycle.add(1)
    }
    map.getValue(vertex).vertexColour = VertexColor.Colours.BlACK
}

private fun isCycle(graph: Graph): Boolean {
    val map = mutableMapOf<Vertex, VertexColor>()
    val cycle = mutableListOf<Int>()
    for (element in graph.vertices)
        map[element] = VertexColor(VertexColor.Colours.WHITE, null)
    for (vertex in graph.vertices) {
        if (map.getValue(vertex).vertexColour == VertexColor.Colours.WHITE)
            dfs(vertex, map, graph, cycle)
        if (cycle.isNotEmpty())
            return true
    }
    return false
}

fun Graph.largestIndependentVertexSet(): Set<Vertex> {
    if (isCycle(this)) throw IllegalArgumentException()
    val result = mutableSetOf<Vertex>()
    val vertices = this.vertices
    while (vertices.isNotEmpty()) {
        val element = vertices.first()
        result.add(element)
        vertices.removeAll(getNeighbors(element))
        vertices.remove(element)
    }
    return result
}
// ресурсоёмкость - O(V), трудоёмкость - O(V+E)

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */
fun Graph.longestSimplePath(): Path {

    fun search(vertex: Vertex, graph: Graph, current: MutableList<Vertex>, path: MutableList<Vertex>) {
        if (!current.contains(vertex)) {
            current.add(vertex)
            for (neighbour in this.getNeighbors(vertex))
                if (!current.contains(neighbour))
                    search(neighbour, this, current, path)
        }
        if (current.size > path.size) {
            path.clear()
            path.addAll(current)
        }
        if (current.size != 1) current.remove(current.last())
    }//O(E^V)

    if (this.vertices.isEmpty()) return Path()
    val longestPath = mutableListOf<Vertex>()
    var current: MutableList<Vertex>
    for (vertex in this.vertices) {
        current = mutableListOf()
        if (longestPath.size != this.vertices.size) {
            search(vertex, this, current, longestPath)
        } else break
    }
    var path = Path(longestPath[0])
    if (longestPath.size > 1)
        for (i in 1 until longestPath.size)
            path = Path(path, this, longestPath[i])
    return path
}// ресурсоёмкость - O(2V), трудоёмкость - O(V*E^V)

/**
 * Балда
 * Сложная
 *
 * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
 * поэтому задача присутствует в этом разделе
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    TODO()
}