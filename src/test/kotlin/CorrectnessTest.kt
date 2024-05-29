import org.jetbrains.kotlinx.lincheck.LoggingLevel
import org.jetbrains.kotlinx.lincheck.annotations.Operation
import org.jetbrains.kotlinx.lincheck.check
import org.jetbrains.kotlinx.lincheck.strategy.managed.modelchecking.ModelCheckingOptions
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressOptions
import stack.SequentialStack
import stack.TreiberStack
import stack.elimination.EliminationStack
import kotlin.test.Test


class TreiberStackTest {
    private val stack = TreiberStack<Int>()

    @Operation
    fun push(value: Int) = stack.push(value)

    @Operation
    fun pop() = stack.pop()

    @Operation
    fun peek() = stack.peek()

    @Test
    fun modelTest() =
        ModelCheckingOptions()
            .iterations(50)
            .invocationsPerIteration(50_000)
            .threads(3)
            .actorsPerThread(3)
            .sequentialSpecification(SequentialStack::class.java)
            .checkObstructionFreedom()
            .logLevel(LoggingLevel.INFO)
            .check(this::class)

    @Test
    fun stressTest() =
        StressOptions()
            .iterations(50)
            .invocationsPerIteration(50_000)
            .threads(3)
            .actorsPerThread(3)
            .sequentialSpecification(SequentialStack::class.java)
            .logLevel(LoggingLevel.INFO)
            .check(this::class)
}

class EliminationStackTest {
    private val stack = EliminationStack<Int>(8)

    @Operation
    fun push(value: Int) = stack.push(value)

    @Operation
    fun pop() = stack.pop()

    @Operation
    fun peek() = stack.peek()

    @Test
    fun modelTest() =
        ModelCheckingOptions()
            .iterations(50)
            .invocationsPerIteration(50_000)
            .threads(3)
            .actorsPerThread(3)
            .sequentialSpecification(SequentialStack::class.java)
            .checkObstructionFreedom()
            .logLevel(LoggingLevel.INFO)
            .check(this::class)

    @Test
    fun stressTest() =
        StressOptions()
            .iterations(50)
            .invocationsPerIteration(50_000)
            .threads(3)
            .actorsPerThread(3)
            .sequentialSpecification(SequentialStack::class.java)
            .logLevel(LoggingLevel.INFO)
            .check(this::class)
}