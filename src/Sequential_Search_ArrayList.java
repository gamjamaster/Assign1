import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

class Sequential_Search_ArrayList<T> implements Runnable {
    private final List<T> list;
    private final T target;
    private final AtomicLong operationCount;

    public Sequential_Search_ArrayList(List<T> list, T target, AtomicLong operationCount) {
        this.list = list;
        this.target = target;
        this.operationCount = operationCount;
    }

    @Override
    public void run() {
        int result = search(list, target, operationCount);
        
        System.out.println("Sequential Search (ArrayList) finished with " + operationCount.get() + " operations, index=" + result);
    }

    public static <T> int search(List<T> list, T x, AtomicLong operationCount) {
        long operations = 0;
        
        for (int i = 0; i < list.size(); i++) { 
            operations++; // Count each comparison operation
            if (Objects.equals(list.get(i), x)) { // internally call list.get(i).equals(x)
                operationCount.set(operations);
                return i;
            }
        }
        
        operationCount.set(operations);
        return -1;
    }
}