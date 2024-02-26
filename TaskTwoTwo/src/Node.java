public class Node {
    Node next;
    int value;

    public Node(int value, Node next) {
        this.next = next;
        this.value = value;
    }

    public Node find(int n) {
        if (n < 0) {
            throw new RuntimeException("n < 0");
        }
        Node node = this;
        for (int i = 0; i < n; i++) {
            node = node.next;
        }
        return node;
    }
}
