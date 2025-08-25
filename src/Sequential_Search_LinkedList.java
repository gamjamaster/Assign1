import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

class Sequential_Search_LinkedList<T> implements Runnable {
    private final List<T> list;
    private final T target;
    private final AtomicLong time;

    public Sequential_Search_LinkedList(List<T> list, T target, AtomicLong time) {
        this.list = list;
        this.target = target;
        this.time = time;
    }

    @Override
    public void run() {
        long start = System.nanoTime();
        int result = search(list, target);
        long end = System.nanoTime();

        long executionTime = end - start;
        time.set(executionTime);

        System.out.println("Sequential Search (LinkedList) finished in " + (end - start)/1_000_000.0 + " ms, index=" + result);
    }

    public static <T> int search(List<T> list, T x) {
        for (int i = 0; i < list.size(); i++) { 
            if (Objects.equals(list.get(i), x)) // internally call list.get(i).equals(x)
                return i;
        }
        return -1;
    }
}