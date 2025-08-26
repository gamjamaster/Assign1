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
        
        Set<Integer> existingIds = new HashSet<>();
        for (Article article : arrayList) {
            existingIds.add(article.getId()); // Assuming Article has getId() method
        }

        // Convert to array for random selection
        Integer[] existingIdsArray = existingIds.toArray(new Integer[0]);

        int[] id = new int[M];
        Random rand = new Random();

        for(int i = 0; i < M; i++){
            if (rand.nextDouble() < 0.8) { // 80% chance
                // Select an existing ID
                int randomIndex = rand.nextInt(existingIdsArray.length);
                id[i] = existingIdsArray[randomIndex];
            } else { // 20% chance
                // Generate a non-existing ID
                int nonExistingId;
                do {
                    nonExistingId = rand.nextInt(50000) + 25000; // Generate from a higher range
                } while (existingIds.contains(nonExistingId));
                id[i] = nonExistingId;
            }
        }

        // Arrays for total operations for each algorithm
        long[] operationsSequentialArray = new long[M];
        long[] operationsSequentialLinked = new long[M];
        long[] operationsFiboArray = new long[M];
        long[] operationsFiboLinked = new long[M];

        for (int idx = 0; idx < M; idx++) {
            int i = id[idx];
            Article target = new Article(i, "", "", 0,0,0,0,0,0);

            AtomicLong opCountSequentialArray = new AtomicLong();
            AtomicLong opCountSequentialLinked = new AtomicLong();
            AtomicLong opCountFiboArray = new AtomicLong();
            AtomicLong opCountFiboLinked = new AtomicLong();

            Thread t1 = new Thread(new Sequential_Search_ArrayList<>(arrayList, target, opCountSequentialArray));
            Thread t2 = new Thread(new Sequential_Search_LinkedList<>(linkedList, target, opCountSequentialLinked));
            Thread t3 = new Thread(new Fibonacci_Search_ArrayList<>(arrayList, target, opCountFiboArray));
            Thread t4 = new Thread(new Fibonacci_Search_LinkedList<>(linkedList, target, opCountFiboLinked));

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

            operationsSequentialArray[idx] = opCountSequentialArray.get();
            operationsSequentialLinked[idx] = opCountSequentialLinked.get();
            operationsFiboArray[idx] = opCountFiboArray.get();
            operationsFiboLinked[idx] = opCountFiboLinked.get();
        }

        // Average Operations
        double avgOpsSeqArr = Arrays.stream(operationsSequentialArray).average().orElse(0);
        double avgOpsSeqLinked = Arrays.stream(operationsSequentialLinked).average().orElse(0);
        double avgOpsFiboArr = Arrays.stream(operationsFiboArray).average().orElse(0);
        double avgOpsFiboLinked = Arrays.stream(operationsFiboLinked).average().orElse(0);

        // Print results to console
        System.out.println("Average Operations:");
        System.out.println("Sequential Array: " + avgOpsSeqArr);
        System.out.println("Sequential Linked: " + avgOpsSeqLinked);
        System.out.println("Fibonacci Array: " + avgOpsFiboArr);
        System.out.println("Fibonacci Linked: " + avgOpsFiboLinked);

        try {
            MatlabEngine eng = MatlabEngine.startMatlab();

            // Create figure with subplots
            eng.eval("figure('Position', [100, 100, 800, 800]);");

            // First graph (top) - Actual performance results
            double[] operations = {
                avgOpsSeqArr, 
                avgOpsSeqLinked, 
                avgOpsFiboArr, 
                avgOpsFiboLinked
            };

            eng.putVariable("searchOperations", operations);

            eng.eval("subplot(2,1,1);");
            eng.eval("bar(searchOperations);");
            eng.eval("set(gca, 'xticklabel', {'Seq-Array', 'Seq-Linked', 'Fibo-Array', 'Fibo-Linked'});");
            eng.eval("ylabel('Number of Operations');");
            eng.eval("title('Actual Average Operations (Experimental Results)');");
            eng.eval("grid on;");

            // Second graph (bottom) - Theoretical time complexity
            TimeComplexity.generateComplexityGraph(eng, arrayList.size());

            Thread.sleep(50000);
            eng.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}