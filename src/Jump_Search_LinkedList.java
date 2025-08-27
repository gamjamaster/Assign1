<<<<<<< HEAD
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Jump_Search_LinkedList<T extends Comparable<T>> implements Runnable {
    private final List<T> list;
    private final T target;
    private final AtomicLong operationCount;

    public Jump_Search_LinkedList(List<T> list, T target, AtomicLong operationCount) {
        this.list = list;
        this.target = target;
        this.operationCount = operationCount;
    }

    @Override
    public void run() {
        int result = search(list, target, operationCount);
        System.out.println("Jump Search (LinkedList) finished with " + operationCount.get() + " operations, index=" + result);
    }

    private static long linkedListGetOperationCost(int index, int size) {
        return Math.min(index, size - index - 1) + 1;
    }

    public static <T extends Comparable<T>> int search(List<T> list, T x, AtomicLong operationCount) {
        long operations = 0;
        int n = list.size();
        int step = (int) Math.floor(Math.sqrt(n));
        int prev = 0;

        while (prev < n && list.get(Math.min(step, n) - 1).compareTo(x) < 0) {
            int idx = Math.min(step, n) - 1;
            operations += linkedListGetOperationCost(idx, n);
            operations++;
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) {
                operationCount.set(operations);
                return -1;
            }
        }

        while (prev < Math.min(step, n) && list.get(prev).compareTo(x) < 0) {
            operations += linkedListGetOperationCost(prev, n);
            operations++;
            prev++;
        }

        if (prev < n) {
            operations += linkedListGetOperationCost(prev, n);
            operations++;
            if (list.get(prev).compareTo(x) == 0) {
                operationCount.set(operations);
                return prev;
            }
        }

        operationCount.set(operations);
        return -1;
    }
}
=======
import java.util.*;

public class Jump_Search_LinkedList<T extends Comparable<T>> implements Runnable {
    
    private final List<T> list;
    private final T target;

    public Jump_Search_LinkedList(List<T> list, T target) {
        this.list = list;
        this.target = target;
    }

    @Override
    public void run() {
        long start = System.nanoTime();
        int result = search(list, target);
        long end = System.nanoTime();
        System.out.println("Jump Search (LinkedList) finished in " + (end - start)/1_000_000.0 + " ms, index=" + result);
    }

    public static <T extends Comparable<T>> int search (List<T> list, T x){
            
         int n = list.size();
        int step = (int)Math.floor(Math.sqrt(n));
        int prev = 0;

        //Jump until a block where the element may be at.
        while (prev < n && list.get(Math.min(step, n) - 1).compareTo(x) < 0) {
            prev = step;
                step += (int) Math.floor(Math.sqrt(n));
                if (prev >= n)
                    return -1;
            }

        //Linearch search throught the block to find the element
        while(prev < Math.min(step,n) && list.get(prev).compareTo(x) < 0){
            prev++;
        }

        //Check if element found
        if(prev < n && list.get(prev).compareTo(x) == 0){
            return prev;
        }
        return -1;
    }
}

>>>>>>> 164f1420b28b58210377ad9f154736703ca50768
