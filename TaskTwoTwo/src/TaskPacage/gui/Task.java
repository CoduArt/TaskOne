package TaskPacage.gui;

public class Task {

    public static int[] execution(Node head) {
        int n = head.size() / 3;
        Node one = classificationReverse(head, 0, n);
        Node two = classificationReverse(head, n, n);
        Node three = classificationReverse(head, 2*n, n);
        Node answer = formAnswer(one, two, three, n);
        return toString(answer);
    }

    public static int[] toString(Node node) {
        int[] list = new int[node.size()];
        int i = 0;
        while (node != null) {
            list[i] = node.value;
            node = node.next;
            i++;
        }
        return list;
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

    public static Node toNode(int[] list) {
        if (list == null || list.length == 0) {
            throw new RuntimeException("null");
        }
        int count = 1;
        Node node = new Node(list[list.length - 1], null);
        for (int i = list.length - 2; i >= 0; i--) {
            node = new Node(list[i], node);
            count++;
        }
        if (count % 3 != 0) {
            throw new RuntimeException("n не кратен 3");
        }
        return node;
    }
}