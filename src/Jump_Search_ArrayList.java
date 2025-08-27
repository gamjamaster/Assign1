import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Jump_Search_ArrayList<T extends Comparable<T>> implements Runnable {
    private final List<T> list;
    private final T target;
    private final AtomicLong operationCount;
    
    public Jump_Search_ArrayList(List<T> list, T target, AtomicLong operationCount) {
        this.list = list;
        this.target = target;
        this.operationCount = operationCount;
    }
    
    @Override
    public void run() {
        int result = search(list, target, operationCount);
        
        System.out.println("Jump Search (ArrayList) finished with " + operationCount.get() + " operations, index=" + result);
    }

    public static <T extends Comparable<T>> int search(List<T> list, T x, AtomicLong operationCount) {
        long operations = 0;
        int n = list.size();
        int step = (int) Math.floor(Math.sqrt(n));
        int prev = 0;

        // Jump until a block where the element may be present
        while (prev < n && list.get(Math.min(step, n) - 1).compareTo(x) < 0) {
            operations++; // Count each comparison in jumping phase
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) {
                operationCount.set(operations);
                return -1;
            }
        }

        // Linear search through the block to find the element
        while (prev < Math.min(step, n) && list.get(prev).compareTo(x) < 0) {
            operations++; // Count each comparison in linear search phase
            prev++;
        }

        // Check if element found
        if (prev < n) {
            operations++; // Count final comparison
            if (list.get(prev).compareTo(x) == 0) {
                operationCount.set(operations);
                return prev;
            }
        }
        
        operationCount.set(operations);
        return -1;
    }
}