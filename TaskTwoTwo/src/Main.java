public class Main {
    public static void main(String[] args) {
        int n = 3;
        Node head = formNode(n*3);
        Node one = classificationReverse(head, 0, n);
        Node two = classificationReverse(head, n, n);
        Node three = classificationReverse(head, 2*n, n);
        Node answer = formAnswer(one, two, three, n);
        print(answer);

    }

    public static Node formNode(int n) {
        int newN = n - 1;
        Node head = new Node(n, null);
        for (int i = 0; i < n - 1; i++) {
            head = new Node(newN, head);
            newN--;
        }
        return head;
    }

    public static void print(Node node) {
        for (Node newNode = node; newNode != null; newNode = newNode.next) {
            System.out.print(newNode.value);
        }
    }

    public static Node classificationReverse(Node node, int count, int n) {
        Node newNode = new Node(node.find(count).value, null);
        for (int i = 1; i < n; i++) {
            newNode = new Node(node.find(count + i).value, newNode);
        }
        return newNode;
    }

    public static Node formAnswer(Node one, Node two, Node three, int n) {
        Node answerNode = null;
        for (int i = 0; i < n; i++) {
            answerNode = new Node(three.value, answerNode);
            three = three.next;
            answerNode = new Node(two.value, answerNode);
            two = two.next;
            answerNode = new Node(one.value, answerNode);
            one = one.next;
        }
        return answerNode;
    }
}