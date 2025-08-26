import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

class Fibonacci_Search_ArrayList<T extends Comparable<T>> implements Runnable {

    private final List<T> list;
    private final T target;
    private final AtomicLong operationCount;

    public Fibonacci_Search_ArrayList(List<T> list, T target, AtomicLong operationCount) {
        this.list = list;
        this.target = target;
        this.operationCount = operationCount;
    }

    @Override
    public void run() {
        int result = search(list, target, operationCount);
        
        System.out.println("Fibonacci Search (ArrayList) finished with " + operationCount.get() + " operations, index=" + result);
    }

    // Returns index of x if present, else returns -1
    public static <T extends Comparable<T>> int search(List<T> list, T x, AtomicLong operationCount) {
        long operations = 0;
        int n = list.size();

        // initialize first three fibonacci numbers
        int a = 0, b = 1, c = 1;

        // find the smallest Fibonacci number greater than or equal to n
        while (c < n) {
            operations++; // Count Fibonacci generation operations
            a = b;
            b = c;
            c = a + b;
        }

        // marks the eliminated range from front
        int offset = -1;

        while (c > 1) {
            // check if a is a valid location
            int i = Math.min(offset + a, n - 1);

            // compare with x using compareTo
            operations++; // Count each comparison operation
            int cmp = list.get(i).compareTo(x);

            if (cmp < 0) { // x is greater
                c = b;
                b = a;
                a = c - b;
                offset = i;
            } else if (cmp > 0) { // x is smaller
                c = a;
                b = b - a;
                a = c - b;
            } else {
                operationCount.set(operations);
                return i; // found
            }
        }

        // check last element
        if (b != 0 && offset + 1 < n) {
            operations++; // Count final comparison
            if (list.get(offset + 1).compareTo(x) == 0) {
                operationCount.set(operations);
                return offset + 1;
            }
        }

        operationCount.set(operations);
        return -1;
    }
}