package TaskPacage;

import java.util.Stack;

public class Realization {
    public static int[] quickSort(int[] args) {
        quickSortForInterval(args);
        return args;
    }

    private static void quickSortForInterval(int[] args) {
        Stack<Integer[]> intervals = new Stack<>();
        intervals.add(new Integer[] {0, args.length - 1});
        while (!intervals.empty()) {
            startQuickSort(args, intervals);
        }
    }

    private static void startQuickSort(int[] args, Stack<Integer[]> intervals) {
        Integer[] points = intervals.pop();
        int left = points[0];
        int right = points[1];
        int pivot = args[(points[0] + points[1]) / 2];
        while (left <= right)
        {
            while (left <= right && args[left] < pivot) {
                ++left;
            }
            while (right >= left && args[right] > pivot) {
                --right;
            }
            if (left == right && left == points[0]) {
                ++left;
            }
            if (left == right && left == points[1]) {
                --right;
            }
            if (left < right) {
                swap(args, left, right);
                ++left;
                --right;
            } else {
                break;
            }
        }
        if (points[0] - right != 0) {intervals.add(new Integer[]{points[0], right});}
        if (right + 1 - points[1] != 0) {intervals.add(new Integer[]{right + 1, points[1]});}
    }

    private static void swap(int[] args, int left, int right) {
        int element1 = args[left];
        int element2 = args[right];
        args[left] = element2;
        args[right] = element1;
    }
}
