package TaskPacage;

import java.util.LinkedList;
import java.util.Queue;

public class Real {

    public static String execution(SelfQueue<String> queue) {
        SelfQueue<String> answer = new SelfQueue<>(queue.elemet());
        reverse(queue, answer);
        reverseOne(queue);
        answer.remove();
        if (check(queue, answer)) {
            return "Y";
        } else {
            return "F";
        }
    }

    public static boolean check(SelfQueue<String> queue, SelfQueue<String> answer) {
        if (queue.isEmpty()) {
            return true;
        } else if (!queue.remove().equals(answer.remove())) {
            return false;
        }
        return check(queue, answer);
    }

    public static void reverse(SelfQueue<String> queue, SelfQueue<String> answer) {
        if (!queue.isEmpty()) {
            String a = queue.remove();
            reverse(queue, answer);
            answer.add(a);
            queue.add(a);
        }
    }

    public static void reverseOne(SelfQueue<String> queue) {
        if (!queue.isEmpty()) {
            String a = queue.remove();
            reverseOne(queue);
            queue.add(a);
        }
    }

    public static SelfQueue<String> makeQueue(String[] args) {
        SelfQueue<String> selfQueue = new SelfQueue<>(args[0]);
        for (int i = 1; i < args.length; i++) {
            selfQueue.add(args[i]);
        }
        return selfQueue;
    }
}
