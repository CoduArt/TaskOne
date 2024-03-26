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
        int pivot = findPivot(args);
        int pivotCount = 0;
        Stack<Integer> leftStack = new Stack<>();
        Stack<Integer> rightStack = new Stack<>();
        while (!args.empty()) {
            if (args.peek() == pivot) {
                args.pop();
                pivotCount++;
            } else if (args.peek() > pivot) {
                rightStack.add(args.pop());
            } else {
                leftStack.add(args.pop());
            }
        }
        leftStack = quickSortForInterval(leftStack);
        addPivot(leftStack, pivot, pivotCount);
        rightStack = quickSortForInterval(rightStack);
        rightStack.addAll(leftStack);
        return rightStack;
    }

    private static int findPivot(Stack<Integer> args) {
        Stack<Integer> stack = new Stack<>();
        stack.addAll(args);
        int length = stack.size() / 2;
        for (int n = 0; n < length; n++) {
            stack.pop();
        }
        return stack.peek();
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

    private static void addPivot(Stack<Integer> stack, int pivot, int count) {
        Stack<Integer> stack1 = new Stack<>();
        for (int i = 0; i < count; i++) {
            stack1.add(pivot);
        }
        stack1.addAll(stack);
        stack.clear();
        stack.addAll(stack1);
    }
}
