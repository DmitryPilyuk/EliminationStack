package stack

import kotlinx.atomicfu.*
import java.util.EmptyStackException

class TreiberStack<T> : Stack<T> {

    private val head = atomic<Node<T>?>(null)

    private fun tryPush(value: T): Boolean {
        val oldHead = head.value
        val newHead = Node(value, oldHead)
        return head.compareAndSet(oldHead, newHead)
    }

    override fun push(value: T) {
        while (true) {
            if (tryPush(value)) {
                return
            }
        }
    }

    private fun tryPop(): Result<Node<T>?> {
        val h = head.value
        if (head.compareAndSet(h, h?.next)) {
            return Result.success(h)
        }
        return Result.failure(EmptyStackException())
    }

    override fun pop(): T? {
        while (true) {
            val res = tryPop()
            if (res.isSuccess) {
                val node = res.getOrNull()
                return node?.value
            }
        }

    }

    override fun peek(): T? = head.value?.value
}