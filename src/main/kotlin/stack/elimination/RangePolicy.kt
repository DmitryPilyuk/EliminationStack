package stack.stack.elimination

class RangePolicy(private val size: Int) {
    private var successes: Int = 0
    private var timeouts: Int = 0
    private var range: Int = size
    private var attempts: Int = 10

    fun getRange() = range
    fun getAttempts() = attempts

    fun recordEliminationTimeout() {
        timeouts++
        if (timeouts > 5) {
            timeouts = 0
            if (range > 1) range /= 2
        }
    }

    fun recordEliminationSuccess() {
        successes++
        if (successes > 5) {
            successes = 0
            if (2 * range <= size) range *= 2
        }
    }

}
