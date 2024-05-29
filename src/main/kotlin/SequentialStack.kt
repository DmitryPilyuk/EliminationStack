package stack

class SequentialStack : Stack<Int> {
    private var head: Node<Int>? = null
    override fun push(value: Int) {
        head = Node(value, head)
    }

    override fun pop(): Int? {
        val node = head
        head = head?.next
        return node?.value
    }

    override fun peek(): Int? = head?.value
}