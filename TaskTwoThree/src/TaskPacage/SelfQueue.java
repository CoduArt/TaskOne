package TaskPacage;

public class SelfQueue<T> {
    private PrivateQueue queue;

    public SelfQueue(T value) {
        queue = new PrivateQueue(value, null);
    }

    public void add(T value) {
        queue = new PrivateQueue(value, queue);
    }

    public T remove() {
        if (queue.next == null) {
            T a = queue.value;
            queue = null;
            return a;
        } else if (queue.next.next == null) {
            T a = queue.next.value;
            queue.next = null;
            return a;
        } else {
            PrivateQueue copyQueue = queue;
            while (copyQueue.next.next != null) {
                copyQueue = copyQueue.next;
            }
            T a = copyQueue.next.value;
            copyQueue.next = null;
            return a;
        }
    }

    public T elemet() {
        return queue.value;
    }

    public boolean isEmpty() {
        if (queue == null) {
            return true;
        }
        return false;
    }

    private class PrivateQueue {
        PrivateQueue next;
        T value;

        private PrivateQueue(T value, PrivateQueue next) {
            this.next = next;
            this.value = value;
        }
    }
}
