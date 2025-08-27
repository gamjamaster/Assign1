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
