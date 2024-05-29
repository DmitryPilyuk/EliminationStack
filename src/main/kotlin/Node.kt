package stack

class Node<T>(val value: T) {
    var next: Node<T>? = null

    constructor(value: T, next: Node<T>?) : this(value) {
        this.next = next
    }
}