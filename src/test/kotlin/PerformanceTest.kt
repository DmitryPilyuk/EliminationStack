import kotlinx.coroutines.*
import org.hamcrest.Condition
import stack.Stack
import stack.TreiberStack
import stack.elimination.EliminationStack
import kotlin.random.Random
import kotlin.system.measureTimeMillis
import kotlin.test.Test

class PerformanceTest {

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    fun measureTime(stack: Stack<Int>, threads: Int, operations: Int, cond: (Int) -> Boolean): Long {
        val rep = 10
        var testTime = 0L
        repeat(rep) {
            val jobs = mutableListOf<Job>()
            val time = measureTimeMillis {
                runBlocking {
                    repeat(threads) { thread ->
                        jobs.add(launch(newSingleThreadContext(thread.toString() + "OP")) {
                            repeat(operations) {
                                if (cond(thread)) {
                                    stack.push(Random.nextInt(10000))
                                } else {
                                    stack.pop()
                                }
                            }
                        })
                    }
                    jobs.joinAll()
                }
            }
            testTime += time
        }
        testTime /= rep
        return testTime
    }


    @Test
    fun performanceTest() {
        val elimStack = EliminationStack<Int>(8)
        val treiberStack = TreiberStack<Int>()
        val operations = 1_000_000
        println("Подобранные данные, push и pop чередуются")
        for (threads in mutableListOf(1, 2, 4, 8, 16)) {
            val elimTime = measureTime(elimStack, threads, operations, fun(thread: Int): Boolean = thread % 2 == 0)
            val treiberTime =
                measureTime(treiberStack, threads, operations, fun(thread: Int): Boolean = thread % 2 == 0)
            println("N = $threads | elimTime = $elimTime ms | treiber = $treiberTime ms")
        }

        println("\nСлучайные данные")
        for (threads in mutableListOf(1, 2, 4, 8, 16)) {
            val elimTime =
                measureTime(elimStack, threads, operations, fun(_: Int): Boolean = Random.nextInt(2) % 2 == 0)
            val treiberTime =
                measureTime(treiberStack, threads, operations, fun(_: Int): Boolean = Random.nextInt(2) % 2 == 0)
            println("N = $threads | elimTime = $elimTime ms | treiber = $treiberTime ms")
        }
    }
}