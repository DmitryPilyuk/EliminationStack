package stack.elimination

import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicStampedReference

class Exchanger<T> {
    companion object {
        private const val EMPTY = 0
        private const val WAITING = 1
        private const val BUSY = 2
    }

    private val slot = AtomicStampedReference<T>(null, EMPTY)

    fun exchange(item: T?, attempts: Int): Result<T?> {
        val stampHolder = intArrayOf(EMPTY)

        repeat(attempts) {
            var yrItem = slot.get(stampHolder)
            val stamp = stampHolder[0]
            when (stamp) {
                EMPTY -> {
                    if (slot.compareAndSet(yrItem, item, EMPTY, WAITING)) {
                        repeat(attempts) {
                            yrItem = slot.get(stampHolder)
                            if (stampHolder[0] == BUSY) {
                                slot.set(null, EMPTY)
                                return Result.success(yrItem)
                            }
                        }
                        if (slot.compareAndSet(item, null, WAITING, EMPTY)) {
                            return Result.failure(TimeoutException())
                        } else {
                            yrItem = slot.get(stampHolder)
                            slot.set(null, EMPTY)
                            return Result.success(yrItem)
                        }
                    }
                }

                WAITING -> {
                    if (slot.compareAndSet(yrItem, item, WAITING, BUSY)) {
                        return Result.success(yrItem)
                    }
                }
            }
        }
        return Result.failure(TimeoutException())
    }
}