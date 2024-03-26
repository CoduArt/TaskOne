package TaskPacage;

import java.util.Stack;

public class Realization {
    public static int[] quickSort(int[] args) {
        Stack<Integer> stack = quickSortForInterval(makeStack(args));
        return makeList(stack);
    }

    private static Stack<Integer> quickSortForInterval(Stack<Integer> args) {
        if (args.size() == 1 || args.empty()) {
            return args;
        }
        int pivot = args.pop();
        Stack<Integer> leftStack = new Stack<>();
        Stack<Integer> rightStack = new Stack<>();
        while (!args.empty()) {
            if (args.peek() > pivot) {
                rightStack.add(args.pop());
            } else {
                leftStack.add(args.pop());
            }
        }
        leftStack = quickSortForInterval(leftStack);
        rightStack = quickSortForInterval(rightStack);
        rightStack.add(pivot);
        rightStack.addAll(leftStack);
        return rightStack;
    }

    private static Stack<Integer> makeStack(int[] args) {
        Stack<Integer> stack = new Stack<>();
        for (Integer el: args) {
            stack.add(el);
        }
        return stack;
    }

    private static int[] makeList(Stack<Integer> stack) {
        int[] list = new int[stack.size()];
        int i = 0;
        while (!stack.empty()) {
            list[i++] = stack.pop();
        }
        return list;
    }
}
