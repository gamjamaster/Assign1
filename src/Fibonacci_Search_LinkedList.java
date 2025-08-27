import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

class Fibonacci_Search_LinkedList<T extends Comparable<T>> implements Runnable {

    private final List<T> list;
    private final T target;
    private final AtomicLong operationCount;

    public Fibonacci_Search_LinkedList(List<T> list, T target, AtomicLong operationCount) {
        this.list = list;
        this.target = target;
        this.operationCount = operationCount;
    }

    @Override
    public void run() {
        int result = search(list, target, operationCount);
        System.out.println("Fibonacci Search (LinkedList) finished with " + operationCount.get() + " operations, index=" + result);
    }

    private static long linkedListGetOperationCost(int index, int size) {
        return Math.min(index, size - index - 1) + 1;
    }

    public static <T extends Comparable<T>> int search(List<T> list, T x, AtomicLong operationCount) {
        long operations = 0;
        int n = list.size();

        int a = 0, b = 1, c = 1;
        while (c < n) {
            operations++;
            a = b;
            b = c;
            c = a + b;
        }

        int offset = -1;

        while (c > 1) {
            int i = Math.min(offset + a, n - 1);

            operations += linkedListGetOperationCost(i, n);
            operations++;
            int cmp = list.get(i).compareTo(x);

            if (cmp < 0) {
                c = b;
                b = a;
                a = c - b;
                offset = i;
            } else if (cmp > 0) {
                c = a;
                b = b - a;
                a = c - b;
            } else {
                operationCount.set(operations);
                return i;
            }
        }

        if (b != 0 && offset + 1 < n) {
            operations += linkedListGetOperationCost(offset + 1, n);
            operations++;
            if (list.get(offset + 1).compareTo(x) == 0) {
                operationCount.set(operations);
                return offset + 1;
            }
        }

        operationCount.set(operations);
        return -1;
    }
}