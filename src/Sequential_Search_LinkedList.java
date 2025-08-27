import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

class Sequential_Search_LinkedList<T> implements Runnable {
    private final List<T> list;
    private final T target;
    private final AtomicLong operationCount;

    public Sequential_Search_LinkedList(List<T> list, T target, AtomicLong operationCount) {
        this.list = list;
        this.target = target;
        this.operationCount = operationCount;
    }

    @Override
    public void run() {
        int result = search(list, target, operationCount);
        System.out.println("Sequential Search (LinkedList) finished with " + operationCount.get() + " operations, index=" + result);
    }

    private static long linkedListGetOperationCost(int index, int size) {
        return Math.min(index, size - index - 1) + 1;
    }

    public static <T> int search(List<T> list, T x, AtomicLong operationCount) {
        long operations = 0;
        int n = list.size();

        for (int i = 0; i < n; i++) {
            operations += linkedListGetOperationCost(i, n);
            operations++;
            if (Objects.equals(list.get(i), x)) {
                operationCount.set(operations);
                return i;
            }
        }
        operationCount.set(operations);
        return -1;
    }
}