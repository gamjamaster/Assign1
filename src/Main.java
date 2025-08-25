import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import com.mathworks.engine.MatlabEngine;

public class Main {
    public static void main(String[] args) {
        String csvFile = "Article.csv"; // CSV file path

        ArticleCsvLoader loader = new ArticleCsvLoader(csvFile);

        List<Article> arrayList = loader.loadToArrayList();
        List<Article> linkedList = loader.loadToLinkedList();

        int M = 30; // number of times to search
        
        int[] id = new int[M];
        Random rand = new Random();

        for(int i = 0; i < M; i++){
            id[i] = rand.nextInt(20972) + 1;
        }

        // Array for total time for each algorithm
        long[] timesSequentialArray = new long[M];
        long[] timesSequentialLinked = new long[M];
        long[] timesFiboArray = new long[M];
        long[] timesFiboLinked = new long[M];

        for (int idx = 0; idx < M; idx++) {
            int i = id[idx];
            Article target = new Article(i, "", "", 0,0,0,0,0,0);

            AtomicLong timeSequentialArray = new AtomicLong();
            AtomicLong timeSequentialLinked = new AtomicLong();
            AtomicLong timeFiboArray = new AtomicLong();
            AtomicLong timeFiboLinked = new AtomicLong();

            Thread t1 = new Thread(new Sequential_Search_ArrayList<>(arrayList, target, timeSequentialArray));
            Thread t2 = new Thread(new Sequential_Search_LinkedList<>(linkedList, target, timeSequentialLinked));
            Thread t3 = new Thread(new Fibonacci_Search_ArrayList<>(arrayList, target, timeFiboArray));
            Thread t4 = new Thread(new Fibonacci_Search_LinkedList<>(linkedList, target, timeFiboLinked));

            t1.start(); 
            t2.start(); 
            t3.start(); 
            t4.start();

            try {
                t1.join(); 
                t2.join(); 
                t3.join(); 
                t4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            timesSequentialArray[idx] = timeSequentialArray.get();
            timesSequentialLinked[idx] = timeSequentialLinked.get();
            timesFiboArray[idx] = timeFiboArray.get();
            timesFiboLinked[idx] = timeFiboLinked.get();
        }

        // Average Time
        double avgSeqArr = Arrays.stream(timesSequentialArray).average().orElse(0);
        double avgSeqLinked = Arrays.stream(timesSequentialLinked).average().orElse(0);
        double avgFiboArr = Arrays.stream(timesFiboArray).average().orElse(0);
        double avgFiboLinked = Arrays.stream(timesFiboLinked).average().orElse(0);

        try {
            MatlabEngine eng = MatlabEngine.startMatlab();

            double[] times = {
                avgSeqArr / 1_000_000.0, 
                avgSeqLinked / 1_000_000.0, 
                avgFiboArr / 1_000_000.0, 
                avgFiboLinked / 1_000_000.0
            };

            eng.putVariable("searchTimes", times);

            eng.eval("bar(searchTimes);");
            eng.eval("set(gca, 'xticklabel', {'Seq-Array', 'Seq-Linked', 'Fibo-Array', 'Fibo-Linked'});");
            eng.eval("ylabel('Time (ms)');");
            eng.eval("title('Average execution time by search algorithm');");
            Thread.sleep(50000);

            eng.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}