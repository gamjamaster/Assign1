import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Binary_Search_LinkedList<T extends Comparable<T>> implements Runnable {
    private final List<T> list;
    private final T target;
    private final AtomicLong operationCount;

    public Binary_Search_LinkedList(List<T> list, T target, AtomicLong operationCount) {
        this.list = list;
        this.target = target;
        this.operationCount = operationCount;
    }

    @Override
    public void run() {
        int result = search(list, target, operationCount);
        System.out.println("Binary Search (LinkedList) finished with " + operationCount.get() + " operations, index=" + result);
    }

    private static long linkedListGetOperationCost(int index, int size) {
        return Math.min(index, size - index - 1) + 1;
    }

    public static <T extends Comparable<T>> int search(List<T> list, T x, AtomicLong operationCount) {
        long operations = 0;
        int l = 0, r = list.size() - 1;
        int n = list.size();

        while (l <= r) {
            int m = l + (r - l) / 2;
            operations += linkedListGetOperationCost(m, n);
            operations++;
            int cmp = list.get(m).compareTo(x);

            if (cmp == 0) {
                operationCount.set(operations);
                return m;
            } else if (cmp > 0) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        operationCount.set(operations);
        return -1;
    }
}