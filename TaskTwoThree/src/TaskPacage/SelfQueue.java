package TaskPacage;

public class SelfQueue<E> implements NullQueue<E>{
    private Node queue;

    @Override
    public boolean isEmpty() {return  queue == null;}

    @Override
    public boolean add(E e) {
        queue = new Node(e, queue);
        return true;
    }

    @Override
    public E remove() {
        if (queue.next == null) {
            E a = queue.value;
            queue = null;
            return a;
        } else if (queue.next.next == null) {
            E a = queue.next.value;
            queue.next = null;
            return a;
        } else {
            Node copyQueue = queue;
            while (copyQueue.next.next != null) {
                copyQueue = copyQueue.next;
            }
            E a = copyQueue.next.value;
            copyQueue.next = null;
            return a;
        }
    }

    @Override
    public E element() {
        return queue.value;
    }

    private class Node {
        Node next;
        E value;

        private Node(E value, Node next) {
            this.next = next;
            this.value = value;
        }
    }
}
