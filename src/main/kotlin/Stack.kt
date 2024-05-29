package stack

interface Stack<T> {
    fun push(value: T)
    fun pop(): T?
    fun peek(): T?
}