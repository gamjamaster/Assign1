<<<<<<< HEAD
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
=======
import java.util.List;

public class Binary_Search_ArrayList<T extends Comparable<T>> implements Runnable {
    
    private final List<T> list;
    private final T target;

    public Binary_Search_ArrayList(List<T> list, T target) {
        this.list = list;
        this.target = target;
    }

     @Override
     public void run() {
        long start = System.nanoTime();
        int result = search(list, target);
        long end = System.nanoTime();
        System.out.println("Binary Search (ArrayList) finished in " + (end - start)/1_000_000.0 + " ms, index=" + result);
    }

    public static <T extends Comparable<T>> int search (List<T> list, T x){
        
        int l = 0, r = list.size() - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;//avoid overfow

            // compare with x using compareTo
            int cmp = list.get(m).compareTo(x);

            if (cmp == 0) {
                return m;//element found

            } else if (cmp > 0) {
                r = m - 1;//Search left half for the element

            } else {
              l = m + 1;//Search left right for the  element
            }  
        }
        return -1;
    }
}
>>>>>>> 164f1420b28b58210377ad9f154736703ca50768
