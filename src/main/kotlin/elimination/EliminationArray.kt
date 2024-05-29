package stack.elimination

import kotlin.random.Random

class EliminationArray<T>(private val capacity: Int) {
    private val exchangers = Array(capacity) { Exchanger<T>() }

    fun visit(value: T?, range: Int, attempts: Int): Result<T?> {
        val slot = Random.nextInt(range)
        return exchangers[slot].exchange(value, attempts)
    }
}