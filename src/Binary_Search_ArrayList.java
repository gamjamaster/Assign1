import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Binary_Search_ArrayList<T extends Comparable<T>> implements Runnable {
    
    private final List<T> list;
    private final T target;
    private final AtomicLong operationCount;
    
    public Binary_Search_ArrayList(List<T> list, T target, AtomicLong operationCount) {
        this.list = list;
        this.target = target;
        this.operationCount = operationCount;
    }

    @Override
    public void run() {
        int result = search(list, target, operationCount);
        
        System.out.println("Binary Search (ArrayList) finished with " + operationCount.get() + " operations, index=" + result);
    }

    public static <T extends Comparable<T>> int search(List<T> list, T x, AtomicLong operationCount) {
        
        long operations = 0;
        int l = 0, r = list.size() - 1;

        while (l <= r) {
            int m = l + (r - l) / 2; // avoid overflow

            // compare with x using compareTo
            operations++; 
            int cmp = list.get(m).compareTo(x);

            if (cmp == 0) {
                operationCount.set(operations); 
                return m; // element found

            } else if (cmp > 0) {
                r = m - 1;

            } else {
                l = m + 1; // Search right half for the element
            }  
        }
        
        operationCount.set(operations);
        return -1;
    }
}