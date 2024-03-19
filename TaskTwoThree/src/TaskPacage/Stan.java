package TaskPacage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Stan {
    public static String executionStan(Queue<String> queue) {
        Queue<String> answer = new LinkedList<>();
        reverse(queue, answer);
        reverseOne(queue);
        if (check(queue, answer)) {
            return "Y";
        } else {
            return "F";
        }
    }

    public static boolean check(Queue<String> queue, Queue<String> answer) {
        if (queue.isEmpty()) {
            return true;
        } else if (!queue.remove().equals(answer.remove())) {
            return false;
        }
        return check(queue, answer);
    }

    public static void reverse(Queue<String> queue, Queue<String> answer) {
        if (!queue.isEmpty()) {
            String a = queue.remove();
            reverse(queue, answer);
            answer.add(a);
            queue.add(a);
        }
    }

    public static void reverseOne(Queue<String> queue) {
        if (!queue.isEmpty()) {
            String a = queue.remove();
            reverseOne(queue);
            queue.add(a);
        }
    }

    public static Queue<String> makeQueue(String[] args) {
        Queue<String> queue = new LinkedList<>();
        for (int i = 0; i < args.length; i++) {
            queue.add(args[i]);
        }
        return queue;
    }

    public static String[] makeList(Queue<String> queue) {
        ArrayList<String> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            list.add(queue.remove());
        }
        String[] answerList = new String[list.size()];
        for (int i = 0; i < answerList.length; i++) {
            answerList[i] = list.get(i);
        }
        return answerList;
    }
}
