package stack.elimination

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.loop
import kotlinx.atomicfu.update
import stack.Node
import stack.Stack
import stack.stack.elimination.RangePolicy


class EliminationStack<T>(capacity: Int) : Stack<T> {
    private val array = EliminationArray<T>(capacity)
    private val head = atomic<Node<T>?>(null)

    private val policy = object : ThreadLocal<RangePolicy>() {
        @Synchronized
        override fun initialValue(): RangePolicy {
            return RangePolicy(capacity)
        }
    }

    override fun push(value: T): Unit {
        val rangePolicy: RangePolicy = policy.get()
        head.loop { h ->
            val newHead = Node(value, h)
            if (head.compareAndSet(h, newHead)) {
                return
            }

            val visitRes = array.visit(value, rangePolicy.getRange(), rangePolicy.getAttempts())
            if (visitRes.isSuccess && visitRes.getOrNull() == null) {
                rangePolicy.recordEliminationSuccess()
                return
            } else {
                rangePolicy.recordEliminationTimeout()
            }
        }
    }
//    override fun push(value: T): Unit = head.loop { h ->
//        val newHead = Node(value, h)
//        if (head.compareAndSet(h, newHead)) {
//            return
//        }
//        val visitRes = array.visit(value, 8, 10)
//        if (visitRes.isSuccess && visitRes.getOrNull() == null) {
//            return
//        }
//    }


    override fun pop(): T? {
        val rangePolicy: RangePolicy = policy.get()
        head.loop { h ->
            val next = h?.next
            if (head.compareAndSet(h, next)) {
                return h?.value
            }

            val visitRes = array.visit(null, rangePolicy.getRange(), rangePolicy.getAttempts())
            if (visitRes.isSuccess) {
                rangePolicy.recordEliminationSuccess()
                visitRes.getOrNull()?.let { return it }
            } else {
                rangePolicy.recordEliminationTimeout()
            }
        }
    }

//    override fun pop(): T? = head.loop { h ->
//        val next = h?.next
//        if (head.compareAndSet(h, next)) {
//            return h?.value
//        }
//
//        val visitRes = array.visit(null, 8, 10)
//        if (visitRes.isSuccess) {
//            visitRes.getOrNull()?.let { return it }
//        }
//    }


    override fun peek() = head.value?.value
}